package br.com.fiap.cp2_diplomaapi.repository;

import br.com.fiap.cp2_diplomaapi.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Busca usuário pelo nome de usuário com roles carregadas
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUsername(String username);
}
