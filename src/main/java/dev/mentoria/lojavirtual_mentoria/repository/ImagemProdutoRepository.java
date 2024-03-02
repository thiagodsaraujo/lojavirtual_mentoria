package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.ImagemProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Long> {

    @Query("SELECT img from ImagemProduto img where img.produto.id = ?1")
    List<ImagemProduto> buscarImagemProduto(Long idProduto);

    @Modifying(flushAutomatically = true) // para dar um commit automático
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM img_prod WHERE produto_id = ?1")
    void deleteImagens(Long idProduto);




}
