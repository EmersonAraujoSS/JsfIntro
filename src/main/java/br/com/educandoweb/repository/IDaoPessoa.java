package br.com.educandoweb.repository;

import br.com.educandoweb.entities.Pessoa;

public interface IDaoPessoa {

    Pessoa consultarUsuario(String login, String senha);
}
