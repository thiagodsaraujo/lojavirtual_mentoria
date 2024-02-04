package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaProduto, Long> {


    @Query(nativeQuery = true, value = "select count(1) > 0 from categoria_produto where upper(trim(nome_desc)) = upper(trim(?1))")
    public boolean existeCategoria(String nomeCategoria);

    @Query("select count(c) from CategoriaProduto c where upper(c.nomeDesc) = :nomeDesc")
    long countByNomeDesc(@Param("nomeDesc") String nomeDesc);

    @Query(value = "select cp from CategoriaProduto cp where upper(trim(cp.nomeDesc))  = upper(trim(?1))")
    public List<CategoriaProduto> buscarCategoriaPorDescricacao(String descricao);




}
