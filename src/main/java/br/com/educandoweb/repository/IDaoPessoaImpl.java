package br.com.educandoweb.repository;

import br.com.educandoweb.entities.Estados;
import br.com.educandoweb.entities.Pessoa;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
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
}
