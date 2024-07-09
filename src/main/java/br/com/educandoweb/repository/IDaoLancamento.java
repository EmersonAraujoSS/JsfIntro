package br.com.educandoweb.repository;

import br.com.educandoweb.entities.Lancamento;

import java.util.List;

public interface IDaoLancamento {

    List<Lancamento> consultar(Long codUser);
}
