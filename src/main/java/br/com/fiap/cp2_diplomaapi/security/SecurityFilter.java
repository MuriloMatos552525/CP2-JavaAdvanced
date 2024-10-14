package br.com.fiap.cp2_diplomaapi.security;

import br.com.fiap.cp2_diplomaapi.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// Importe o Logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Crie o Logger
    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.debug("SecurityFilter: doFilterInternal chamado para URI: {}", request.getRequestURI());

        var token = recoverToken(request);
        if (token != null) {
            var username = tokenService.validateToken(token);
            logger.debug("Token validado. Username extraído: {}", username);

            if (username != null) {
                var userDetails = customUserDetailsService.loadUserByUsername(username);
                logger.debug("UserDetails carregado: {}", userDetails.getUsername());
                logger.debug("Authorities do usuário: {}", userDetails.getAuthorities());

                if (userDetails != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.debug("Autenticação estabelecida para o usuário: {}", userDetails.getUsername());
                }
            } else {
                logger.debug("Username não pôde ser extraído do token.");
            }
        } else {
            logger.debug("Nenhum token encontrado na requisição.");
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        logger.debug("Cabeçalho Authorization: {}", authHeader);
        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(7) : null;
    }
}
