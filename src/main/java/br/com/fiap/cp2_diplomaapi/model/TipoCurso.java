package br.com.fiap.cp2_diplomaapi.model;

import lombok.Getter;

@Getter
public enum TipoCurso {

    GRADUACAO("Graduação"),
    POS_GRADUACAO("Pós-Graduação");

    private final String descricao;

    TipoCurso(String descricao) {
        this.descricao = descricao;
    }

}
