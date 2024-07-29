package br.com.educandoweb.repository;

import br.com.educandoweb.entities.Lancamento;

import java.util.Date;
import java.util.List;

public interface IDaoLancamento {

    List<Lancamento> consultar(Long codUser);

    List<Lancamento> consultarLimite10(Long codUser);

    List<Lancamento> relatorioLancamento(String numNota, Date dataIni, Date dateFim);
}
