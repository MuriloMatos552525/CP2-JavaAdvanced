package br.com.fiap.cp2_diplomaapi.dto;

import br.com.fiap.cp2_diplomaapi.model.Sexo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DiplomaRequestDTO {

    @NotNull(message = "O diplomado é obrigatório.")
    private Long diplomadoId;

    @NotNull(message = "O curso é obrigatório.")
    private Long cursoId;

    @NotNull(message = "A data de conclusão é obrigatória.")
    private LocalDate dataConclusao;

    @NotNull(message = "O sexo do reitor é obrigatório.")
    private Sexo sexoReitor;  // Enum para garantir valores válidos

    @NotBlank(message = "O nome do reitor é obrigatório.")
    private String nomeReitor;
}
