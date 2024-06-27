package br.com.educandoweb;

import jakarta.inject.Named;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlCommandButton;
import java.util.ArrayList;
import java.util.List;



//@SessionScoped // essa anotação mantem os dados na tela quando eu estou na mesma sesao
@ViewScoped // essa anotação mantem os dados na tela enquanto o usuário estiver logado na tela
//@ApplicationScoped //ele vai manter os dados para todos os usuários (compartilha os dados com outros usuarios) (mesmo abrindo a url em outro navegador)
@ManagedBean // meu ManagedBean conecta o meu Bean a minha pagina xhtml
@Named(value = "pessoaBean")

public class PessoaBean {

    //Atributos
    private String nome;
    private String senha;
    private String texto;

    private HtmlCommandButton commandButton; // controlando meu commandButton no meu manageBean
    private List<String> listNomes = new ArrayList<>();


    //Metodo
    public String addNome(){
        listNomes.add(nome);

        if (listNomes.size() > 3){ // leitura = se minha lista de nomes for maior que tres, eu desabilito o botão
            commandButton.setDisabled(true);
            return "pagenavegada?faces-redirect=true";  // assim que ele passar de tres nomes ele desabilita e me redireciona para essa pagina
        }                                                   //NAVEGACAO DINAMICA
                                                                       // ?faces-redirect=true = uso para redirecionar minha url na pagina
        return""; //retornado null ou vazio, ele fica na mesma pagina
    }


    //Metodos especiais
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<String> getListNomes() {
        return listNomes;
    }
    public void setListNomes(List<String> listNomes) {
        this.listNomes = listNomes;
    }
    public HtmlCommandButton getCommandButton() {
        return commandButton;
    }
    public void setCommandButton(HtmlCommandButton commandButton) {
        this.commandButton = commandButton;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
}
