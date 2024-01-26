package dev.mentoria.lojavirtual_mentoria.model;

import javax.persistence.*;

@Entity(name = "tbl_acesso_endpoint")
public class AcessoEndPoint {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nomeEndPoint;

    private Integer qtdAcesso = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
