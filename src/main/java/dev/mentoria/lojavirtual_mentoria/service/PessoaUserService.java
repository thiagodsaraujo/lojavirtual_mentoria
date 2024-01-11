package dev.mentoria.lojavirtual_mentoria.service;


import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import dev.mentoria.lojavirtual_mentoria.model.Usuario;
import dev.mentoria.lojavirtual_mentoria.repository.PessoaJuridicaRepository;
import dev.mentoria.lojavirtual_mentoria.repository.UsuarioRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class PessoaUserService {

    private final UsuarioRepository usuarioRepository;

    private final PessoaJuridicaRepository juridicaRepository;

    private final JdbcTemplate jdbcTemplate;


    public PessoaUserService(UsuarioRepository usuarioRepository, PessoaJuridicaRepository pessoaJuridicaRepository, JdbcTemplate jdbcTemplate) {
        this.usuarioRepository = usuarioRepository;
        this.juridicaRepository = pessoaJuridicaRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica juridica) {

        // Validações já foram feitas no controller e pode salvar
//        var juridica = juridicaRepository.save(juridica);

        for (int i = 0; i< juridica.getEnderecos().size(); i++){
            juridica.getEnderecos().get(i).setPessoa(juridica);
            juridica.getEnderecos().get(i).setEmpresa(juridica);
        }

        juridica = juridicaRepository.save(juridica);

        // faz a verificação se já existe Usuario referente a PJ
        Usuario usuarioPj = usuarioRepository.findUserByPessoa(juridica.getId(), juridica.getEmail());

        if (usuarioPj == null){
            // se for nulo
            String constraint = usuarioRepository.consultaConstraintAcesso();
            // está fazendo isso por causa do problema da criação do jpa de uma constraint para o acessos automatico que nao deveria existir
            // o que está atrapalhando o save
            if (constraint != null){
                jdbcTemplate.execute("begin; alter table usuarios_acessos drop constraint " + constraint + "; commit;");
                // corrige o banco
            }

            Usuario novoUsuario = new Usuario();

            novoUsuario.setDataAtualSenha(Calendar.getInstance().getTime());
            novoUsuario.setEmpresa(juridica);
            novoUsuario.setPessoa(juridica);
            novoUsuario.setLogin(juridica.getEmail());


            // Senha padrão
            int i = 123;
            String senha = "" + i;
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            novoUsuario.setSenha(senhaCript);

            usuarioRepository.save(novoUsuario);

            usuarioRepository.insereAcessoUserPj(novoUsuario.getId());

            // Fazer o envio do e-mail do login e da senha



        }



        return juridica;
    }
}
