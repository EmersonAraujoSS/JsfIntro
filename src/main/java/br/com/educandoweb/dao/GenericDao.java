package br.com.educandoweb.dao;

import br.com.educandoweb.entities.Pessoa;
import br.com.educandoweb.jpautil.JPAutil;
import javax.persistence.EntityTransaction;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


public class GenericDao <E>{


    private EntityManager entityManager = JPAutil.getEntityManager(); //aqui estou estanciando minha conexão com o banco de dados e passsando o metodo que faz update
                                                                            //tudo o que tem no DAO é para acessar o banco de dados

    //Metodo
    public void salvar(E entity){
        EntityManager entityManager = JPAutil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();  //instanciando minha transação que vai fazer as modificações no banco de dados
        entityTransaction.begin(); // iniciando minha transacao

        entityManager.persist(entity); // persist = esse persist que é o comando usado para salvar
        entityTransaction.commit();
        entityManager.close();

    }


    public E merge(E entity){
        EntityManager entityManager = JPAutil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        E retorno = entityManager.merge(entity);
        entityTransaction.commit();
        entityManager.close();

        return retorno;
    }


    public void deletePorId(E entity){
        EntityManager entityManager = JPAutil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Object id = JPAutil.getPrimaryKey(entity);
        entityManager.createQuery("delete from "+entity.getClass().getCanonicalName() + " where id = " + id).executeUpdate();

        entityTransaction.commit();
        entityManager.close();

    }


    public List<E> getListEntity(Class<E> entity) {
        EntityManager entityManager = JPAutil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        List<E> retornoList = entityManager.createQuery("from "+entity.getName()).getResultList();

        entityTransaction.commit();
        entityManager.close();

        return retornoList;

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

        Object id = JPAutil.getPrimaryKey(entity);

        E e = (E) entityManager.find(entity.getClass(), id);

        return e;
    }


   // public void deletePorId(E entity){

      //  Object id = JPAutil.getPrimaryKey(entity);

     //   EntityTransaction transaction = entityManager.getTransaction();
       // transaction.begin();
      //  entityManager.createNativeQuery("delete from "+entity.getClass().getSimpleName().toLowerCase()+" where id="+id).executeUpdate();
     //   transaction.commit();
 //   }


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


    public E consultar(Class<E> entity, String codigo){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        E Object = (E) entityManager.find(entity, Long.parseLong(codigo));
        entityTransaction.commit();

        return Object;
    }



    public EntityManager getEntityManager() {
        return entityManager;
    }
}
