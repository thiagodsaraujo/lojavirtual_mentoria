package dev.mentoria.lojavirtual_mentoria.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.mentoria.lojavirtual_mentoria.model.dto.ItemVendaDTO;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vd_cp_loja_virt")
public class VendaCompraLojaVirtual implements Serializable {
    // Tabela referente a venda de um produto da loja
    // Muitas vendas para uma pessoa

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = PessoaFisica.class, cascade = CascadeType.ALL)
    @NotNull(message = "A pessoa que realizou a compra não pode ser nula")
    @JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
    private PessoaFisica pessoa;

    @ManyToOne(targetEntity = Endereco.class, cascade = CascadeType.ALL)
    @NotNull(message = "O endereço de entrega não pode ser nulo")
    @JoinColumn(name = "endereco_entrega_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "endereco_entrega_fk"))
    private Endereco enderecoEntrega;

    @ManyToOne(targetEntity = Endereco.class, cascade = CascadeType.ALL)
    @NotNull(message = "O endereço de cobrança não pode ser nulo")
    @JoinColumn(name = "endereco_cobranca_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "endereco_cobranca_fk"))
    private Endereco enderecoCobranca;

    @Column(nullable = false)
    @Min(value = 0, message = "O valor total da venda não pode ser negativo")
    private BigDecimal valorTotal;

    private BigDecimal valorDesconto;

    @ManyToOne(targetEntity = FormaPagamento.class)
    @NotNull(message = "A forma de pagamento não pode ser nula")
    @JoinColumn(name = "forma_pgto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "forma_pgto_fk"))
    private FormaPagamento formaPagamento;


    // uma nota fiscal é associada a só uma venda da loja virtual
    @OneToOne(targetEntity = NotaFiscalVenda.class, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(allowGetters = true)
    // tenta converter para json e dá erro, então ignoramos a conversão vai entrar em loop
    @NotNull(message = "A nota fiscal não pode ser nula")
    @JoinColumn(name = "nota_fiscal_id", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "nota_fiscal_fk"))
    private NotaFiscalVenda notaFiscalVenda;


    // Muitas vendas para um cupom de desconto
    @ManyToOne(targetEntity = CupomDesconto.class)
    @JoinColumn(name = "cupom_desconto_id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "cupom_desconto_fk"))
    private CupomDesconto cupomDesconto;

    @Column(nullable = false)
    @Min(value = 0, message = "O valor do frete não pode ser negativo")
    @NotNull(message = "O valor do frete não pode ser nulo")
    private BigDecimal valorFrete;

    @Column(nullable = false)
    @Min(value = 1, message = "O prazo de entrega não pode ser menor que 1")
    private Integer diasEntrega;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @NotNull(message = "A data de entrega não pode ser nula")
    private Date dataEntrega;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "A data da venda não pode ser nula")
    @Column(nullable = false)
    private Date dataVenda;


    @ManyToOne(targetEntity = PessoaJuridica.class)
    @NotNull(message = "A empresa não pode ser nula")
    @JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
    private PessoaJuridica empresa;


    @OneToMany(mappedBy = "vendaCompraLojaVirtual", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, targetEntity = ItemVendaLoja.class)
    private List<ItemVendaLoja> itemVendaLojas = new ArrayList<ItemVendaLoja>();


    public void setItemVendaLojas(List<ItemVendaLoja> itemVendaLojas) {
        this.itemVendaLojas = itemVendaLojas;
    }

    public List<ItemVendaLoja> getItemVendaLojas() {
        return itemVendaLojas;
    }

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }

    //

    public PessoaFisica getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaFisica pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Endereco getEnderecoCobranca() {
        return enderecoCobranca;
    }

    public void setEnderecoCobranca(Endereco enderecoCobranca) {
        this.enderecoCobranca = enderecoCobranca;
    }

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

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public NotaFiscalVenda getNotaFiscalVenda() {
        return notaFiscalVenda;
    }

    public void setNotaFiscalVenda(NotaFiscalVenda notaFiscalVenda) {
        this.notaFiscalVenda = notaFiscalVenda;
    }

    public CupomDesconto getCupomDesconto() {
        return cupomDesconto;
    }

    public void setCupomDesconto(CupomDesconto cupomDesconto) {
        this.cupomDesconto = cupomDesconto;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Integer getDiasEntrega() {
        return diasEntrega;
    }

    public void setDiasEntrega(Integer diasEntrega) {
        this.diasEntrega = diasEntrega;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VendaCompraLojaVirtual that)) return false;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }



}
