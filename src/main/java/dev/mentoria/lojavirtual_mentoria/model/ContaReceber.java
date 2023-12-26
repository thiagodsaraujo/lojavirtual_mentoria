package dev.mentoria.lojavirtual_mentoria.model;


import dev.mentoria.lojavirtual_mentoria.model.enums.StatusContaReceber;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "conta_receber")
public class ContaReceber implements Serializable {
    // Pega o nome da calsse para o relacionamento interno dele
    // Muitas contas para receber para uma pessoa - muitos para um


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusContaReceber status;


    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dtVencimento;


    @Temporal(TemporalType.DATE)
    private Date dtPagamento;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    private BigDecimal valorDesconto;


    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusContaReceber getStatus() {
        return status;
    }

    public void setStatus(StatusContaReceber status) {
        this.status = status;
    }

    public Date getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Date getDtPagamento() {
        return dtPagamento;
    }

    public void setDtPagamento(Date dtPagamento) {
        this.dtPagamento = dtPagamento;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }


    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
