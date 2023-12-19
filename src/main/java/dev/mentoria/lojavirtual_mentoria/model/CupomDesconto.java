package dev.mentoria.lojavirtual_mentoria.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "cupom")
public class CupomDesconto implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    private String codigoCupom;

    private BigDecimal valorRealDesconto;

    private BigDecimal valorPorcentDesconto;

    @Temporal(TemporalType.DATE)
    private Date dataValidadeCupom;



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
