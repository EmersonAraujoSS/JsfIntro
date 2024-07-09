package br.com.educandoweb;

import br.com.educandoweb.dao.GenericDao;
import br.com.educandoweb.entities.Lancamento;
import br.com.educandoweb.entities.Pessoa;
import br.com.educandoweb.repository.IDaoLancamento;
import br.com.educandoweb.repository.IDaoLancamentoImpl;


import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;


@ViewScoped
@ManagedBean // meu ManagedBean conecta o meu Bean a minha pagina xhtml
@Named(value = "lancamentoBean")
public class LancamentoBean {

    //ATRIBUTOS
    private Lancamento lancamento = new Lancamento();
    private GenericDao<Lancamento> genericDao = new GenericDao();
    private List<Lancamento> lancamentoList = new ArrayList<>();
    private IDaoLancamento daoLancamento = new IDaoLancamentoImpl();


    // MÉTODOS
    public String salvar(){

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");

        lancamento.setUsuario(pessoaUser);
        genericDao.merge(lancamento);
        carregarLancamentos();

        return "";
    }

    @PostConstruct //essa anotação indica que sempre que for carregado em memória, ele irá carregar a minha lista
    private void carregarLancamentos(){

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");

        lancamentoList = daoLancamento.consultar(pessoaUser.getId());
    }


    public String novo(){
        lancamento = new Lancamento();

        return "";
    }


    public String remove(){
        genericDao.deletePorId(lancamento);
        lancamento = new Lancamento();
        carregarLancamentos();
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
