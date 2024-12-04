package com.henriquelaureano.educacional.dto;

import java.time.LocalDate;

public record NotaResponseDTO(Integer ID, Double nota, LocalDate data_lancamento, DisciplinaResponseDTO disciplina) {
}
