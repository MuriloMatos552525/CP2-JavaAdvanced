package br.com.fiap.cp2_diplomaapi.dto;

import br.com.fiap.cp2_diplomaapi.model.Sexo;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class DiplomaResponseDTO {

    private String id; // Removido 'final'
    private String nomeDiplomado; // Removido 'final'
    private String nacionalidade; // Removido 'final'
    private String naturalidade; // Removido 'final'
    private String rg; // Removido 'final'
    private String nomeCurso; // Removido 'final'
    private String tipoCurso; // Removido 'final'
    private LocalDate dataConclusao; // Removido 'final'
    private Sexo sexoReitor; // Removido 'final'
    private String nomeReitor; // Removido 'final'
    private String tituloReitor;
    private String cargoReitor;
    private String textoDiploma;

    public void gerarTextoDiploma() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.tituloReitor = (sexoReitor == Sexo.M ? "Prof. Dr. " : "Profa. Dra. ") + nomeReitor;
        this.cargoReitor = sexoReitor == Sexo.M ? "reitor" : "reitora";

        this.textoDiploma = String.format(
                "O %s, %s da Universidade Fake, no uso de suas atribuições, confere a %s, de nacionalidade %s, natural de %s, " +
                        "portador do rg %s, o presente diploma de %s, do curso de %s, por ter concluído seus estudos nesta instituição de ensino no dia %s.",
                tituloReitor,
                cargoReitor,
                nomeDiplomado,
                nacionalidade,
                naturalidade,
                rg,
                tipoCurso,
                nomeCurso,
                dataConclusao.format(formatter)
        );
    }
}
