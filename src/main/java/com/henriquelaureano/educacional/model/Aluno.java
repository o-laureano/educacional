package com.henriquelaureano.educacional.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alunos")
public class Aluno {
    /*
     * ID (PK, int): Identificador único do aluno.
     * nome (varchar, 100): Nome completo do aluno.
     * email (varchar, 100): Email do aluno.
     * matricula (varchar, 20): Número de matrícula único do aluno.
     * data_nascimento (date): Data de nascimento do aluno.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(length = 100)
    private String nome;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String matricula;

    @Column
    private LocalDate data_nascimento;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("aluno")
    private List<Matricula> matriculas;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public void addMatricula(Matricula matricula) {
        if (this.matriculas == null) {
            this.matriculas = new ArrayList<>();
        }
        this.matriculas.add(matricula);
    }
}
