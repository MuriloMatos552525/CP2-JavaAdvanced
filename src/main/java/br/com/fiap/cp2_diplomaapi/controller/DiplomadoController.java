package br.com.fiap.cp2_diplomaapi.controller;

import br.com.fiap.cp2_diplomaapi.dto.DiplomadoRequestDTO;
import br.com.fiap.cp2_diplomaapi.model.Diplomado;
import br.com.fiap.cp2_diplomaapi.repository.DiplomadoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diplomados")
public class DiplomadoController {

    private final DiplomadoRepository diplomadoRepository;

    public DiplomadoController(DiplomadoRepository diplomadoRepository) {
        this.diplomadoRepository = diplomadoRepository;
    }

    // Endpoint para criar um diplomado (acessível por ADMIN)
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Diplomado> criarDiplomado(@RequestBody @Valid DiplomadoRequestDTO diplomadoRequestDTO) {
        Diplomado diplomado = new Diplomado();
        diplomado.setNome(diplomadoRequestDTO.getNome());
        diplomado.setNacionalidade(diplomadoRequestDTO.getNacionalidade().toString());
        diplomado.setNaturalidade(diplomadoRequestDTO.getNaturalidade());
        diplomado.setRg(diplomadoRequestDTO.getRg());

        diplomadoRepository.save(diplomado);

        return new ResponseEntity<>(diplomado, HttpStatus.CREATED);
    }

    // Endpoint para listar diplomados (apenas ADMIN pode acessar)
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Diplomado>> listarDiplomados() {
        return ResponseEntity.ok(diplomadoRepository.findAll());
    }
}
