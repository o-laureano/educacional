package com.henriquelaureano.educacional.repository;

import com.henriquelaureano.educacional.model.Disciplina;
import com.henriquelaureano.educacional.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findByDisciplina(Disciplina disciplina);
}
