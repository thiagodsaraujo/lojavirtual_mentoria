package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.CupomDesconto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CupomDescontoRepository extends JpaRepository<CupomDesconto, Integer>{

    @Query(value = "select c from CupomDesconto c where c.empresa.id = ?1")
    public List<CupomDesconto> cupDescontoPorEmpresa(Long idEmpresa);

}
