package br.com.fiap.cp2_diplomaapi.model;

import lombok.Getter;

@Getter
public enum Sexo {

    M("Masculino"),
    F("Feminino");

    private final String descricao;

    Sexo(String descricao) {
        this.descricao = descricao;
    }

}
