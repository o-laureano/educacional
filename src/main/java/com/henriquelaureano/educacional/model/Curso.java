package com.henriquelaureano.educacional.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {

    /*
     * ID (PK, Integer): Identificador único do curso.
     * nome (varchar, 100): Nome do curso.
     * codigo (varchar, 20): Código único do curso.
     * carga_horaria (Integer): Carga horária total do curso em horas.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(length = 100)
    private String nome;

    @Column(length = 20)
    private String codigo;

    @Column
    private Integer carga_horaria;
    
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

    public Integer getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(Integer carga_horaria) {
        this.carga_horaria = carga_horaria;
    }
}
