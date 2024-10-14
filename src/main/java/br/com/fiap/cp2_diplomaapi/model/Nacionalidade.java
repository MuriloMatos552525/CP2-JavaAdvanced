package br.com.fiap.cp2_diplomaapi.model;

import lombok.Getter;

@Getter
public enum Nacionalidade {

    BRASILEIRO("Brasil", "BR"),
    ARGENTINO("Argentina", "AR"),
    AMERICANO("Estados Unidos", "US");

    // Getters
    private final String pais;
    private final String sigla;

    // Construtor
    Nacionalidade(String pais, String sigla) {
        this.pais = pais;
        this.sigla = sigla;
    }

}
