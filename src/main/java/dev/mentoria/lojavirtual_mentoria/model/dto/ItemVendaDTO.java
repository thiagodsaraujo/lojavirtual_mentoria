package dev.mentoria.lojavirtual_mentoria.model.dto;

import dev.mentoria.lojavirtual_mentoria.model.Produto;

public class ItemVendaDTO {

    private Double quantidade;

    private Produto produto;

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

}
