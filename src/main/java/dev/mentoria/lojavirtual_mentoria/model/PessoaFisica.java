package dev.mentoria.lojavirtual_mentoria.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "pessoa_fisica")
@PrimaryKeyJoinColumn(name = "id")
public class PessoaFisica extends Pessoa implements Serializable {


    @Column(nullable = false)
    private String cpf;


    @Temporal(TemporalType.DATE)
    private Date dataNascimento;


    /**
     * get field @Column(nullable = false)
     *
     * @return cpf @Column(nullable = false)

     */
    public String getCpf() {
        return this.cpf;
    }

    /**
     * set field @Column(nullable = false)
     *
     * @param cpf @Column(nullable = false)

     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * get field @Temporal(TemporalType.DATE)
     *
     * @return dataNascimento @Temporal(TemporalType.DATE)

     */
    public Date getDataNascimento() {
        return this.dataNascimento;
    }

    /**
     * set field @Temporal(TemporalType.DATE)
     *
     * @param dataNascimento @Temporal(TemporalType.DATE)

     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PessoaFisica that = (PessoaFisica) o;

        return getCpf() != null ? getCpf().equals(that.getCpf()) : that.getCpf() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getCpf() != null ? getCpf().hashCode() : 0);
        return result;
    }
}



