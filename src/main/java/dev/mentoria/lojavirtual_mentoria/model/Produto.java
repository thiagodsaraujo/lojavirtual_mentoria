package dev.mentoria.lojavirtual_mentoria.model;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Tipo de unidade do produto deve ser informado!")
    private String tipoUnidade;

    @Size(min = 10, message = "Nome do produto deve ter mais de 10 letras")
    @NotNull(message = "Nome do produto deve ser informado!")
    @Column(nullable = false)
    private String nome;


    @NotNull(message = "Descrição do produto deve ser informada!")
    @Column(columnDefinition = "text", length = 2000, nullable = false)
    private String descricao;

    // TODO NotaItemProduto - Associar com essa classe

    @NotNull(message = "Peso do produto deve ser informado!")
    @Column(nullable = false)
    private Double peso;


    @NotNull(message = "Largura do produto deve ser informado!")
    @Column(nullable = false)
    private Double largura;

    @NotNull(message = "Altura do produto deve ser informado!")
    @Column(nullable = false)
    private Double altura;

    @Column(nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @NotNull(message = "Profundidade deve ser informada!")
    @Column(nullable = false)
    private Double profundidade;

    @NotNull(message = "Valor de venda deve ser informado!")
    @Column(nullable = false)
    private BigDecimal valorVenda = BigDecimal.ZERO;

    @Column(nullable = false)
    private Integer qtdEstoque = 0;

    @Column(name = "qtd_alerta_estoque")
    private Integer qtdeAlertaEstoque = 0;

    private String linkYoutube;

    private Boolean alertaQtdEstoque = Boolean.FALSE;

    @Column(name = "qtde_clique")
    private Integer qtdeClique = 0;


    @NotNull(message = "Empresa responsável pelo produto deve ser informada!")
    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
    private PessoaJuridica empresa;

    @NotNull(message = "Categoria do produto deve ser informada!")
    @ManyToOne(targetEntity = CategoriaProduto.class)
    @JoinColumn(name = "categoriaProd_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "categoriaProd_id_fk"))
    private CategoriaProduto categoriaProduto;

    @NotNull(message = "Marca do produto deve ser informada!")
    @ManyToOne(targetEntity = MarcaProduto.class)
    @JoinColumn(name = "marcaProduto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "marcaProduto_id_fk"))
    private MarcaProduto marcaProduto;

    // Esse relacionamento não existe, iria gerar um erro ao cadastrar um produto
//    @NotNull(message = "Nota do produto deve ser informada!")
//    @ManyToOne(targetEntity = NotaItemProduto.class)
//    @JoinColumn(name = "notaItemProduto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "notaItemProduto_id_fk"))
//    private NotaItemProduto notaItemProduto;

    @OneToMany(mappedBy = "produto", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImagemProduto> imagens = new ArrayList<ImagemProduto>();
    // Um produto tem várias imagens e uma imagem pertence a um produto

    public List<ImagemProduto> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemProduto> imagens) {
        this.imagens = imagens;
    }

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }

    public MarcaProduto getMarcaProduto() {
        return marcaProduto;
    }

    public void setMarcaProduto(MarcaProduto marcaProduto) {
        this.marcaProduto = marcaProduto;
    }

    public Integer getQtdeClique() {
        return qtdeClique;
    }

    public void setQtdeClique(Integer qtdeClique) {
        this.qtdeClique = qtdeClique;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoUnidade() {
        return tipoUnidade;
    }

    public void setTipoUnidade(String tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(Double profundidade) {
        this.profundidade = profundidade;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Integer getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(Integer qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public Integer getQtdeAlertaEstoque() {
        return qtdeAlertaEstoque;
    }

    public void setQtdeAlertaEstoque(Integer qtdeAlertaEstoque) {
        this.qtdeAlertaEstoque = qtdeAlertaEstoque;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public Boolean getAlertaQtdEstoque() {
        return alertaQtdEstoque;
    }

    public void setAlertaQtdEstoque(Boolean alertaQtdEstoque) {
        this.alertaQtdEstoque = alertaQtdEstoque;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getQtdClique() {
        return qtdeClique;
    }

    public void setQtdClique(Integer qtdClique) {
        this.qtdeClique = qtdClique;
    }


    public CategoriaProduto getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto produto)) return false;

        return getId().equals(produto.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
