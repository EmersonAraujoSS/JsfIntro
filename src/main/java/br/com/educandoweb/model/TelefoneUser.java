package br.com.educandoweb.model;


import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;


@Entity
public class TelefoneUser {


    //ATRIBUTOS
    @Id  //para gerar sempre com o numero id
    @GeneratedValue(strategy = GenerationType.AUTO)  //para não dar conflito de insert repetido
    private Long id;

    @Column(nullable = false)
    private String tipo;
    @Column(nullable = false) // nullable = o campo de telefone não pode ser nulo
    private String numero;

    @ManyToOne (optional = false, fetch = FetchType.EAGER)  //estanciacao de muitos telefones para uma pessoa, por isso estanciei a minha classe
    private UsuarioPessoaIntro usuarioPessoaIntro;              //FetchType.EAGER, o relacionamento é carregado imediatamente junto com a entidade principal.



    //METODOS ESPECIAIS
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public UsuarioPessoaIntro getUsuarioPessoaIntro() {
        return usuarioPessoaIntro;
    }
    public void setUsuarioPessoaIntro(UsuarioPessoaIntro usuarioPessoaIntro) {
        this.usuarioPessoaIntro = usuarioPessoaIntro;
    }
}
