package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.VendaCompraLojaVirtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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




}