package br.com.fiap.cp2_diplomaapi.dto;

import br.com.fiap.cp2_diplomaapi.model.Nacionalidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DiplomadoRequestDTO {

    @NotBlank(message = "O nome do diplomado é obrigatório.")
    private String nome;

    @NotNull(message = "A nacionalidade é obrigatória.")
    private Nacionalidade nacionalidade;

    @NotBlank(message = "A naturalidade é obrigatória.")
    private String naturalidade;

    @NotBlank(message = "O RG é obrigatório.")
    private String rg;
}
