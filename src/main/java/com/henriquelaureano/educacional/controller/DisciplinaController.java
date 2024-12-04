package com.henriquelaureano.educacional.controller;


import com.henriquelaureano.educacional.dto.DisciplinaDesempenhoResponseDTO;
import com.henriquelaureano.educacional.dto.DisciplinaRequestDTO;
import com.henriquelaureano.educacional.model.Curso;
import com.henriquelaureano.educacional.model.Disciplina;
import com.henriquelaureano.educacional.model.Nota;
import com.henriquelaureano.educacional.model.Professor;
import com.henriquelaureano.educacional.repository.CursoRepository;
import com.henriquelaureano.educacional.repository.DisciplinaRepository;
import com.henriquelaureano.educacional.repository.NotaRepository;
import com.henriquelaureano.educacional.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private NotaRepository notaRepository;

    @GetMapping
    public ResponseEntity<List<Disciplina>> findAll() {
        List<Disciplina> disciplinas = this.repository.findAll();
        return ResponseEntity.ok(disciplinas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> findById(@PathVariable Integer id) {
        Disciplina disciplina = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrado."));

        return ResponseEntity.ok(disciplina);
    }

    @PostMapping
    public ResponseEntity<Disciplina> save(@RequestBody DisciplinaRequestDTO dto) {
        Disciplina disciplina = new Disciplina();

        disciplina.setNome(dto.nome());
        disciplina.setCodigo(dto.codigo());

        Curso curso = this.cursoRepository.findById(dto.curso_id())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        disciplina.setCurso(curso);

        Professor professor = this.professorRepository.findById(dto.professor_id())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));

        disciplina.setProfessor(professor);

        Disciplina savedDisciplina = this.repository.save(disciplina);

        return ResponseEntity.ok(savedDisciplina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> update(@PathVariable Integer id, @RequestBody DisciplinaRequestDTO dto) {
        Disciplina disciplina = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrado."));

        disciplina.setNome(dto.nome());
        disciplina.setCodigo(dto.codigo());

        Curso curso = this.cursoRepository.findById(dto.curso_id())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        disciplina.setCurso(curso);

        Professor professor = this.professorRepository.findById(dto.professor_id())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));

        disciplina.setProfessor(professor);
        Disciplina savedDisciplina = this.repository.save(disciplina);

        return ResponseEntity.ok(savedDisciplina);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Disciplina disciplina = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrado."));

        this.repository.delete(disciplina);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/notas")
    public ResponseEntity<List<Nota>> getNotas(@PathVariable Integer id) {
        Disciplina disciplina = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrado."));

        List<Nota> notas = this.notaRepository.findByDisciplina(disciplina);

        return ResponseEntity.ok(notas);
    }

    @GetMapping("/{id}/desempenho")
    public ResponseEntity<DisciplinaDesempenhoResponseDTO> getDesempenho(@PathVariable Integer id) {
        Disciplina disciplina = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrado."));

        List<Nota> notas = this.notaRepository.findByDisciplina(disciplina);
        double media = notas.stream().mapToDouble(Nota::getNota).average().orElse(0.0);
        media = Math.round(media * 100.0) / 100.0;
        Integer numeroAlunos = notas.size();

        DisciplinaDesempenhoResponseDTO desempenho = new DisciplinaDesempenhoResponseDTO(disciplina.getNome(), disciplina.getCodigo(), disciplina.getCurso(), media, numeroAlunos);

        return ResponseEntity.ok(desempenho);

    }

    @GetMapping("/desempenho")
    public ResponseEntity<List<DisciplinaDesempenhoResponseDTO>> getDesempenho() {
        List<Disciplina> disciplinas = this.repository.findAll();
        List<DisciplinaDesempenhoResponseDTO> desempenhos = disciplinas.stream().map(disciplina -> {
            List<Nota> notas = this.notaRepository.findByDisciplina(disciplina);
            double media = notas.stream().mapToDouble(Nota::getNota).average().orElse(0.0);
            media = Math.round(media * 100.0) / 100.0;
            Integer numeroAlunos = notas.size();
            return new DisciplinaDesempenhoResponseDTO(disciplina.getNome(), disciplina.getCodigo(), disciplina.getCurso(), media, numeroAlunos);
        }).toList();

        return ResponseEntity.ok(desempenhos);
    }
}
