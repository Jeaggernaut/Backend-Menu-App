package argus.menu.servicios;

import argus.menu.modelo.Usuario;
import argus.menu.modelo.LoginRequest;
import argus.menu.modelo.LoginResponse;
import argus.menu.modelo.RegisterRequest;
import argus.menu.modelo.RegisterResponse;
import argus.menu.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServicio {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthServicio.class);
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        logger.info("Intento de login con email: " + loginRequest.getEmail());
        
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByEmail(loginRequest.getEmail());
        
        if (usuarioOpt.isEmpty()) {
            logger.warn("Usuario no encontrado: " + loginRequest.getEmail());
            throw new Exception("Usuario no encontrado");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        if (!usuario.getActivo()) {
            logger.warn("Usuario desactivado: " + loginRequest.getEmail());
            throw new Exception("Usuario desactivado");
        }
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            logger.warn("Contraseña incorrecta para: " + loginRequest.getEmail());
            throw new Exception("Contraseña incorrecta");
        }
        
        String token = generateToken(usuario);
        logger.info("Login exitoso para: " + loginRequest.getEmail());
        
        return new LoginResponse(
            token,
            usuario.getId().toString(),
            usuario.getNombre(),
            usuario.getEmail()
        );
    }
    
    private String generateToken(Usuario usuario) {
        // Opción simple: UUID (considera usar JWT para producción)
        return UUID.randomUUID().toString();
    }
    
    public Usuario registrarUsuario(Usuario usuario) throws Exception {
        if (usuarioRepositorio.findByEmail(usuario.getEmail()).isPresent()) {
            throw new Exception("El email ya está registrado");
        }
        
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setActivo(true);
        
        logger.info("Usuario registrado: " + usuario.getEmail());
        return usuarioRepositorio.save(usuario);
    }
    
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }
    
    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        // Validar que el email no exista
        if (usuarioRepositorio.findByEmail(registerRequest.getEmail()).isPresent()) {
            logger.warn("Intento de registro con email existente: " + registerRequest.getEmail());
            throw new Exception("El email ya está registrado");
        }
        
        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setEmail(registerRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        
        // Generar nombre automático del email (parte antes del @)
        String nombre = registerRequest.getEmail().split("@")[0];
        usuario.setNombre(nombre);
        
        usuario.setActivo(true);
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setFechaActualizacion(LocalDateTime.now());
        
        // Guardar el usuario en la BD
        Usuario usuarioGuardado = usuarioRepositorio.save(usuario);
        logger.info("Usuario registrado exitosamente: " + usuarioGuardado.getEmail());
        
        return new RegisterResponse(
            usuarioGuardado.getId().toString(),
            usuarioGuardado.getEmail(),
            usuarioGuardado.getNombre(),
            "Usuario creado exitosamente"
        );
    }
}
