package br.com.educandoweb.converter;

import br.com.educandoweb.entities.Cidades;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.io.Serializable;

@FacesConverter(forClass = Cidades.class, value = "cidadeConverter")
public class CidadeConverter implements Converter, Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager entityManager;


    //RETORNA O OBJETO INTEIRO
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String codigoCidade) {

        EntityManager entityManager = CDI.current().select(EntityManager.class).get();

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
