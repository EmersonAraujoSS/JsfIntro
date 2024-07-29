package br.com.educandoweb;

import br.com.educandoweb.dao.GenericDao;
import br.com.educandoweb.entities.Lancamento;
import br.com.educandoweb.entities.Pessoa;
import br.com.educandoweb.repository.IDaoLancamento;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//@ManagedBean // meu ManagedBean conecta o meu Bean a minha pagina xhtml
@ViewScoped
@Named(value = "lancamentoBean")
public class LancamentoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    //ATRIBUTOS
    private Lancamento lancamento = new Lancamento();
    private List<Lancamento> lancamentoList = new ArrayList<>();

    @Inject
    private GenericDao<Lancamento> genericDao;

    @Inject
    private IDaoLancamento daoLancamento;


    // MÉTODOS
    public String salvar(){

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");

        lancamento.setUsuario(pessoaUser);
        genericDao.merge(lancamento);
        carregarLancamentos();
        FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Salvo com sucesso"));

        return "";
    }

    @PostConstruct //essa anotação indica que sempre que for carregado em memória, ele irá carregar a minha lista
    private void carregarLancamentos(){

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");

        lancamentoList = daoLancamento.consultarLimite10(pessoaUser.getId());
    }


    public String novo(){
        lancamento = new Lancamento();

        return "";
    }


    public String remove(){
        genericDao.deletePorId(lancamento);
        lancamento = new Lancamento();
        carregarLancamentos();
        FacesContext.getCurrentInstance().addMessage("msg",new FacesMessage("Excluido com sucesso"));
        return "";
    }



    //MÉTODOS ESPECIAIS
    public Lancamento getLancamento() {
        return lancamento;
    }
    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }
    public GenericDao<Lancamento> getGenericDao() {
        return genericDao;
    }
    public void setGenericDao(GenericDao<Lancamento> genericDao) {
        this.genericDao = genericDao;
    }
    public List<Lancamento> getLancamentoList() {
        return lancamentoList;
    }
    public void setLancamentoList(List<Lancamento> lancamentoList) {
        this.lancamentoList = lancamentoList;
    }
}
