package br.com.fiap.cp2_diplomaapi.repository;

import br.com.fiap.cp2_diplomaapi.model.Diploma;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DiplomaRepository extends JpaRepository<Diploma, UUID> {
    // Consultas adicionais podem ser adicionadas aqui conforme necessário
}
