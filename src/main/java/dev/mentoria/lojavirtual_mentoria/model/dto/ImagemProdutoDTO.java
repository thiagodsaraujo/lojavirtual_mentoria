package dev.mentoria.lojavirtual_mentoria.model.dto;

import java.io.Serializable;

public class ImagemProdutoDTO implements Serializable {

    private Long id;
    private String imagemOriginal;
    private String imageMiniatura;
    private Long produtoId;
    private Long empresaId;

    public ImagemProdutoDTO() {
    }

    public ImagemProdutoDTO(Long id, String imagemOriginal, String imageMiniatura, Long produtoId, Long empresaId) {
        this.id = id;
        this.imagemOriginal = imagemOriginal;
        this.imageMiniatura = imageMiniatura;
        this.produtoId = produtoId;
        this.empresaId = empresaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImagemOriginal(String imagemOriginal) {
        this.imagemOriginal = imagemOriginal;
    }

    public void setImageMiniatura(String imageMiniatura) {
        this.imageMiniatura = imageMiniatura;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public String getImagemOriginal() {
        return imagemOriginal;
    }

    public String getImageMiniatura() {
        return imageMiniatura;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public Long getEmpresaId() {
        return empresaId;
    }
}
