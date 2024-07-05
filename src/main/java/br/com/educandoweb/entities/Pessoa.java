package br.com.educandoweb.entities;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String sobrenome;
    private Integer idade;
    private String sexo;
    private String[] frameworks;
    private Boolean ativo;
    private String login;
    private String senha;
    private String perFilUser;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;



    //CONSTRUTOR (vazio)
    public Pessoa(){

    }

    //CONSTRUTOR (com argumentos)
    public Pessoa(Long id, String nome, String sobrenome, Integer idade, Date dataNascimento, String sexo, String[] frameworks, Boolean ativo, String login, String senha, String perFilUser) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idade = idade;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.frameworks = frameworks;
        this.ativo = ativo;
        this.login = login;
        this.senha = senha;
        this.perFilUser = perFilUser;
    }



    //METODOS ESPECIAIS
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
    public String getSobrenome() {
        return sobrenome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public Integer getIdade() {
        return idade;
    }
    public void setIdade(Integer idade) {
        this.idade = idade;
    }
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public String[] getFrameworks() {return frameworks;}
    public void setFrameworks(String[] frameworks) {
        this.frameworks = frameworks;
    }
    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    public Date getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getPerFilUser() {
        return perFilUser;
    }
    public void setPerFilUser(String perFilUser) {
        this.perFilUser = perFilUser;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
