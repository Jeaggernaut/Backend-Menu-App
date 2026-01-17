package argus.menu.controlador;

import argus.menu.modelo.Usuario;
import argus.menu.modelo.LoginRequest;
import argus.menu.modelo.LoginResponse;
import argus.menu.modelo.RegisterRequest;
import argus.menu.modelo.RegisterResponse;
import argus.menu.servicios.AuthServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("menu-app/auth")
@CrossOrigin(value = "http://localhost:4200")
public class AuthControlador {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthControlador.class);
    
    @Autowired
    private AuthServicio authServicio;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Login request recibido para: " + loginRequest.getEmail());
            LoginResponse response = authServicio.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error en login: " + e.getMessage());
            return ResponseEntity.status(401).body(null);
        }
    }
    
    @PostMapping("/registro")
    public ResponseEntity<RegisterResponse> registro(@RequestBody RegisterRequest registerRequest) {
        try {
            logger.info("Registro request recibido para: " + registerRequest.getEmail());
            
            // Validar campos vacíos
            if (registerRequest.getEmail() == null || registerRequest.getPassword() == null) {
                logger.warn("Registro - Campos vacíos");
                return ResponseEntity.badRequest()
                    .body(new RegisterResponse(null, null, null, "Email y contraseña son requeridos"));
            }
            
            // Validar formato de email
            if (!registerRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                logger.warn("Registro - Email inválido: " + registerRequest.getEmail());
                return ResponseEntity.badRequest()
                    .body(new RegisterResponse(null, null, null, "Email inválido"));
            }
            
            // Validar longitud de contraseña
            if (registerRequest.getPassword().length() < 6) {
                logger.warn("Registro - Contraseña muy corta");
                return ResponseEntity.badRequest()
                    .body(new RegisterResponse(null, null, null, 
                        "La contraseña debe tener mínimo 6 caracteres"));
            }
            
            RegisterResponse response = authServicio.register(registerRequest);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error en registro: " + e.getMessage());
            return ResponseEntity.status(409)
                .body(new RegisterResponse(null, null, null, e.getMessage()));
        }
    }
    
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        logger.info("Obteniendo usuario con id: " + id);
        Usuario usuario = authServicio.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
