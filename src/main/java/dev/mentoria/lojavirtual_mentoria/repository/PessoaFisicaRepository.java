package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.PessoaFisica;
import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PessoaFisicaRepository extends CrudRepository<PessoaFisica, Long > {


    @Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
    public PessoaFisica existeCpfCadastrado(String cpf);

    @Query("select (count(p) > 0) from PessoaFisica p where p.cpf = ?1")
    boolean existsByCpf(String cnpj);

}
