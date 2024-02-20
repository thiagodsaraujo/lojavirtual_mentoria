package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.NotaFiscalCompra;
import dev.mentoria.lojavirtual_mentoria.model.NotaItemProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface NotaItemProdutoRepository extends JpaRepository<NotaItemProduto, Long> {


    @Query("SELECT nt from NotaItemProduto nt where nt.produto.id = ?1 and nt.notaFiscalCompra.id = ?2")
    List<NotaItemProduto> buscaItemPorProdutoNotaFiscalId(Long idProduto, Long idNotaFiscalCompra);

    @Query("SELECT nt from NotaItemProduto nt where nt.notaFiscalCompra.id = ?1")
    List<NotaItemProduto> buscaItemPorNotaFiscalId(Long idNotaFiscalCompra);

    @Query("SELECT nt from NotaItemProduto nt where nt.produto.id = ?1")
    List<NotaItemProduto> buscaItemPorProdutoId(Long idProduto);

    @Query("SELECT nt from NotaItemProduto nt where nt.notaFiscalCompra.id = ?1")
    List<NotaItemProduto> buscarNotaItemPorNotaFiscalCompraId(Long idNotaFiscalCompra);

    @Query("SELECT nt from NotaItemProduto nt where nt.notaFiscalCompra.empresa.id = ?1")
    List<NotaFiscalCompra> buscaNotaItemPorEmpresaId(Long idEmpresa);


    @Override
    void deleteById(Long aLong);
}