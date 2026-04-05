package com.connect_in.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// Routes du contrat API à implémenter en Fix 5 :
// GET    /api/user           → profil de l'user connecté
// PUT    /api/user/update    → modifier son profil
// POST   /api/user/avatar    → uploader son avatar
// DELETE /api/user           → supprimer son compte
// GET    /api/users/search   → recherche d'users

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

}
