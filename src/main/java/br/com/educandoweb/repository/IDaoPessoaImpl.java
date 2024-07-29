package br.com.educandoweb.repository;

import br.com.educandoweb.entities.Estados;
import br.com.educandoweb.entities.Lancamento;
import br.com.educandoweb.entities.Pessoa;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Named
public class IDaoPessoaImpl implements IDaoPessoa, Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager entityManager;

    @Override
    public Pessoa consultarUsuario(String login, String senha) {

        Pessoa pessoa = null;

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            pessoa = (Pessoa) entityManager.createQuery("select p from Pessoa p where p.login = '" + login + "' and p.senha = '" + senha + "'").getSingleResult();
            }
            catch (javax.persistence.NoResultException e) { //se não encontrar usuario com login e senha
            }

        entityTransaction.commit();

        return pessoa;
    }



    @Override
    public List<SelectItem> listaEstados() {

        List<SelectItem> selectItems = new ArrayList<SelectItem>();

        List<Estados> estados = entityManager.createQuery("from Estados").getResultList();

        for (Estados estado : estados){
            selectItems.add(new SelectItem(estado, estado.getNome()));
        }

        return selectItems;
    }



    @Override
    public List<Pessoa> relatorioPessoa(String nome, Date dataIni, Date dataFim) {

        List<Pessoa> pessoaList = new ArrayList<Pessoa>();

        StringBuilder sql = new StringBuilder();  //StringBuilder = Utilizado para construir a query JPQL dinamicamente.

        sql.append(" select l from Pessoa l ");

        if (dataIni == null && dataFim == null && nome != null && !nome.isEmpty()) {
            sql.append(" where upper(l.nome) like '%").append(nome.trim().toUpperCase()).append("%'");
        }
        else if (nome == null || (nome != null && nome.isEmpty()) && dataIni != null && dataFim == null) {

            String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
            sql.append(" where l.dataNascimento >= '").append(dataIniString).append("'");
        }
        else if (nome == null || (nome != null && nome.isEmpty()) && dataIni == null && dataFim != null) {

            String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
            sql.append(" where l.dataNascimento <= '").append(dataFimString).append("'");
        }
        else if (nome == null || (nome != null && nome.isEmpty()) && dataIni != null && dataFim != null){

            String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
            String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
            sql.append(" where l.dataNascimento >= '").append(dataIniString).append("' ");
            sql.append(" and l.dataNascimento <= '").append(dataFimString).append("' ");
        }
        else if (nome != null && !nome.isEmpty() && dataIni != null && dataFim != null) {

            String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
            String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
            sql.append(" where l.dataNascimento >= '").append(dataIniString).append("' ");
            sql.append(" and l.dataNascimento <= '").append(dataFimString).append("' ");
            sql.append(" and upper(l.nome) like '%").append(nome.trim().toUpperCase()).append("%'");
        }

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        pessoaList = entityManager.createQuery(sql.toString()).getResultList();

        transaction.commit();

        return pessoaList;
    }

}
