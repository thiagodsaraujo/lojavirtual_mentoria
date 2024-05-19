package dev.mentoria.lojavirtual_mentoria.model;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "cup_desc")
public class CupomDesconto implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(nullable = false)
    @NotEmpty(message = "Código do cupom não pode ser vazio")
    private String codigoCupom;


    private BigDecimal valorRealDesconto;

    private BigDecimal valorPorcentDesconto;

    @NotEmpty(message = "Data de validade do cupom não pode ser vazia")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataValidadeCupom;

    @ManyToOne(targetEntity = Pessoa.class)
    @NotEmpty(message = "Empresa não pode ser vazia, por gentileza adicione o cupom a uma empresa")
    @JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
    private Pessoa empresa;


    public Pessoa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Pessoa empresa) {
        this.empresa = empresa;
    }

    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCodigoCupom() {
        return codigoCupom;
    }

    public void setCodigoCupom(String codigoCupom) {
        this.codigoCupom = codigoCupom;
    }

    public BigDecimal getValorRealDesconto() {
        return valorRealDesconto;
    }

    public void setValorRealDesconto(BigDecimal valorRealDesconto) {
        this.valorRealDesconto = valorRealDesconto;
    }

    public BigDecimal getValorPorcentDesconto() {
        return valorPorcentDesconto;
    }

    public void setValorPorcentDesconto(BigDecimal valorPorcentDesconto) {
        this.valorPorcentDesconto = valorPorcentDesconto;
    }

    public Date getDataValidadeCupom() {
        return dataValidadeCupom;
    }

    public void setDataValidadeCupom(Date dataValidadeCupom) {
        this.dataValidadeCupom = dataValidadeCupom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CupomDesconto that)) return false;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
