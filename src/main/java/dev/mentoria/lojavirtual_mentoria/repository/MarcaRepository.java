package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.MarcaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarcaRepository extends JpaRepository<MarcaProduto, Long> {


    @Query("select m from MarcaProduto m where upper(trim(m.nomeDesc)) like %?1%")
    List<MarcaProduto> findByNomeDescAllIgnoreCase(@Param("nomeDesc") String nomeDesc);






}
