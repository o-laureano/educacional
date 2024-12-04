package com.henriquelaureano.educacional.controller;

import com.henriquelaureano.educacional.dto.NotaRequestDTO;
import com.henriquelaureano.educacional.model.Disciplina;
import com.henriquelaureano.educacional.model.Matricula;
import com.henriquelaureano.educacional.model.Nota;
import com.henriquelaureano.educacional.repository.DisciplinaRepository;
import com.henriquelaureano.educacional.repository.MatriculaRepository;
import com.henriquelaureano.educacional.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    NotaRepository repository;

    @Autowired
    MatriculaRepository matriculaRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @GetMapping
    public ResponseEntity<List<Nota>> finAll() {
        List<Nota> notas = this.repository.findAll();
        return ResponseEntity.ok(notas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nota> findById(@PathVariable Integer id) {
        Nota nota = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada."));
        return ResponseEntity.ok(nota);
    }

    @PostMapping
    public ResponseEntity<Nota> save(@RequestBody NotaRequestDTO dto) {
        Nota nota = new Nota();

        nota.setNota(dto.nota());
        nota.setData_lancamento(dto.data_lancamento());

        Matricula matricula = this.matriculaRepository.findById(dto.matricula_id())
                .orElseThrow(() -> new IllegalArgumentException("Matricula não encontrada."));

        nota.setMatricula(matricula);

        Disciplina disciplina = this.disciplinaRepository.findById(dto.disciplina_id())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada."));

        nota.setDisciplina(disciplina);

        Nota savedNota = this.repository.save(nota);

        return ResponseEntity.ok(savedNota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nota> update(@PathVariable Integer id, @RequestBody NotaRequestDTO dto) {
        Nota nota = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada."));

        nota.setNota(dto.nota());
        nota.setData_lancamento(dto.data_lancamento());

        Matricula matricula = this.matriculaRepository.findById(dto.matricula_id())
                .orElseThrow(() -> new IllegalArgumentException("Matricula não encontrada."));

        nota.setMatricula(matricula);

        Disciplina disciplina = this.disciplinaRepository.findById(dto.disciplina_id())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada."));

        nota.setDisciplina(disciplina);

        Nota savedNota = this.repository.save(nota);

        return ResponseEntity.ok(savedNota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Nota nota = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada."));

        this.repository.delete(nota);
        return ResponseEntity.noContent().build();
    }

}
