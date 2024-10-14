package br.com.fiap.cp2_diplomaapi.controller;

import br.com.fiap.cp2_diplomaapi.dto.AuthRequest;
import br.com.fiap.cp2_diplomaapi.dto.LoginResponseDTO;
import br.com.fiap.cp2_diplomaapi.dto.RegisterDTO;
import br.com.fiap.cp2_diplomaapi.model.Role;
import br.com.fiap.cp2_diplomaapi.model.User;
import br.com.fiap.cp2_diplomaapi.repository.UserRepository;
import br.com.fiap.cp2_diplomaapi.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

// Importe o Logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    // Crie o Logger
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerDTO) {
        // Verifica se o usuário já está registrado
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário já registrado.");
        }

        // Cria um novo usuário e define suas propriedades
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // Define o papel do usuário (ADMIN ou USER)
        if (registerDTO.getRole() != null && registerDTO.getRole().equalsIgnoreCase("ADMIN")) {
            user.setRoles(Collections.singleton(Role.ADMIN));
        } else {
            user.setRoles(Collections.singleton(Role.USER));
        }

        // Salva o novo usuário no banco de dados
        userRepository.save(user);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest) {
        try {
            logger.debug("Tentativa de autenticar o usuário: {}", authRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            logger.debug("Usuário autenticado com sucesso: {}", authentication.getName());

            String token = tokenService.generateToken(authentication);
            logger.debug("Token gerado para o usuário: {}", authentication.getName());

            return ResponseEntity.ok(new LoginResponseDTO(token));

        } catch (Exception e) {
            logger.error("Falha na autenticação do usuário: {}", authRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login ou senha inválidos.");
        }
    }
}
