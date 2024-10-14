package br.com.fiap.cp2_diplomaapi.service;

import br.com.fiap.cp2_diplomaapi.dto.DiplomaRequestDTO;
import br.com.fiap.cp2_diplomaapi.dto.DiplomaResponseDTO;
import br.com.fiap.cp2_diplomaapi.exception.ResourceNotFoundException;
import br.com.fiap.cp2_diplomaapi.model.Curso;
import br.com.fiap.cp2_diplomaapi.model.Diploma;
import br.com.fiap.cp2_diplomaapi.model.Diplomado;
import br.com.fiap.cp2_diplomaapi.model.Sexo;
import br.com.fiap.cp2_diplomaapi.repository.CursoRepository;
import br.com.fiap.cp2_diplomaapi.repository.DiplomaRepository;
import br.com.fiap.cp2_diplomaapi.repository.DiplomadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DiplomaService {

    @Autowired
    private DiplomaRepository diplomaRepository;

    @Autowired
    private DiplomadoRepository diplomadoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public DiplomaResponseDTO criarDiploma(DiplomaRequestDTO request) {
        Diplomado diplomado = diplomadoRepository.findById(request.getDiplomadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Diplomado não encontrado com ID: " + request.getDiplomadoId()));
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com ID: " + request.getCursoId()));

        Diploma diploma = new Diploma();
        // O UUID será gerado automaticamente pelo JPA
        diploma.setDiplomado(diplomado);
        diploma.setCurso(curso);
        diploma.setDataConclusao(request.getDataConclusao());
        diploma.setSexoReitor(Sexo.valueOf(String.valueOf(request.getSexoReitor())));
        diploma.setNomeReitor(request.getNomeReitor());

        diplomaRepository.save(diploma);

        return gerarDiplomaResponse(diploma);
    }

    public DiplomaResponseDTO obterDiplomaPorId(UUID id) {
        Diploma diploma = diplomaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diploma não encontrado com ID: " + id));

        return gerarDiplomaResponse(diploma);
    }

    private DiplomaResponseDTO gerarDiplomaResponse(Diploma diploma) {
        DiplomaResponseDTO responseDTO = new DiplomaResponseDTO();
        responseDTO.setId(diploma.getId().toString());
        responseDTO.setNomeDiplomado(diploma.getDiplomado().getNome());
        responseDTO.setNacionalidade(diploma.getDiplomado().getNacionalidade());
        responseDTO.setNaturalidade(diploma.getDiplomado().getNaturalidade());
        responseDTO.setRg(diploma.getDiplomado().getRg());
        responseDTO.setNomeCurso(diploma.getCurso().getNome());
        responseDTO.setTipoCurso(diploma.getCurso().getTipo().toString());
        responseDTO.setDataConclusao(diploma.getDataConclusao());
        responseDTO.setSexoReitor(diploma.getSexoReitor());
        responseDTO.setNomeReitor(diploma.getNomeReitor());

        responseDTO.gerarTextoDiploma();

        return responseDTO;
    }
}