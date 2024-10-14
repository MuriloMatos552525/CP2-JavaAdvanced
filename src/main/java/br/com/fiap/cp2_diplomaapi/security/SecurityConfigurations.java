package br.com.fiap.cp2_diplomaapi.security;

import br.com.fiap.cp2_diplomaapi.security.SecurityFilter;
import br.com.fiap.cp2_diplomaapi.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Importações atualizadas
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Habilita @PreAuthorize
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita segurança em nível de método
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Define o PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define o AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Define o SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Desativa CSRF para APIs stateless
                .csrf(csrf -> csrf.disable())
                // Configura a sessão como STATELESS
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configura as regras de autorização
                .authorizeHttpRequests(authorize -> authorize
                        // Rotas abertas para todos
                        .requestMatchers("/auth/login", "/auth/register").permitAll()
                        // Rotas protegidas - requisições GET (disponível para USER e ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/diplomas/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/diplomados/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/cursos/**").hasAnyAuthority("ADMIN", "USER")
                        // Rotas protegidas - requisições POST (apenas ADMIN pode criar)
                        .requestMatchers(HttpMethod.POST, "/api/diplomas").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/diplomados").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/cursos").hasAuthority("ADMIN")
                        // Rotas protegidas - requisições PUT e DELETE (apenas ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")
                        // Qualquer outra requisição deve ser autenticada
                        .anyRequest().authenticated()
                )
                // Adiciona o CustomUserDetailsService e PasswordEncoder
                .userDetailsService(customUserDetailsService)
                .authenticationManager(authenticationManager(null))
                // Adiciona o filtro JWT antes do UsernamePasswordAuthenticationFilter
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
