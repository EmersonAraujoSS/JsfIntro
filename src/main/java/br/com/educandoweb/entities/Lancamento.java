package br.com.educandoweb.entities;


import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.ForeignKey;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
public class Lancamento implements Serializable {
    private static final long serialVersionUID = 1L;


    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //para não dar conflito de insert repetido
    private Long id;

    private String numeroNotaFiscal;
    private String empresaOrigem;
    private String empresaDestino;

    @Temporal(TemporalType.DATE)
    private Date dataInicial;
    @Temporal(TemporalType.DATE)
    private Date dataFim;


    @ManyToOne(optional = false) //uma pessoa pode fazer vários lancamentos
    @ForeignKey(name = "usuario_fk")
    private Pessoa usuario;


    //CONSTRUTOR
    public Lancamento() {

    }


    //MÉTODOS ESPECIAS
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }
    public void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }
    public String getEmpresaOrigem() {
        return empresaOrigem;
    }
    public void setEmpresaOrigem(String empresaOrigem) {
        this.empresaOrigem = empresaOrigem;
    }
    public String getEmpresaDestino() {
        return empresaDestino;
    }
    public void setEmpresaDestino(String empresaDestino) {
        this.empresaDestino = empresaDestino;
    }
    public Pessoa getUsuario() {
        return usuario;
    }
    public void setUsuario(Pessoa usuario) {
        this.usuario = usuario;
    }
    public Date getDataInicial() {
        return dataInicial;
    }
    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }
    public Date getDataFim() {
        return dataFim;
    }
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lancamento that = (Lancamento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
