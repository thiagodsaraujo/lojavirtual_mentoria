package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.ContaPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {

    @Query("select cp from ContaPagar cp where upper(trim(cp.descricao)) like %?1%")
    List<ContaPagar> buscarContaDesc(String desc);

    @Query("select cp from ContaPagar cp where upper(trim(cp.descricao)) like %?1% and cp.empresa.id = ?2")
    List<ContaPagar> buscarContaDescPorEmpresa(String desc, Long idPessoa);

    @Query("select cp from ContaPagar cp where cp.pessoa.id = ?1 ")
    List<ContaPagar> buscarContaPessoa(Long idPessoa);

    @Query("select cp from ContaPagar cp where cp.pessoa_fornecedor.id = ?1 ")
    List<ContaPagar> buscarContaFornecedor(Long idForn);

    @Query("select cp from ContaPagar cp where cp.empresa.id = ?1 ")
    List<ContaPagar> buscaContaPorEmpresa(Long idEmpresa);

}
