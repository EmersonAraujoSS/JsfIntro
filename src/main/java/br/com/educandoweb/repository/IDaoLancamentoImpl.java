package br.com.educandoweb.repository;

import br.com.educandoweb.entities.Lancamento;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

@Named
public class IDaoLancamentoImpl implements IDaoLancamento, Serializable {
    private static final long serialVersionUID = 1L;


    //Atributo
    @Inject
    private EntityManager entityManager;


    @Override
    public List<Lancamento> consultar(Long codUser) {

        List<Lancamento> lista = null;

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        lista = entityManager.createQuery("from Lancamento where usuario.id = " + codUser).getResultList();

        transaction.commit();
        entityManager.close();

        return lista;
    }
}
