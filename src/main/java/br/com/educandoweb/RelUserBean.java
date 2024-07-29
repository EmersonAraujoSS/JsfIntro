package br.com.educandoweb;

import br.com.educandoweb.dao.GenericDao;
import br.com.educandoweb.entities.Lancamento;
import br.com.educandoweb.entities.Pessoa;
import br.com.educandoweb.repository.IDaoPessoa;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ViewScoped // essa anotação mantem os dados na tela enquanto o usuário estiver logado na tela
@Named(value = "relUser")
public class RelUserBean implements Serializable {
    private static final long serialVersionUID = 1L;


    //Atributos
    private List<Pessoa> pessoasList = new ArrayList<>();
    private Date dataIni;
    private Date dataFim;
    private String nome;
    @Inject
    private IDaoPessoa iDaoPessoa;
    @Inject
    private GenericDao<Pessoa> genericDao;



    //Métodos
    public void relPessoa(){
        if (dataIni == null && dataFim == null && nome == null){
            pessoasList = genericDao.getListEntity(Pessoa.class);
        }
        else {
            pessoasList = iDaoPessoa.relatorioPessoa(nome, dataIni, dataFim);
        }
    }



    //Métodos especiais
    public List<Pessoa> getPessoasList() {
        return pessoasList;
    }
    public void setPessoasList(List<Pessoa> pessoasList) {
        this.pessoasList = pessoasList;
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
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
