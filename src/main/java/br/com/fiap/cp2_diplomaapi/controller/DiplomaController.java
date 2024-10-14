package br.com.fiap.cp2_diplomaapi.controller;

import br.com.fiap.cp2_diplomaapi.dto.DiplomaRequestDTO;
import br.com.fiap.cp2_diplomaapi.dto.DiplomaResponseDTO;
import br.com.fiap.cp2_diplomaapi.service.DiplomaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/diplomas")
public class DiplomaController {

    private final DiplomaService diplomaService;

    // Injeção de dependência via construtor
    public DiplomaController(DiplomaService diplomaService) {
        this.diplomaService = diplomaService;
    }

    // Endpoint para criar um diploma (apenas ADMIN pode acessar)
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DiplomaResponseDTO> criarDiploma(@RequestBody DiplomaRequestDTO diplomaRequestDTO) {
        DiplomaResponseDTO responseDTO = diplomaService.criarDiploma(diplomaRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Endpoint para obter um diploma por ID (acessível por ADMIN e USER)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<DiplomaResponseDTO> obterDiplomaPorId(@PathVariable UUID id) {
        DiplomaResponseDTO responseDTO = diplomaService.obterDiplomaPorId(id);
        return ResponseEntity.ok(responseDTO);
    }
}
