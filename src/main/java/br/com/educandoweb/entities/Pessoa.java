package br.com.educandoweb.entities;

import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

//MInha classe tem que estar com a anotação "@Entity" para informar que será uma tabela do banco de dados
@Entity
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //para não dar conflito de insert repetido
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
    private String nivelProgramador;
    private Integer[] linguagens; //coloquei as linguagens no array porque eu posso selecionar várias linguagens com o "selectManyCheckbox" ná minha firstpage
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;

    @Transient //Transient = não cria essa coluna no banco de dados, ele só cria o objeto em memória
    private Estados estados;

    @ManyToOne // uma cidade pode ter várias pessoas
    private Cidades cidades;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento = new Date();



    //CONSTRUTOR (vazio)
    public Pessoa(){

    }

    //CONSTRUTOR (com argumentos)
    public Pessoa(Long id, String nome, String sobrenome, Integer idade, String sexo, String[] frameworks, Boolean ativo, String login, String senha, String perFilUser, String nivelProgramador, Integer[] linguagens, String cep, String logradouro, String complemento, String bairro, String localidade, String uf, String unidade, String ibge, String gia, Date dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idade = idade;
        this.sexo = sexo;
        this.frameworks = frameworks;
        this.ativo = ativo;
        this.login = login;
        this.senha = senha;
        this.perFilUser = perFilUser;
        this.nivelProgramador = nivelProgramador;
        this.linguagens = linguagens;
        this.cep = cep;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.unidade = unidade;
        this.ibge = ibge;
        this.gia = gia;
        this.dataNascimento = dataNascimento;
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
    public String getNivelProgramador() {
        return nivelProgramador;
    }
    public void setNivelProgramador(String nivelProgramador) {
        this.nivelProgramador = nivelProgramador;
    }
    public Integer[] getLinguagens() {
        return linguagens;
    }
    public void setLinguagens(Integer[] linguagens) {
        this.linguagens = linguagens;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getLocalidade() {
        return localidade;
    }
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }
    public String getUnidade() {
        return unidade;
    }
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
    public String getIbge() {
        return ibge;
    }
    public void setIbge(String ibge) {
        this.ibge = ibge;
    }
    public String getGia() {
        return gia;
    }
    public void setGia(String gia) {
        this.gia = gia;
    }
    public Estados getEstados() {
        return estados;
    }
    public void setEstados(Estados estados) {
        this.estados = estados;
    }
    public Cidades getCidades() {
        return cidades;
    }
    public void setCidades(Cidades cidades) {
        this.cidades = cidades;
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
