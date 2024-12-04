package com.henriquelaureano.educacional.model;

import jakarta.persistence.*;

@Entity
@Table(name = "disciplinas")
public class Disciplina {
    /*
     * ID (PK, Integer): Identificador único da disciplina.
     * nome (varchar, 100): Nome da disciplina.
     * codigo (varchar, 20): Código único da disciplina.
     * curso_id (FK, Integer): Referência ao curso ao qual a disciplina pertence
     * (relacionamento ManyToOne com Cursos).
     * professor_id (FK, Integer): Professor responsável pela disciplina (relacionamento
     * ManyToOne com Professores).
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(length = 100)
    private String nome;

    @Column(length = 20)
    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    private Curso curso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id", referencedColumnName = "id")
    private Professor professor;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
