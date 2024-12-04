package com.henriquelaureano.educacional.controller;

import com.henriquelaureano.educacional.dto.*;
import com.henriquelaureano.educacional.model.Aluno;
import com.henriquelaureano.educacional.model.Matricula;
import com.henriquelaureano.educacional.model.Nota;
import com.henriquelaureano.educacional.model.Turma;
import com.henriquelaureano.educacional.repository.AlunoRepository;
import com.henriquelaureano.educacional.repository.MatriculaRepository;
import com.henriquelaureano.educacional.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @GetMapping
    public List<Aluno> finAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable Integer id) {
        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));
        return ResponseEntity.ok(aluno);
    }

    @PostMapping
    public ResponseEntity<Aluno> save(@RequestBody AlunoRequestDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setData_nascimento(dto.data_nascimento());
        aluno.setMatricula(dto.matricula());

        return ResponseEntity.ok(this.repository.save(aluno));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Aluno> update(@PathVariable Integer id, @RequestBody AlunoRequestDTO dto) {
        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setData_nascimento(dto.data_nascimento());
        aluno.setMatricula(dto.matricula());

        return ResponseEntity.ok(this.repository.save(aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        this.repository.delete(aluno);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{aluno_id}/matricula")
    public ResponseEntity<Aluno> addMatricula(@PathVariable Integer aluno_id, @RequestBody AlunoTurmaRequestDTO dto) {
        Aluno aluno = this.repository.findById(aluno_id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));
        Turma turma = this.turmaRepository.findById(dto.turma_id())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);

        boolean matriculaJaExiste = aluno.getMatriculas().stream()
                .anyMatch(m -> m.getTurma().getID().equals(dto.turma_id()));

        if (!matriculaJaExiste) {
            aluno.addMatricula(matricula);
        } else {
            throw new IllegalArgumentException("O aluno já está matriculado nesta turma.");
        }


        Aluno alunoNota = this.repository.save(aluno);
        return ResponseEntity.ok(alunoNota);

    }

    @GetMapping("/{aluno_id}/boletim")
    public ResponseEntity<BoletimResponseDTO> getNotas(@PathVariable Integer aluno_id) {
        Aluno aluno = this.repository.findById(aluno_id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));
        List<NotaResponseDTO> notas = new ArrayList<>();
        if (!aluno.getMatriculas().isEmpty()) {
            for (Matricula matricula : aluno.getMatriculas()) {
                for (Nota nota : matricula.getNotas()) {
                    notas.add(
                            new NotaResponseDTO(
                                    nota.getID(),
                                    nota.getNota(),
                                    nota.getData_lancamento(),
                                    new DisciplinaResponseDTO(
                                            nota.getDisciplina().getNome(),
                                            nota.getDisciplina().getCodigo()
                                    )
                            )
                    );
                }
            }
        }
        return ResponseEntity.ok(new BoletimResponseDTO(notas));
    }


}
