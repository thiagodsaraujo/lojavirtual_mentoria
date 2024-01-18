package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional // Só vai comitar se estiver tudo ok, se não rollback
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {


    @Query("select u from Usuario u where u.login = ?1")
    Usuario findUsuarioByLogin(String login);


    @Query(value = "select u from Usuario u where u.id = ?1 and u.login = ?2")
    Usuario findUserByPessoa(Long id, String email);


    @Query(value = "select constraint_name\n" +
            "from information_schema.constraint_column_usage\n" +
            "where table_name = 'usuarios_acessos' and column_name = 'acesso_id'\n" +
            "and constraint_name <> 'unique_acesso_user';", nativeQuery = true)
    String consultaConstraintAcesso();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into usuarios_acessos(usuario_id, acesso_id) VALUES (?1, (SELECT  ID FROM acesso WHERE descricao = 'ROLE_USER'))")
    void insereAcessoUser(Long id);

    // ENVIAR ACESSO DINAMICO
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into usuarios_acessos(usuario_id, acesso_id) VALUES (?1, (SELECT  ID FROM acesso WHERE descricao = ?2 limit 1))")
    void insereAcessoUserDinamico(Long id, String acesso);

    @Query(value = "select u from Usuario u where u.dataAtualSenha <= current_date - 90")
    List<Usuario> usuarioSenhaVencida();

}
