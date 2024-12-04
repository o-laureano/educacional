package com.henriquelaureano.educacional.controller;

import com.henriquelaureano.educacional.dto.TurmaDesempenhoResponseDTO;
import com.henriquelaureano.educacional.dto.TurmaRequestDTO;
import com.henriquelaureano.educacional.model.Curso;
import com.henriquelaureano.educacional.model.Matricula;
import com.henriquelaureano.educacional.model.Nota;
import com.henriquelaureano.educacional.model.Turma;
import com.henriquelaureano.educacional.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    @Autowired
    MatriculaRepository matriculaRepository;
    @Autowired
    AlunoRepository alunoRepository;
    @Autowired
    private TurmaRepository repository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private NotaRepository notaRepository;

    @GetMapping
    public ResponseEntity<List<Turma>> finAll() {
        List<Turma> turmas = this.repository.findAll();
        return ResponseEntity.ok(turmas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> findById(@PathVariable Integer id) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado."));

        return ResponseEntity.ok(turma);
    }

    @PostMapping
    public ResponseEntity<Turma> save(@RequestBody TurmaRequestDTO dto) {
        Turma turma = new Turma();
        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());

        Curso curso = this.cursoRepository.findById(dto.curso_id())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        turma.setCurso(curso);
        Turma savedTurma = this.repository.save(turma);
        return ResponseEntity.ok(savedTurma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> update(@PathVariable Integer id, @RequestBody TurmaRequestDTO dto) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado."));

        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());

        Curso curso = cursoRepository.findById(dto.curso_id())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        turma.setCurso(curso);

        Turma savedTurma = this.repository.save(turma);

        return ResponseEntity.ok(savedTurma);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado."));

        this.repository.delete(turma);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/notas")
    public ResponseEntity<List<Nota>> getNotes(@PathVariable Integer id) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado."));

        List<Nota> notas = new ArrayList<>();
        for (Matricula matricula : turma.getMatriculas()) {
            notas.addAll(matricula.getNotas());
        }

        return ResponseEntity.ok(notas);
    }

    @GetMapping("/{id}/desempenho")
    public ResponseEntity<TurmaDesempenhoResponseDTO> getPerformance(@PathVariable Integer id) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado."));


        List<Nota> notas = new ArrayList<>();
        for (Matricula matricula : turma.getMatriculas()) {
            notas.addAll(matricula.getNotas());
        }

        double media = notas.stream().mapToDouble(Nota::getNota).average().orElse(0.0);

        media = Math.round(media * 100.0) / 100.0;

        return ResponseEntity.ok(new TurmaDesempenhoResponseDTO(turma.getAno(), turma.getSemestre(), turma.getCurso(), media, turma.getMatriculas().size()));

    }

    @GetMapping("/desempenho")
    public ResponseEntity<List<TurmaDesempenhoResponseDTO>> getPerformance() {
        List<Turma> turmas = this.repository.findAll();
        List<TurmaDesempenhoResponseDTO> turmaDesempenhoResponseDTOS = new ArrayList<>();
        for (Turma turma : turmas) {
            List<Nota> notas = new ArrayList<>();
            for (Matricula matricula : turma.getMatriculas()) {
                notas.addAll(matricula.getNotas());
            }

            double media = notas.stream().mapToDouble(Nota::getNota).average().orElse(0.0);

            media = Math.round(media * 100.0) / 100.0;

            turmaDesempenhoResponseDTOS.add(new TurmaDesempenhoResponseDTO(turma.getAno(), turma.getSemestre(), turma.getCurso(), media, turma.getMatriculas().size()));
        }

        return ResponseEntity.ok(turmaDesempenhoResponseDTOS);
    }


}
