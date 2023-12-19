package dev.mentoria.lojavirtual_mentoria;


import dev.mentoria.lojavirtual_mentoria.model.NotaFiscalCompra;
import dev.mentoria.lojavirtual_mentoria.model.Produto;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "nota_item_prod")
public class NotaItemProduto implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Double quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "produto_fk"))
    private Produto produto;


    @ManyToOne
    @JoinColumn(name = "nota_fiscal_compra_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "nota_fiscal_compra_fk"))
    private NotaFiscalCompra notaFiscalCompra;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public NotaFiscalCompra getNotaFiscalCompra() {
        return notaFiscalCompra;
    }

    public void setNotaFiscalCompra(NotaFiscalCompra notaFiscalCompra) {
        this.notaFiscalCompra = notaFiscalCompra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotaItemProduto that)) return false;

        if (!getId().equals(that.getId())) return false;
        if (getQuantidade() != null ? !getQuantidade().equals(that.getQuantidade()) : that.getQuantidade() != null)
            return false;
        if (getProduto() != null ? !getProduto().equals(that.getProduto()) : that.getProduto() != null) return false;
        return getNotaFiscalCompra() != null ? getNotaFiscalCompra().equals(that.getNotaFiscalCompra()) : that.getNotaFiscalCompra() == null;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
