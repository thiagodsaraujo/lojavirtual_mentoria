package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.CategoriaProduto;
import dev.mentoria.lojavirtual_mentoria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {


    @Query(nativeQuery = true, value = "select count(1) > 0 from categoria_produto where upper(trim(nome_desc)) = upper(trim(?1))")
    public boolean existeProduto(String nomeProduto);


    @Query("select count(c) from Produto c where upper(c.nome) = :nomeProduto")
    long countByNomeNome(@Param("nomeProduto") String nomeProduto);

    @Query(value = "select cp from Produto cp where upper(trim(cp.nome)) = upper(trim(?1))")
    public List<Produto> buscarProdutoPorNome(String nome);


    @Query(value = "select prod from Produto  prod where upper(trim(prod.nome)) = ?1 and prod.empresa.id = ?2")
    public List<Produto> buscarProdutoPorNomeNaEmpresa(String nome, Long idEmpresa);


}
