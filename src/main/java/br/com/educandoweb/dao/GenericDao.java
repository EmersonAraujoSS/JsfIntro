package br.com.educandoweb.dao;

import br.com.educandoweb.HibernateUtil;
import javax.persistence.EntityTransaction;
import javax.persistence.EntityManager;
import java.util.List;


public class GenericDao <E>{


    private EntityManager entityManager = HibernateUtil.getEntityManager(); //aqui estou estanciando minha conexão com o banco de dados e passsando o metodo que faz update


    //Metodo
    public void salvar(E entity){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();

    }

    //Metodo
    public E updateMerge(E entity){  //método que salva e atualiza no banco de dados
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        E entitySalva = entityManager.merge(entity);
        transaction.commit();

        return entitySalva;
    }



    public E pesquisar(E entity){

        Object id = HibernateUtil.getPrimaryKey(entity);

        E e = (E) entityManager.find(entity.getClass(), id);

        return e;
    }


    public void deletePorId(E entity){

        Object id = HibernateUtil.getPrimaryKey(entity);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createNativeQuery("delete from "+entity.getClass().getSimpleName().toLowerCase()+" where id="+id).executeUpdate();
        transaction.commit();
    }


    public E pesquisar(Long id, Class<E> entity){
        E e = (E) entityManager.find(entity, id);

        return e;
    }


    public List<E> listar(Class<E> entity){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<E> lista = entityManager.createQuery("from " + entity.getName()).getResultList();
        transaction.commit();

        return lista;
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }
}
