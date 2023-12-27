package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional // Só vai comitar se estiver tudo ok, se não rollback
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {


    @Query("select u from Usuario u where u.login = ?1")
    Usuario findUsuarioByLogin(String login);

}
