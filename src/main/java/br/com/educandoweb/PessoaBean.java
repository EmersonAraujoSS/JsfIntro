package br.com.educandoweb;

import br.com.educandoweb.dao.GenericDao;
import br.com.educandoweb.entities.Pessoa;


import br.com.educandoweb.repository.IDaoPessoa;
import br.com.educandoweb.repository.IDaoPessoaImpl;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;



//@SessionScoped // essa anotação mantem os dados na tela quando eu estou na mesma sesao
@ViewScoped // essa anotação mantem os dados na tela enquanto o usuário estiver logado na tela
//@ApplicationScoped //ele vai manter os dados para todos os usuários (compartilha os dados com outros usuarios) (mesmo abrindo a url em outro navegador)
@ManagedBean // meu ManagedBean conecta o meu Bean a minha pagina xhtml
@Named(value = "pessoaBean")

public class PessoaBean {

    //Atributos
    private Pessoa pessoa = new Pessoa();
    private GenericDao<Pessoa> genericDao = new GenericDao<Pessoa>();
    private List<Pessoa> pessoasList = new ArrayList<>();
    private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();


    //Metodos
    public String salvar() {
        pessoa = genericDao.merge(pessoa);
        carregarPessoas();

        return "";
    }


    public String novo(){
        pessoa = new Pessoa();

        return "";
    }

    public String remove(){
        genericDao.deletePorId(pessoa);
        pessoa = new Pessoa();
        carregarPessoas();

        return "";
    }

    @PostConstruct  //essa anotação indica que sempre que for carregado em memória, ele irá carregar a minha lista
    public void carregarPessoas(){
        pessoasList = genericDao.getListEntity(Pessoa.class);
    }


    public String logar(){

        Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

        if(pessoaUser != null){

            //adicionar o usuário na sessão usuarioLogado
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.getSessionMap().put("usuarioLogado", pessoaUser);

            return "firstpage.xhtml";
        }

        return "index.xhtml";
    }


    public boolean permiteAcesso(String acesso){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");

        return pessoaUser.getPerFilUser().equals(acesso);
    }



    //Metodos especias
    public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public GenericDao<Pessoa> getGenericDao() {
        return genericDao;
    }
    public void setGenericDao(GenericDao<Pessoa> genericDao) {
        this.genericDao = genericDao;
    }
    public List<Pessoa> getPessoasList() {
        return pessoasList;
    }
    public void setPessoasList(List<Pessoa> pessoasList) {
        this.pessoasList = pessoasList;
    }
}
