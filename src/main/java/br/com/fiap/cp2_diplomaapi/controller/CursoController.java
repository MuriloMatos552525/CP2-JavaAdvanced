package br.com.fiap.cp2_diplomaapi.controller;

import br.com.fiap.cp2_diplomaapi.dto.CursoRequestDTO;
import br.com.fiap.cp2_diplomaapi.model.Curso;
import br.com.fiap.cp2_diplomaapi.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoRepository cursoRepository;

    public CursoController(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    // Endpoint para criar um curso (acessível por ADMIN)
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Curso> criarCurso(@RequestBody @Valid CursoRequestDTO cursoRequestDTO) {
        Curso curso = new Curso();
        curso.setNome(cursoRequestDTO.getNome());
        curso.setTipo(cursoRequestDTO.getTipoCurso());

        cursoRepository.save(curso);

        return new ResponseEntity<>(curso, HttpStatus.CREATED);
    }

    // Endpoint para listar cursos (acessível por ADMIN)
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Curso>> listarCursos() {
        return ResponseEntity.ok(cursoRepository.findAll());
    }
}
