package com.henriquelaureano.educacional.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "notas")
public class Nota {
    /*
     * ID (PK, Integer): Identificador único da nota.
     * matricula_id (FK, Integer): Referência à matrícula do aluno (relacionamento
     * ManyToOne com Matriculas).
     * disciplina_id (FK, Integer): Referência à disciplina para a qual a nota foi
     * atribuída (relacionamento ManyToOne com Disciplinas).
     * nota (decimal, 5, 2): Nota obtida pelo aluno na disciplina.
     * data_lancamento (date): Data de lançamento da nota.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column
    private double nota;

    @Column
    private LocalDate data_lancamento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matricula_id", referencedColumnName = "id")
    @JsonIgnoreProperties("notas")
    private Matricula matricula;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disciplina_id", referencedColumnName = "id")
    private Disciplina disciplina;


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }


    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public LocalDate getData_lancamento() {
        return data_lancamento;
    }

    public void setData_lancamento(LocalDate data_lancamento) {
        this.data_lancamento = data_lancamento;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}
