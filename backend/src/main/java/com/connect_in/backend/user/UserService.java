package com.connect_in.backend.user;

import com.connect_in.backend.shared.exception.ResourceNotFoundException;
import com.connect_in.backend.shared.exception.ValidationException;
import com.connect_in.backend.user.dto.AvatarResponse;
import com.connect_in.backend.user.dto.CreateUserRequest;
import com.connect_in.backend.user.dto.UpdateUserRequest;
import com.connect_in.backend.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${storage.avatars-dir:storage/avatars}")
    private String avatarsStorageDir;

    // ── Routes contrat frontend ───────────────────────────────────────────────

    // GET /api/user — profil de l'utilisateur connecté
    public UserDTO getCurrentUser(User currentUser) {
        return userMapper.toDTO(currentUser);
    }

    // PUT /api/user/update — modifier son propre profil
    @Transactional
    public UserDTO updateCurrentUser(User currentUser, UpdateUserRequest request) {
        log.info("Mise à jour du profil de l'utilisateur ID : {}", currentUser.getId());

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                throw new ValidationException("Un utilisateur existe déjà avec cet email : " + request.getEmail());
            }
            user.setEmail(request.getEmail());
        }

        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            user.setLastName(request.getLastName());
        }

        // Changement de mot de passe (optionnel)
        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {
            if (request.getCurrentPassword() == null || !passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new ValidationException("Mot de passe actuel incorrect");
            }
            if (!request.getNewPassword().equals(request.getNewPasswordConfirmation())) {
                throw new ValidationException("Les nouveaux mots de passe ne correspondent pas");
            }
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        User updated = userRepository.save(user);
        log.info("Profil mis à jour, ID : {}", updated.getId());
        return userMapper.toDTO(updated);
    }

    // POST /api/user/avatar — uploader son avatar
    @Transactional
    public AvatarResponse updateAvatar(User currentUser, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ValidationException("Aucun fichier fourni");
        }

        String avatarPath = saveAvatar(file);

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        user.setProfilPic(avatarPath);
        userRepository.save(user);

        log.info("Avatar mis à jour pour l'utilisateur ID : {}", currentUser.getId());
        return new AvatarResponse(avatarPath);
    }

    // DELETE /api/user — supprimer son propre compte
    @Transactional
    public void deleteAccount(User currentUser) {
        log.info("Suppression du compte de l'utilisateur ID : {}", currentUser.getId());
        userRepository.deleteById(currentUser.getId());
        log.info("Compte supprimé avec succès");
    }

    // GET /api/users/search?q= — recherche par prénom ou nom
    public List<UserDTO> searchUsers(String query) {
        return userRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ── Routes admin (UserAdminController) ───────────────────────────────────

    @Transactional
    public UserDTO createUser(CreateUserRequest request) {
        log.info("(Admin) Création d'un utilisateur avec l'email : {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Un utilisateur existe déjà avec cet email : " + request.getEmail());
        }

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);

        log.info("Utilisateur créé avec succès, ID : {}", savedUser.getId());
        return userMapper.toDTO(savedUser);
    }

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDTO);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));

        if (request.getEmail() != null) {
            if (userRepository.existsByEmail(request.getEmail()) && !user.getEmail().equals(request.getEmail())) {
                throw new ValidationException("Un utilisateur existe déjà avec cet email : " + request.getEmail());
            }
            user.setEmail(request.getEmail());
        }
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());

        return userMapper.toDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        }
        userRepository.deleteById(id);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String saveAvatar(MultipartFile file) {
        try {
            Path dir = Paths.get(avatarsStorageDir);
            Files.createDirectories(dir);

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = dir.resolve(filename);
            Files.write(filePath, file.getBytes());

            return "/storage/avatars/" + filename;
        } catch (IOException e) {
            log.error("Erreur lors de la sauvegarde de l'avatar : {}", e.getMessage());
            throw new RuntimeException("Impossible de sauvegarder l'avatar", e);
        }
    }
}
