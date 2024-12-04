package com.henriquelaureano.educacional.controller;

import com.henriquelaureano.educacional.dto.ProfessorRequestDTO;
import com.henriquelaureano.educacional.model.Professor;
import com.henriquelaureano.educacional.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository repository;

    @GetMapping
    public ResponseEntity<List<Professor>> finAll() {
        List<Professor> professor = this.repository.findAll();

        return ResponseEntity.ok(professor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> findById(@PathVariable Integer id) {
        Professor professor = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));
        
        return ResponseEntity.ok(professor);
    }

    @PostMapping
    public ResponseEntity<Professor> save(@RequestBody ProfessorRequestDTO dto) {
        Professor professor = new Professor();
        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setEspecialidade(dto.especialidade());
        professor.setTelefone(dto.telefone());

        Professor savedProfessor = this.repository.save(professor);

        return ResponseEntity.ok(savedProfessor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> update(@PathVariable Integer id, @RequestBody ProfessorRequestDTO dto) {
        Professor professor = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));

        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setEspecialidade(dto.especialidade());
        professor.setTelefone(dto.telefone());

        Professor savedProfessor = this.repository.save(professor);
        return ResponseEntity.ok(savedProfessor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Professor professor = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));

        this.repository.delete(professor);
        return ResponseEntity.noContent().build();
    }
}
