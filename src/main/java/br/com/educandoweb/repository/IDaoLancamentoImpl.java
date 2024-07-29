package br.com.educandoweb.repository;

import br.com.educandoweb.entities.Lancamento;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.lang.invoke.LambdaConversionException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
public class IDaoLancamentoImpl implements IDaoLancamento, Serializable {
    private static final long serialVersionUID = 1L;


    //Atributo
    @Inject
    private EntityManager entityManager;



    //Metodos
    @Override
    public List<Lancamento> consultarLimite10(Long codUser){

        List<Lancamento> lista = null;

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        lista = entityManager.createQuery("from Lancamento where usuario.id = " + codUser + " order by id desc ").setMaxResults(10).getResultList();

        transaction.commit();
        entityManager.close();

        return lista;
    }


    @Override
    public List<Lancamento> consultar(Long codUser) {

        List<Lancamento> lista = null;

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        lista = entityManager.createQuery("from Lancamento where usuario.id = " + codUser).getResultList();

        transaction.commit();
        entityManager.close();

        return lista;
    }


    @Override
    public List<Lancamento> relatorioLancamento(String numNota, Date dataIni, Date dateFim) {

        List<Lancamento> lancamentoList = new ArrayList<Lancamento>();

        StringBuilder sql = new StringBuilder();  //StringBuilder = Utilizado para construir a query JPQL dinamicamente.

        sql.append(" select l from Lancamento l ");

        if (dataIni == null && dateFim == null && numNota != null && !numNota.isEmpty()) {
            sql.append(" where l.numeroNotaFiscal = '").append(numNota.trim()).append("'");
        }
        else if (numNota == null || (numNota != null && numNota.isEmpty()) && dataIni != null && dateFim == null) {

            String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
            sql.append(" where l.dataInicial >= '").append(dataIniString).append("'");
        }
        else if (numNota == null || (numNota != null && numNota.isEmpty()) && dataIni == null && dateFim != null) {

            String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dateFim);
            sql.append(" where l.dataFim <= '").append(dataFimString).append("'");
        }
        else if (numNota == null || (numNota != null && numNota.isEmpty()) && dataIni != null && dateFim != null){

            String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
            String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dateFim);
            sql.append(" where l.dataInicial >= '").append(dataIniString).append("' ");
            sql.append(" and l.dataFim <= '").append(dataFimString).append("' ");
        }
        else if (numNota != null && !numNota.isEmpty() && dataIni != null && dateFim != null) {

            String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
            String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dateFim);
            sql.append(" where l.dataInicial >= '").append(dataIniString).append("' ");
            sql.append(" and l.dataFim <= '").append(dataFimString).append("' ");
            sql.append(" and l.numeroNotaFiscal = '").append(numNota.trim()).append("'");
        }

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        lancamentoList = entityManager.createQuery(sql.toString()).getResultList();

        transaction.commit();

        return lancamentoList;
    }
}
