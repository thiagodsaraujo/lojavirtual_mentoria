package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.NotaFiscalCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface NotaFiscalCompraRepository extends JpaRepository<NotaFiscalCompra,Long> {

    @Query("SELECT n FROM NotaFiscalCompra n WHERE upper(trim(n.descricaoObs)) like %?1%")
    List<NotaFiscalCompra> buscarNotaPorDescricao(String descricao);

    @Query("select n from NotaFiscalCompra n where n.descricaoObs = ?1")
    NotaFiscalCompra findByDescricaoObs(String descricaoObs);

    @Query("select n from NotaFiscalCompra n where n.numeroNota = ?1 and n.serieNota = ?2")
    NotaFiscalCompra findByNumeroNotaAndSerieNota(String numeroNota, String serieNota);

    @Query("select n from NotaFiscalCompra n where n.numeroNota = ?1")
    NotaFiscalCompra findByNumeroNota(String numeroNota);





    boolean existsByDescricaoObs(String descricaoObs);

        @Query("SELECT n FROM NotaFiscalCompra n WHERE n.pessoa.id = ?1")
    List<NotaFiscalCompra> buscarNotaPorPessoaId(Long id);

    @Query("SELECT n FROM NotaFiscalCompra n WHERE n.contaPagar.id = ?1")
    List<NotaFiscalCompra> buscarNotaContaPagar(Long idContaPagar);

    @Query("SELECT n FROM NotaFiscalCompra n WHERE n.empresa.id = ?1")
    List<NotaFiscalCompra> buscarNotaPorEmpresaId(Long id);

    // para deletar com segurança deve ser dessa forma
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true) //forçar deletar no banco de dados
    @Query(nativeQuery = true, value = "DELETE FROM nota_item_produto ntp WHERE ntp.nota_fiscal_compra_id= ?1")
    void deleteItemFilhoNotaFiscalCompra(Long idNotaFiscalCompra);

}
