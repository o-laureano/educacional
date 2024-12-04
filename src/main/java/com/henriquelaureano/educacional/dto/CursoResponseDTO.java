package com.henriquelaureano.educacional.dto;

import com.henriquelaureano.educacional.model.Disciplina;

import java.util.List;

public record CursoResponseDTO(String nome, String codigo, Integer carga_horaria, Integer ID,
                               List<Disciplina> disciplinas) {
}
