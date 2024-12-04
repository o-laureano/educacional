package com.henriquelaureano.educacional.dto;

import java.time.LocalDate;

public record NotaRequestDTO(Double nota, LocalDate data_lancamento, Integer matricula_id, Integer disciplina_id) {
}
