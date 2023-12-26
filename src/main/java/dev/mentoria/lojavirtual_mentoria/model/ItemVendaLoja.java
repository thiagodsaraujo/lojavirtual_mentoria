package dev.mentoria.lojavirtual_mentoria.model;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "item_venda_loja")
public class ItemVendaLoja implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Double quantidade;

    @ManyToOne(targetEntity = Produto.class)
    @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "produto_fk"))
    private Produto produto;

    @ManyToOne(targetEntity = VendaCompraLojaVirtual.class)
    @JoinColumn(name = "venda_compraLoja_virtual_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "venda_compraLoja_virtual_fk"))
    private VendaCompraLojaVirtual vendaCompraLojaVirtual;



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

    public VendaCompraLojaVirtual getVendaCompraLojaVirtual() {
        return vendaCompraLojaVirtual;
    }

    public void setVendaCompraLojaVirtual(VendaCompraLojaVirtual vendaCompraLojaVirtual) {
        this.vendaCompraLojaVirtual = vendaCompraLojaVirtual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemVendaLoja that)) return false;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
