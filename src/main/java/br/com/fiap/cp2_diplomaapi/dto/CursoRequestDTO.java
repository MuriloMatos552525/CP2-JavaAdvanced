package br.com.fiap.cp2_diplomaapi.dto;

import br.com.fiap.cp2_diplomaapi.model.TipoCurso;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CursoRequestDTO {

    @NotBlank(message = "O nome do curso é obrigatório.")
    private String nome;

    @NotNull(message = "O tipo de curso é obrigatório.")
    private TipoCurso tipoCurso;
}
