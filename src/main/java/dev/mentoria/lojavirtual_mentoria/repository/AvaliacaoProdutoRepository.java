package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.AvaliacaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AvaliacaoProdutoRepository  extends JpaRepository<AvaliacaoProduto, Long> {

    @Query("SELECT ap from AvaliacaoProduto ap where ap.produto.id = ?1")
    List<AvaliacaoProduto> avaliacaoProduto(Long idProduto);


    @Query("SELECT ap from AvaliacaoProduto ap where ap.pessoa.id = ?1 and ap.produto.id = ?2")
    List<AvaliacaoProduto> avaliacaoProdutoPessoa(Long idPessoa, Long idProduto);

    @Query("SELECT ap from AvaliacaoProduto ap where ap.pessoa.id = ?1")
    List<AvaliacaoProduto> avaliacaoPessoa(Long idPessoa);



}
