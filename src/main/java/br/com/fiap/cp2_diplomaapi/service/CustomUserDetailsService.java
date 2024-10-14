package br.com.fiap.cp2_diplomaapi.service;

import br.com.fiap.cp2_diplomaapi.model.User;
import br.com.fiap.cp2_diplomaapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

// Importe o Logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Crie o Logger
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Tentativa de carregar usuário pelo username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.debug("Usuário não encontrado: {}", username);
                    return new UsernameNotFoundException("Usuário não encontrado com o username: " + username);
                });
        logger.debug("Usuário encontrado: {}", user.getUsername());
        logger.debug("Roles do usuário: {}", user.getRoles());
        return user;
    }
}
