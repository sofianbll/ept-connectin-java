package com.connect_in.backend.service;


import com.connect_in.backend.exception.ResourceNotFoundException;
import com.connect_in.backend.mapper.UserMapper;
import com.connect_in.backend.model.dto.UserDTO;
import com.connect_in.backend.model.dto.request.CreateUserRequest;
import com.connect_in.backend.model.dto.request.UpdateUserRequest;
import com.connect_in.backend.model.entity.User;
import com.connect_in.backend.repository.UserRepository;
import com.connect_in.backend.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // Lombok génère le constructeur avec tous les champs final
@Slf4j  // Lombok génère un logger
@Transactional(readOnly = true)  // Par défaut, toutes les méthodes sont en lecture seule
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // CREATE
    @Transactional  // Méthode en écriture (override le readOnly)
    public UserDTO createUser(CreateUserRequest request) {
        log.info("Création d'un utilisateur avec l'email : {}", request.getEmail());

        // Validation métier : l'email doit être unique
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Un utilisateur existe déjà avec cet email : " + request.getEmail());
        }

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);

        log.info("Utilisateur créé avec succès, ID : {}", savedUser.getId());
        return userMapper.toDTO(savedUser);
    }

    // READ - Récupérer tous les utilisateurs
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        log.info("Récupération de tous les utilisateurs");
        return userRepository.findAll(pageable)
                .map(userMapper::toDTO);
    }

    // READ - Récupérer un utilisateur par son ID
    public UserDTO getUserById(Long id) {
        log.info("Récupération de l'utilisateur avec l'ID : {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        return userMapper.toDTO(user);
    }

    // UPDATE
    @Transactional
    public UserDTO updateUser(Long id, UpdateUserRequest request) {
        log.info("Mise à jour de l'utilisateur avec l'ID : {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));

        // Mettre à jour uniquement les champs fournis
        if (request.getEmail() != null) {
            // Vérifier que l'email n'est pas déjà utilisé par un autre utilisateur
            if (userRepository.existsByEmail(request.getEmail()) &&
                    !user.getEmail().equals(request.getEmail())) {
                throw new ValidationException("Un utilisateur existe déjà avec cet email : " + request.getEmail());
            }
            user.setEmail(request.getEmail());
        }

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        User updatedUser = userRepository.save(user);
        log.info("Utilisateur mis à jour avec succès");

        return userMapper.toDTO(updatedUser);
    }

    // DELETE
    @Transactional
    public void deleteUser(Long id) {
        log.info("Suppression de l'utilisateur avec l'ID : {}", id);

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        }

        userRepository.deleteById(id);
        log.info("Utilisateur supprimé avec succès");
    }

}
