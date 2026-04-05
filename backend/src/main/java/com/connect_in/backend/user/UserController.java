package com.connect_in.backend.user;

import com.connect_in.backend.user.dto.AvatarResponse;
import com.connect_in.backend.user.dto.UpdateUserRequest;
import com.connect_in.backend.user.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET /api/user returns the current user's profile.
    @GetMapping("/user")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.getCurrentUser(currentUser));
    }

    // PUT /api/user/update updates the current user's profile.
    @PutMapping("/user/update")
    public ResponseEntity<UserDTO> updateCurrentUser(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateCurrentUser(currentUser, request));
    }

    // POST /api/user/avatar uploads the current user's avatar (multipart).
    @PostMapping(value = "/user/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AvatarResponse> updateAvatar(
            @AuthenticationPrincipal User currentUser,
            @RequestParam("avatar") MultipartFile file) {
        return ResponseEntity.ok(userService.updateAvatar(currentUser, file));
    }

    // DELETE /api/user deletes the current user's account.
    @DeleteMapping("/user")
    public ResponseEntity<Map<String, Boolean>> deleteAccount(@AuthenticationPrincipal User currentUser) {
        userService.deleteAccount(currentUser);
        return ResponseEntity.ok(Map.of("delete_content", true));
    }

    // GET /api/users/search?q= searches users by first or last name.
    @GetMapping("/users/search")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String q) {
        return ResponseEntity.ok(userService.searchUsers(q));
    }
}
