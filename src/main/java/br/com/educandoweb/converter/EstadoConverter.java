package br.com.educandoweb.converter;

import br.com.educandoweb.entities.Estados;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.io.Serializable;


@FacesConverter(forClass = Estados.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager entityManager;


    @Override //RETORNA O OBJETO INTEIRO
    public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {

        EntityManager entityManager = CDI.current().select(EntityManager.class).get();

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
