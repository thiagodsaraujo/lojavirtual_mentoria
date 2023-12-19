package dev.mentoria.lojavirtual_mentoria.model.enums;

public enum TipoUnidade {

    COBRANCA("Cobrança"),

    ENTREGA("Entrega");

    private String descricao;

    private TipoUnidade(String descricao) {
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
