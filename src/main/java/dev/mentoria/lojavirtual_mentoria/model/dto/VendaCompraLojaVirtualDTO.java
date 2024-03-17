package dev.mentoria.lojavirtual_mentoria.model.dto;

import java.math.BigDecimal;

public class VendaCompraLojaVirtualDTO {

    private BigDecimal valorTotal;
    private BigDecimal valorDesconto;

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }
}
