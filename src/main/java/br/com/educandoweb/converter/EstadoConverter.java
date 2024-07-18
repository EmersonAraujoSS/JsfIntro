package br.com.educandoweb.converter;


import br.com.educandoweb.entities.Estados;
import br.com.educandoweb.jpautil.JPAutil;
import javax.persistence.EntityTransaction;
import javax.persistence.EntityManager;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.io.Serializable;


@FacesConverter(forClass = Estados.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable {


    @Override //RETORNA O OBJETO INTEIRO
    public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {

        EntityManager entityManager = JPAutil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Estados estados = (Estados) entityManager.find(Estados.class, Long.parseLong(codigoEstado));

        return estados;
    }

    @Override //RETORNA APENAS O CÓDIGO EM STRING
    public String getAsString(FacesContext context, UIComponent component, Object estado) {

        if (estado == null) {
            return null;
        }

        if (estado instanceof Estados){
            return ((Estados) estado).getId().toString();
        }else {
            return estado.toString();
        }
    }
}
