package br.com.educandoweb.entities;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;


@Entity
public class Cidades implements Serializable {
    private static final long serialVersionUID = 1L;


    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)  //fetch = FetchType.EAGER = quando carregar um estado eu também quero que carregue uam cidade
    private Estados estados;



    //MÉTODOS ESPECIAIS
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Estados getEstados() {
        return estados;
    }
    public void setEstados(Estados estados) {
        this.estados = estados;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cidades cidades = (Cidades) o;
        return Objects.equals(id, cidades.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }



}
