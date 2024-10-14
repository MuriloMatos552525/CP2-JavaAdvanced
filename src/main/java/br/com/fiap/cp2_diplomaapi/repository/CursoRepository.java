package br.com.fiap.cp2_diplomaapi.repository;

import br.com.fiap.cp2_diplomaapi.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Busca cursos cujo nome contém o texto fornecido (ignora maiúsculas e minúsculas)
    List<Curso> findByNomeContainingIgnoreCase(String nome);
}
