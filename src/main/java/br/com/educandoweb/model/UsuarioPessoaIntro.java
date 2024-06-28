package br.com.educandoweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


//MInha classe tem que estar com a anotação "@Entity" para informar que será uma tabela do banco de dados
@Entity
public class UsuarioPessoaIntro {


    @Id //para gerar sempre com o numero id
    @GeneratedValue(strategy = GenerationType.AUTO) //para não dar conflito de insert repetido
    private Long id;

    //Atributos
    private String nome;
    private String sobrenome;
    private String email;
    private String login;
    private String senha;
    private int idade;


    //Metodos especiais
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "UsuarioPessoaIntro{" +
                "\n id= " + id +
                "\n nome= " + nome
                + "\n sobrenome= " + sobrenome
                + "\n email= " + email
                + "\n login= " + login
                + "\n senha= " + senha
                + "\n idade= " + idade
                + "\n"
                +'}';
    }
}
