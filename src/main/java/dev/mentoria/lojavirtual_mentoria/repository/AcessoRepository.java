package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.Acesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AcessoRepository extends JpaRepository<Acesso, Long> {



    @Query("select (count(a) > 0) from Acesso a where upper(a.descricao) = upper(?1)")
    boolean existsByDescricaoAllIgnoreCase(String descricao);



    long countById(Long id);




    @Query("select a from Acesso a where upper (trim(a.descricao)) like %?1%")
    Acesso buscarAcessoPorDescricao(String descricao);

}
