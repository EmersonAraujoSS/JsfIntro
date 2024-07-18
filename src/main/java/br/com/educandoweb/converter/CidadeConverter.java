package br.com.educandoweb.converter;


import br.com.educandoweb.entities.Cidades;
import br.com.educandoweb.jpautil.JPAutil;
import javax.persistence.EntityTransaction;
import javax.persistence.EntityManager;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.io.Serializable;

@FacesConverter(forClass = Cidades.class, value = "cidadeConverter")
public class CidadeConverter implements Converter, Serializable {


    //RETORNA O OBJETO INTEIRO
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String codigoCidade) {

        EntityManager entityManager = JPAutil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Cidades cidade = (Cidades) entityManager.find(Cidades.class, Long.parseLong(codigoCidade));


        return cidade;
    }

    //RETORNA APENAS O CÓDIGO EM STRING
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object cidade) {

        if (cidade == null) {
            return null;
        }

        if (cidade instanceof Cidades){
            return ((Cidades)cidade).getId().toString();
        }else {
            return cidade.toString();
        }
    }
}
