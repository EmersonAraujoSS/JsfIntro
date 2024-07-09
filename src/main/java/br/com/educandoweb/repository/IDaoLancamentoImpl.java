package br.com.educandoweb.repository;

import br.com.educandoweb.entities.Lancamento;
import br.com.educandoweb.jpautil.JPAutil;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.util.List;

public class IDaoLancamentoImpl implements IDaoLancamento{


    @Override
    public List<Lancamento> consultar(Long codUser) {

        List<Lancamento> lista = null;

        EntityManager entityManager = JPAutil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        lista = entityManager.createQuery("from Lancamento where usuario.id = " + codUser).getResultList();

        transaction.commit();
        entityManager.close();

        return lista;
    }
}
