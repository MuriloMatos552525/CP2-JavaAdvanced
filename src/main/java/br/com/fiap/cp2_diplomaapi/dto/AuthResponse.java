package br.com.fiap.cp2_diplomaapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponse {
    private final String accessToken;
}
