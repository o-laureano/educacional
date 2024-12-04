package com.henriquelaureano.educacional.controller;

import com.henriquelaureano.educacional.dto.MatriculaRequestDTO;
import com.henriquelaureano.educacional.model.Aluno;
import com.henriquelaureano.educacional.model.Matricula;
import com.henriquelaureano.educacional.model.Turma;
import com.henriquelaureano.educacional.repository.AlunoRepository;
import com.henriquelaureano.educacional.repository.MatriculaRepository;
import com.henriquelaureano.educacional.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    @Autowired
    MatriculaRepository repository;

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    TurmaRepository turmaRepository;

    @GetMapping
    public ResponseEntity<List<Matricula>> finAll() {
        List<Matricula> matriculas = this.repository.findAll();
        return ResponseEntity.ok(matriculas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> findById(@PathVariable Integer id) {
        Matricula matricula = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matricula não encontrada."));

        return ResponseEntity.ok(matricula);
    }

    @PostMapping
    public ResponseEntity<Matricula> save(@RequestBody MatriculaRequestDTO dto) {
        Matricula matricula = new Matricula();

        Aluno aluno = this.alunoRepository.findById(dto.aluno_id())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        matricula.setAluno(aluno);

        Turma turma = this.turmaRepository.findById(dto.turma_id())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));

        matricula.setTurma(turma);

        Matricula savedMatricula = this.repository.save(matricula);

        return ResponseEntity.ok(savedMatricula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Matricula> update(@PathVariable Integer id, @RequestBody MatriculaRequestDTO dto) {
        Matricula matricula = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matricula não encontrada."));

        Aluno aluno = this.alunoRepository.findById(dto.aluno_id())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        matricula.setAluno(aluno);

        Turma turma = this.turmaRepository.findById(dto.aluno_id())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));

        matricula.setTurma(turma);
        Matricula savedMatricula = this.repository.save(matricula);

        return ResponseEntity.ok(savedMatricula);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Matricula matricula = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matricula não encontrada."));

        this.repository.delete(matricula);
        return ResponseEntity.noContent().build();
    }
}
