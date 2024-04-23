package dev.mentoria.lojavirtual_mentoria.model.dto;

import dev.mentoria.lojavirtual_mentoria.model.Endereco;
import dev.mentoria.lojavirtual_mentoria.model.Pessoa;
import dev.mentoria.lojavirtual_mentoria.model.PessoaFisica;
import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import dev.mentoria.lojavirtual_mentoria.model.enums.TipoEndereco;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendaCompraLojaVirtualDTO {

    private Long id;

    private BigDecimal valorTotal;

    private BigDecimal valorDesc;

    private Pessoa pessoa;

    private Endereco cobranca;

    private Endereco entrega;

    private BigDecimal valorFrete;

    private Boolean excluido;



    private List<ItemVendaDTO> itemVendaLoja = new ArrayList<ItemVendaDTO>();

    public void setItemVendaLoja(List<ItemVendaDTO> itemVendaLoja) {
        this.itemVendaLoja = itemVendaLoja;
    }

    public List<ItemVendaDTO> getItemVendaLoja() {
        return itemVendaLoja;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public BigDecimal getValorDesc() {
        return valorDesc;
    }

    public void setValorDesc(BigDecimal valorDesc) {
        this.valorDesc = valorDesc;
    }

    public Endereco getCobranca() {
        return cobranca;
    }

    public void setCobranca(Endereco cobranca) {
        this.cobranca = cobranca;
    }

    public Endereco getEntrega() {
        return entrega;
    }

    public void setEntrega(Endereco entrega) {
        this.entrega = entrega;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }
}
