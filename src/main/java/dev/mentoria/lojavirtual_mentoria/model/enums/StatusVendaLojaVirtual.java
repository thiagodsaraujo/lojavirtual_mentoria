package dev.mentoria.lojavirtual_mentoria.model.enums;

public enum StatusVendaLojaVirtual {

    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada"),
    ABANDONOU_CARRINHO("Abandonou Carrinho");

    private String descricao = "";

    StatusVendaLojaVirtual(String descricao) {
        this.descricao = descricao;

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
