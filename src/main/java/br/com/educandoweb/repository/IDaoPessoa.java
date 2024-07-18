package br.com.educandoweb.repository;

import br.com.educandoweb.entities.Pessoa;

import javax.faces.model.SelectItem;
import java.util.List;

public interface IDaoPessoa {

    Pessoa consultarUsuario(String login, String senha);

    List<SelectItem> listaEstados();
}
