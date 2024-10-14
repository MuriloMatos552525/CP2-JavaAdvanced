package br.com.fiap.cp2_diplomaapi.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class RegisterDTO {

    @NotBlank(message = "O nome de usuário é obrigatório.")
    @JsonProperty("username")
    private String username;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private String role;  // Pode ser opcional, definido como null ou default para usuários normais
}
