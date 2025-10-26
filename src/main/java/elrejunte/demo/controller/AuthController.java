package elrejunte.demo.controller;

import elrejunte.demo.entity.User;
import elrejunte.demo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permitir frontend
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Registro de usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        boolean success = authService.register(user);
        if (success) {
            // Devolvemos los datos del nuevo usuario (sin password)
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("{\"message\": \"El email ya está registrado\"}");
        }
    }

    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User loggedUser = authService.login(user.getEmail(), user.getPassword());
        if (loggedUser != null) {
            loggedUser.setPassword(null); // No enviar contraseña
            return ResponseEntity.ok(loggedUser);
        } else {
            return ResponseEntity
                    .status(401)
                    .body("{\"message\": \"Credenciales incorrectas\"}");
        }
    }
}
