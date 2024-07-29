package br.com.educandoweb;

import br.com.educandoweb.dao.GenericDao;
import br.com.educandoweb.entities.Lancamento;
import br.com.educandoweb.repository.IDaoLancamento;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ViewScoped // essa anotação mantem os dados na tela enquanto o usuário estiver logado na tela
@Named(value = "relLancamento")
public class RelLancamentoBean implements Serializable {
    private static final long serialVersionUID = 1L;


    //Atributos
    private Date dataIni;
    private Date dataFim;
    private String numNota;
    private List<Lancamento> lancamentosList = new ArrayList<>();

    @Inject
    private IDaoLancamento daoLancamento;

    @Inject
    private GenericDao<Lancamento> genericDao;




    //Métodos
    public void buscarLancamentos() {
        if (dataIni == null && dataFim == null && numNota == null) {
            lancamentosList = genericDao.getListEntity(Lancamento.class);
        }
        else {
            lancamentosList = daoLancamento.relatorioLancamento(numNota, dataIni, dataFim);
        }

    }



    //Métodos especiais
    public List<Lancamento> getLancamentosList() {
        return lancamentosList;
    }
    public void setLancamentosList(List<Lancamento> lancamentosList) {
        this.lancamentosList = lancamentosList;
    }
    public Date getDataIni() {
        return dataIni;
    }
    public void setDataIni(Date dataIni) {
        this.dataIni = dataIni;
    }
    public Date getDataFim() {
        return dataFim;
    }
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    public String getNumNota() {
        return numNota;
    }
    public void setNumNota(String numNota) {
        this.numNota = numNota;
    }
}
