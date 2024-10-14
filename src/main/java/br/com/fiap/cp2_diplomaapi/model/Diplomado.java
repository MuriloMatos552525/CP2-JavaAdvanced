package br.com.fiap.cp2_diplomaapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Diplomado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @Size(max = 50, message = "A nacionalidade deve ter no máximo 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String nacionalidade;

    @Size(max = 100, message = "A naturalidade deve ter no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String naturalidade;

    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}", message = "O RG deve seguir o formato XX.XXX.XXX-X.")
    @Column(nullable = false, unique = true)
    private String rg;
}
