package com.henriquelaureano.educacional.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "matriculas")
public class Matricula {

    /*
     * ID (PK, Integer): Identificador único da matrícula.
     * aluno_id (FK, Integer): Referência ao aluno matriculado (relacionamento ManyToOne
     * com Alunos).
     * turma_id (FK, Integer): Referência à turma na qual o aluno está matriculado
     * (relacionamento ManyToOne com Turmas).
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @ManyToOne
    @JoinColumn(name = "aluno_id", referencedColumnName = "id")
    @JsonIgnoreProperties("matriculas")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "turma_id", referencedColumnName = "id")
    @JsonIgnoreProperties("matriculas")
    private Turma turma;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("matricula")
    private List<Nota> notas;

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public Integer getId() {
        return ID;
    }

    public void setId(Integer ID) {
        this.ID = ID;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aluno, turma);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matricula matricula = (Matricula) o;
        return Objects.equals(aluno, matricula.aluno) && Objects.equals(turma, matricula.turma);
    }
}
