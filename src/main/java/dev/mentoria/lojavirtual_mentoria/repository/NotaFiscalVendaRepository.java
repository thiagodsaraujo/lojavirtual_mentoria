package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.NotaFiscalVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaFiscalVendaRepository extends JpaRepository<NotaFiscalVenda, Long> {
}
