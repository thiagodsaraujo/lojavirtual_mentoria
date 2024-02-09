package dev.mentoria.lojavirtual_mentoria.model;



import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "nota_fiscal_compra")
public class NotaFiscalCompra implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "Número da Nota é obrigatório")
    @Column(nullable = false, unique = true)
    private String numeroNota;

    @NotNull(message = "Série da Nota é obrigatório")
    @Column(nullable = false)
    private String serieNota;

    private String descricaoObs;

    @NotNull(message = "Valor Total da Nota é obrigatório")
    @Column(nullable = false)
    private BigDecimal valorTotal;


    private BigDecimal valorDesconto;

    @NotNull(message = "Valor do ICMS é obrigatório")
    @Column(nullable = false)
    private BigDecimal valorIcms;

    @NotNull(message = "Data da compra é obrigatória")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private Date dataCompra;


    // validar esses atributos no controller, não fica mto compatível criar validações aqui
    //usamos o targetEntity aqui para ajudar o JPA a saber qual classe de destino pois é uma herança
    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
    private PessoaJuridica pessoa;


    @ManyToOne
    @JoinColumn(name = "conta_pagar_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "conta_pagar_fk"))
    private ContaPagar contaPagar;


    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
    private PessoaJuridica empresa;


    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }

    //


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }

    public String getSerieNota() {
        return serieNota;
    }

    public void setSerieNota(String serieNota) {
        this.serieNota = serieNota;
    }

    public String getDescricaoObs() {
        return descricaoObs;
    }

    public void setDescricaoObs(String descricaoObs) {
        this.descricaoObs = descricaoObs;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorIcms() {
        return valorIcms;
    }

    public void setValorIcms(BigDecimal valorIcms) {
        this.valorIcms = valorIcms;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaJuridica pessoa) {
        this.pessoa = pessoa;
    }

    public ContaPagar getContaPagar() {
        return contaPagar;
    }

    public void setContaPagar(ContaPagar contaPagar) {
        this.contaPagar = contaPagar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotaFiscalCompra that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getNumeroNota() != null ? !getNumeroNota().equals(that.getNumeroNota()) : that.getNumeroNota() != null)
            return false;
        if (getSerieNota() != null ? !getSerieNota().equals(that.getSerieNota()) : that.getSerieNota() != null)
            return false;
        if (getDescricaoObs() != null ? !getDescricaoObs().equals(that.getDescricaoObs()) : that.getDescricaoObs() != null)
            return false;
        if (getValorTotal() != null ? !getValorTotal().equals(that.getValorTotal()) : that.getValorTotal() != null)
            return false;
        if (getValorDesconto() != null ? !getValorDesconto().equals(that.getValorDesconto()) : that.getValorDesconto() != null)
            return false;
        if (getValorIcms() != null ? !getValorIcms().equals(that.getValorIcms()) : that.getValorIcms() != null)
            return false;
        if (getDataCompra() != null ? !getDataCompra().equals(that.getDataCompra()) : that.getDataCompra() != null)
            return false;
        if (getPessoa() != null ? !getPessoa().equals(that.getPessoa()) : that.getPessoa() != null) return false;
        return getContaPagar() != null ? getContaPagar().equals(that.getContaPagar()) : that.getContaPagar() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
