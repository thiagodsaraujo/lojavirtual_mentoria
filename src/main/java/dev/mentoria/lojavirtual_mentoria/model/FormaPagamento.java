package dev.mentoria.lojavirtual_mentoria.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "forma_pagamento")
public class FormaPagamento implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "A descrição da forma de pagamento não pode ser nula")
    private String descricao;


    @ManyToOne(targetEntity = PessoaJuridica.class)
    @NotNull(message = "A empresa não pode ser nula")
    @JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
    private PessoaJuridica empresa;


    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }

    //

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormaPagamento that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return getDescricao() != null ? getDescricao().equals(that.getDescricao()) : that.getDescricao() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
