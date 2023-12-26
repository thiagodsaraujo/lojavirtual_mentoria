package dev.mentoria.lojavirtual_mentoria.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id")
public class PessoaJuridica extends Pessoa implements Serializable {


    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private String inscEstadual;


    private String inscMunicipal;

    @Column(nullable = false)
    private String nomeFantasia;

    @Column(nullable = false)
    private String razaoSocial;

    private String categoria;



    public String getInscEstadual() {
        return inscEstadual;
    }

    public void setInscEstadual(String inscEstadual) {
        this.inscEstadual = inscEstadual;
    }

    public String getInscMunicipal() {
        return inscMunicipal;
    }

    public void setInscMunicipal(String inscMunicipal) {
        this.inscMunicipal = inscMunicipal;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * get field @Column(nullable = false)
     *
     * @return cpf @Column(nullable = false)

     */
    public String getCnpj() {
        return this.cnpj;
    }

    /**
     * set field @Column(nullable = false)
     *
     * @param cnpj @Column(nullable = false)

     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PessoaJuridica that = (PessoaJuridica) o;

        return getCnpj() != null ? getCnpj().equals(that.getCnpj()) : that.getCnpj() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getCnpj() != null ? getCnpj().hashCode() : 0);
        return result;
    }
}



