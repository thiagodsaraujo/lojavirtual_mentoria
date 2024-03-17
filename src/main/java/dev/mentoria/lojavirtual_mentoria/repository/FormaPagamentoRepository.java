package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
}
