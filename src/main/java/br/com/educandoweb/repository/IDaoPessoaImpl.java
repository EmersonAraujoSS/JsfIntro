package br.com.educandoweb.repository;

import br.com.educandoweb.entities.Pessoa;
import br.com.educandoweb.jpautil.JPAutil;
import javax.persistence.EntityTransaction;
import javax.persistence.EntityManager;

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
}
