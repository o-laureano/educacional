package com.henriquelaureano.educacional.repository;

import com.henriquelaureano.educacional.model.Curso;
import com.henriquelaureano.educacional.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {
    List<Disciplina> findByCurso(Curso curso);
}
