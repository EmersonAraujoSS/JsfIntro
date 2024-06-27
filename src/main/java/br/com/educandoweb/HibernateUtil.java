package br.com.educandoweb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class HibernateUtil {

    //essa classe que vai ser responsavel pela minha conexão com o banco de dados
    public static EntityManagerFactory factory = null;


    //Metodo
    static {
        init();
    }


    //Metodo
    private static void init(){

        try {

            if (factory == null){
                factory = Persistence.createEntityManagerFactory("jsfIntro");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Metodo
    public static EntityManager getEntityManager(){
        return factory.createEntityManager(); //prover a parte de persistencia do banco de dados (alterações do banco de dados)
    }


    //Metodo
    public static Object getPrimaryKey(Object entity){
        return factory.getPersistenceUnitUtil().getIdentifier(entity);  //esse método retorna a primary key
    }

}
