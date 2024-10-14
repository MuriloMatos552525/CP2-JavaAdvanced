package br.com.fiap.cp2_diplomaapi.repository;

import br.com.fiap.cp2_diplomaapi.model.Diplomado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DiplomadoRepository extends JpaRepository<Diplomado, Long> {

    // Busca diplomados por nacionalidade
    List<Diplomado> findByNacionalidade(String nacionalidade);
}
