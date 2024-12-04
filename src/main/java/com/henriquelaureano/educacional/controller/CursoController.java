package com.henriquelaureano.educacional.controller;


import com.henriquelaureano.educacional.dto.CursoRequestDTO;
import com.henriquelaureano.educacional.dto.CursoResponseDTO;
import com.henriquelaureano.educacional.model.Curso;
import com.henriquelaureano.educacional.model.Disciplina;
import com.henriquelaureano.educacional.repository.CursoRepository;
import com.henriquelaureano.educacional.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> findAll() {
        List<Curso> cursos = this.repository.findAll();

        return ResponseEntity.ok(cursos.stream()
                .map(curso -> {
                    List<Disciplina> disciplinas = this.disciplinaRepository.findByCurso(curso);
                    return new CursoResponseDTO(curso.getNome(), curso.getCodigo(), curso.getCarga_horaria(), curso.getID(), disciplinas);
                })
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> findById(@PathVariable Integer id) {
        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));
        List<Disciplina> disciplinas = this.disciplinaRepository.findByCurso(curso);
        return ResponseEntity.ok(new CursoResponseDTO(curso.getNome(), curso.getCodigo(), curso.getCarga_horaria(), curso.getID(), disciplinas));


    }

    @PostMapping
    public ResponseEntity<Curso> save(@RequestBody CursoRequestDTO dto) {
        Curso curso = new Curso();
        curso.setNome(dto.nome());
        curso.setCodigo(dto.codigo());
        curso.setCarga_horaria(dto.carga_horaria());

        return ResponseEntity.ok(this.repository.save(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> update(@PathVariable Integer id, @RequestBody CursoRequestDTO dto) {
        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        curso.setNome(dto.nome());
        curso.setCodigo(dto.codigo());
        curso.setCarga_horaria(dto.carga_horaria());

        return ResponseEntity.ok(this.repository.save(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        this.repository.delete(curso);
        return ResponseEntity.noContent().build();
    }
}
