package br.com.educandoweb.jpautil;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@ApplicationScoped //é uma instancia só para o projeto inteiro(compartilha os objetos para todos os usuários do sistema)
public class JPAutil {

    //essa classe que vai ser responsavel pela minha conexão com o banco de dados
    private EntityManagerFactory factory = null;


    //CONSTRUTOR
    public JPAutil() {
        if (factory == null){
            factory = Persistence.createEntityManagerFactory("jsfIntro");
        }
    }


    //Metodo
    @Produces
    @RequestScoped
    public EntityManager getEntityManager(){
        return factory.createEntityManager(); //prover a parte de persistencia do banco de dados (alterações do banco de dados)
    }


    //Metodo
    public Object getPrimaryKey(Object entity){
        return factory.getPersistenceUnitUtil().getIdentifier(entity);  //esse método retorna a primary key
    }

}
