package br.com.fiap.cp2_diplomaapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Diploma {

    @Id
    @GeneratedValue
    private UUID id; // UUID para o hash de validação

    @ManyToOne
    @JoinColumn(name = "diplomado_id", nullable = false)
    private Diplomado diplomado;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @PastOrPresent(message = "A data de conclusão deve ser no passado ou presente.")
    @Column(nullable = false)
    private LocalDate dataConclusao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexoReitor;

    @Size(max = 100, message = "O nome do reitor deve ter no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nomeReitor;
}
