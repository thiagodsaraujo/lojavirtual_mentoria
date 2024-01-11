package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.Pessoa;
import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PessoaJuridicaRepository extends CrudRepository<PessoaJuridica, Long > {


    @Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
    public PessoaJuridica existeCnpjCadastrado(String cnpj);

    @Query("select (count(p) > 0) from PessoaJuridica p where p.cnpj = ?1")
    boolean existsByCnpj(String cnpj);






}
