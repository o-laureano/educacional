package com.henriquelaureano.educacional.dto;

import com.henriquelaureano.educacional.model.Curso;

public record TurmaDesempenhoResponseDTO(Integer ano, Integer semestre, Curso curso, double media_turma,
                                         Integer numero_matriculas) {
}
