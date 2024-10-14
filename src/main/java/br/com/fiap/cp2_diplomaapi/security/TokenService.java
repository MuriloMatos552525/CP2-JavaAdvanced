package br.com.fiap.cp2_diplomaapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

// Importe o Logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    // Crie o Logger
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    public String generateToken(Authentication authentication) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String username = authentication.getName();

            logger.debug("Gerando token para o usuário: {}", username);

            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(username)
                    .withExpiresAt(LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.UTC))
                    .sign(algorithm);
        } catch (Exception exception) {
            logger.error("Erro na geração de token para o usuário: {}", authentication.getName(), exception);
            throw new RuntimeException("Erro na geração de token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String username = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

            logger.debug("Token válido. Usuário extraído: {}", username);
            return username;
        } catch (Exception exception) {
            logger.error("Erro na validação do token", exception);
            return null; // Token inválido ou expirado
        }
    }
}
