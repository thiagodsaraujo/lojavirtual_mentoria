package dev.mentoria.lojavirtual_mentoria.model.dto;

import java.io.Serializable;

public class CategoriaProdutoDTO implements Serializable {

    //Essa classe foi criada devido ao erro que estava acontecendo, não estava salvando a empresa da categoria devido
    // a "Empresa" extender de Pessoa que é uma classe abstrata.

    private Long id;

    private String nomeDesc;

    private String empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDesc() {
        return nomeDesc;
    }

    public void setNomeDesc(String nomeDesc) {
        this.nomeDesc = nomeDesc;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
