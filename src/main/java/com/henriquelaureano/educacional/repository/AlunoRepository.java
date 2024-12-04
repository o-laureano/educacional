package com.henriquelaureano.educacional.repository;


import com.henriquelaureano.educacional.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

}

