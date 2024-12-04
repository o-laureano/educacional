package com.henriquelaureano.educacional.repository;

import com.henriquelaureano.educacional.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
}
