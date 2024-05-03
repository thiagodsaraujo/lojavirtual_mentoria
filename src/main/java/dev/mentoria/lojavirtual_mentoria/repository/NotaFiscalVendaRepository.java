package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.NotaFiscalVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaFiscalVendaRepository extends JpaRepository<NotaFiscalVenda, Long>
{

    @Query(value = "SELECT nf " +
            "FROM NotaFiscalVenda nf " +
            "WHERE nf.vendaCompraLojaVirtual.id = ?1")
    List<NotaFiscalVenda> buscaNotaPorVenda(Long idVenda);


    @Query(value = "SELECT nf " +
            "FROM NotaFiscalVenda nf " +
            "WHERE nf.vendaCompraLojaVirtual.id = ?1")
    NotaFiscalVenda buscaObjetoNotaPorVenda(Long idVenda);


}
