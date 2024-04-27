package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.VendaCompraLojaVirtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface VendaCompraLojaVirtualRepository extends JpaRepository<VendaCompraLojaVirtual, Long> {


//    @Modifying(flushAutomatically = true) // update ou delete precisa
//    @Query(nativeQuery = true, value =
//            "UPDATE nota_fiscal_venda set venda_compra_loja_virtual_id = null where venda_compra_loja_virtual_id = :idVenda;" +
//            "delete from nota_fiscal_venda where venda_compra_loja_virtual_id = :idVenda;" +
//            "delete from item_venda_loja where venda_compra_loja_virtual_id = :idVenda;" +
//            "delete from status_rastreio where id = :idVenda;" +
//            "delete from vd_cp_loja_virt where id = :idVenda;")
//    void exclusaoTotalVendaBanco(@Param("idVenda") Long idVenda);
// Ele tentou excluir direto por query, mas não deu certo, então ele fez um método para excluir por id no service de venda

    @Query("SELECT v FROM VendaCompraLojaVirtual v WHERE v.pessoa.id = :idPessoa")
    VendaCompraLojaVirtual findByPessoaId(Long idPessoa);



    @Query(value = "SELECT vd FROM VendaCompraLojaVirtual vd WHERE vd.id = ?1 and vd.excluido = false")
    VendaCompraLojaVirtual findByIdExclusao(Long id);
    // se o campo excluido for true, ele não vai retornar o registro, retornando os campos todos null

    @Query(value = "SELECT itemVdLoja.vendaCompraLojaVirtual " +
            "FROM ItemVendaLoja itemVdLoja " +
            "WHERE itemVdLoja.vendaCompraLojaVirtual.excluido = false " +
            "and itemVdLoja.produto.id = ?1")
    List<VendaCompraLojaVirtual> vendaPorProduto(Long idProduto);


    @Query(value = "SELECT distinct vd.vendaCompraLojaVirtual " +
            "FROM ItemVendaLoja vd " +
            "WHERE vd.vendaCompraLojaVirtual.excluido = false " +
            "and upper(trim(vd.produto.nome)) like %:valor%")
    List<VendaCompraLojaVirtual> vendaPorNomeProduto(String valor);


    @Query(value = "SELECT distinct vd.vendaCompraLojaVirtual " +
            "FROM ItemVendaLoja vd " +
            "WHERE vd.vendaCompraLojaVirtual.excluido = false " +
            "and upper(trim(vd.vendaCompraLojaVirtual.pessoa.nome)) like %:valor%")
    List<VendaCompraLojaVirtual> vendaPorNomeCliente(String valor);

    @Query(value = "SELECT distinct vd.vendaCompraLojaVirtual " +
            "FROM ItemVendaLoja vd " +
            "WHERE vd.vendaCompraLojaVirtual.excluido = false " +
            "and vd.vendaCompraLojaVirtual.pessoa.id = :id")
    List<VendaCompraLojaVirtual> vendaPorIdCliente(Long id);


    @Query(value = "SELECT distinct vd.vendaCompraLojaVirtual " +
            "FROM ItemVendaLoja vd " +
            "WHERE vd.vendaCompraLojaVirtual.excluido = false " +
            "and upper(trim(vd.vendaCompraLojaVirtual.enderecoCobranca.ruaLogradouro)) like %:valor%")
    List<VendaCompraLojaVirtual> vendaPorEndCobranca(String valor);

    @Query(value = "SELECT distinct vd.vendaCompraLojaVirtual " +
            "FROM ItemVendaLoja vd " +
            "WHERE vd.vendaCompraLojaVirtual.excluido = false " +
            "and upper(trim(vd.vendaCompraLojaVirtual.enderecoEntrega.ruaLogradouro)) like %:valor%")
    List<VendaCompraLojaVirtual> vendaPorEndEntrega(String valor);

    @Query(value = "SELECT DISTINCT venda.vendaCompraLojaVirtual " +
            "FROM ItemVendaLoja venda " +
            "WHERE venda.vendaCompraLojaVirtual.excluido = false " +
            "and venda.vendaCompraLojaVirtual.enderecoEntrega.cep =:cep" +
            " or venda.vendaCompraLojaVirtual.enderecoCobranca.cep= :cep")
    List<VendaCompraLojaVirtual> vendaPorCep(String cep);

    @Query(value = "SELECT DISTINCT venda.vendaCompraLojaVirtual " +
            "FROM ItemVendaLoja venda " +
            "WHERE venda.vendaCompraLojaVirtual.excluido = false " +
            "AND venda.vendaCompraLojaVirtual.dataVenda " +
            "BETWEEN :dataInicio AND :dataFim")
    List<VendaCompraLojaVirtual> vendaPorData(Date dataInicio, Date dataFim);
}