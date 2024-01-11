package dev.mentoria.lojavirtual_mentoria.model.enums;

public enum TipoPessoa {

    PJ("PJ"),

    PF("PF");

    private String descricao;

    private TipoPessoa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
