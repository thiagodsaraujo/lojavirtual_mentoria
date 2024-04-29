package dev.mentoria.lojavirtual_mentoria.repository;


import dev.mentoria.lojavirtual_mentoria.model.StatusRastreio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRastreioRepository extends JpaRepository<StatusRastreio, Long> {


    @Query(value = "SELECT sr " +
            "FROM StatusRastreio sr " +
            "WHERE sr.vendaCompraLojaVirtual.id = ?1")
    public List<StatusRastreio> listaStatusRastreio(Long idVenda);





}
