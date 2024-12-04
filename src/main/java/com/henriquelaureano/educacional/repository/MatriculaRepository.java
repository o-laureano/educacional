package com.henriquelaureano.educacional.repository;

import com.henriquelaureano.educacional.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
}
