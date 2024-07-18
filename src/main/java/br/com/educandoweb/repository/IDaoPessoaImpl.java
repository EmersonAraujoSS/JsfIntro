package br.com.educandoweb.repository;

import br.com.educandoweb.entities.Estados;
import br.com.educandoweb.entities.Pessoa;
import br.com.educandoweb.jpautil.JPAutil;
import javax.faces.model.SelectItem;
import javax.persistence.EntityTransaction;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class IDaoPessoaImpl implements IDaoPessoa{


    @Override
    public Pessoa consultarUsuario(String login, String senha) {

        Pessoa pessoa = null;

        EntityManager entityManager = JPAutil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        pessoa = (Pessoa) entityManager.createQuery("select p from Pessoa p where p.login = '" + login + "' and p.senha = '" + senha + "'").getSingleResult();

        entityTransaction.commit();
        entityManager.close();

        return pessoa;
    }



    @Override
    public List<SelectItem> listaEstados() {

        List<SelectItem> selectItems = new ArrayList<SelectItem>();

        EntityManager entityManager = JPAutil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        List<Estados> estados = entityManager.createQuery("from Estados").getResultList();

        for (Estados estado : estados){
            selectItems.add(new SelectItem(estado, estado.getNome()));
        }

        return selectItems;
    }
}
