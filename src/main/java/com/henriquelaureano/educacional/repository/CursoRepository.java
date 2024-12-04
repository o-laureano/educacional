package com.henriquelaureano.educacional.repository;

import com.henriquelaureano.educacional.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
}
