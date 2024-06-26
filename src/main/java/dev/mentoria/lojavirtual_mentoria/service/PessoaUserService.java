package dev.mentoria.lojavirtual_mentoria.service;


import dev.mentoria.lojavirtual_mentoria.model.PessoaFisica;
import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import dev.mentoria.lojavirtual_mentoria.model.Usuario;
import dev.mentoria.lojavirtual_mentoria.model.dto.CEPDto;
import dev.mentoria.lojavirtual_mentoria.model.dto.ConsultaCNPJDto;
import dev.mentoria.lojavirtual_mentoria.repository.PessoaFisicaRepository;
import dev.mentoria.lojavirtual_mentoria.repository.PessoaJuridicaRepository;
import dev.mentoria.lojavirtual_mentoria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

@Service
public class PessoaUserService {

    private final UsuarioRepository usuarioRepository;

    private final PessoaJuridicaRepository juridicaRepository;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

    private final PessoaFisicaRepository fisicaRepository;


    public PessoaUserService(UsuarioRepository usuarioRepository, PessoaJuridicaRepository pessoaJuridicaRepository, JdbcTemplate jdbcTemplate, PessoaFisicaRepository fisicaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.juridicaRepository = pessoaJuridicaRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.fisicaRepository = fisicaRepository;
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

            // Amarrar o usuario a empresa que trabalham
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

            usuarioRepository.insereAcessoUser(novoUsuario.getId());
            usuarioRepository.insereAcessoUserDinamico(novoUsuario.getId(), "ROLE_ADMIN");

            // Fazer o envio do e-mail do login e da senha

            StringBuilder mensagemHtml = new StringBuilder();

            mensagemHtml.append(("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Dados de Acesso</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h2>Informações de Acesso</h2>\n" +
                    "    <p>Olá "+juridica.getNome()+",</p>\n" +
                    "    <p>Aqui estão as suas informações de acesso:</p>\n" +
                    "    <ul>\n" +
                    "        <li><strong>Usuário:</strong> "+juridica.getEmail()+"</li>\n" +
                    "        <li><strong>Senha:</strong> "+senha+"</li>\n" +
                    "    </ul>\n" +
                    "    <p>Por favor, mantenha essas informações em local seguro.</p>\n" +
                    "    <p>Atenciosamente,<br>Equipe da Sua Aplicação</p>\n" +
                    "</body>\n" +
                    "</html>\n"));

            try {
//                serviceSendEmail.enviarEmailHtmlOutlook("Acesso Gerado para Loja Virtual", String.valueOf(mensagemHtml), juridica.getEmail());
//                serviceSendEmail.sendEmail(juridica.getEmail(), "Acesso Gerado para Loja Virtual", mensagemHtml.toString());

            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Email não enviado: " + e);
            }



        }



        return juridica;
    }

    public PessoaFisica salvarPessoaFisica(PessoaFisica fisica) {
// Validações já foram feitas no controller e pode salvar
//        var juridica = juridicaRepository.save(juridica);

        for (int i = 0; i< fisica.getEnderecos().size(); i++){
            fisica.getEnderecos().get(i).setPessoa(fisica);
//            fisica.getEnderecos().get(i).setEmpresa(fisica);
        }

        fisica = fisicaRepository.save(fisica);

        // faz a verificação se já existe Usuario referente a PJ
        Usuario usuarioPj = usuarioRepository.findUserByPessoa(fisica.getId(), fisica.getEmail());

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

            // Amarrar o usuario a empresa que trabalham
            novoUsuario.setDataAtualSenha(Calendar.getInstance().getTime());
            novoUsuario.setEmpresa(fisica.getEmpresa());
            novoUsuario.setPessoa(fisica);
            novoUsuario.setLogin(fisica.getEmail());


            // Senha padrão
            int i = 123;
            String senha = "" + i;
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            novoUsuario.setSenha(senhaCript);

            usuarioRepository.save(novoUsuario);

            usuarioRepository.insereAcessoUser(novoUsuario.getId());

            // Fazer o envio do e-mail do login e da senha

            StringBuilder mensagemHtml = new StringBuilder();

            mensagemHtml.append(("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Dados de Acesso</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h2>Informações de Acesso</h2>\n" +
                    "    <p>Olá "+fisica.getNome()+",</p>\n" +
                    "    <p>Aqui estão as suas informações de acesso:</p>\n" +
                    "    <ul>\n" +
                    "        <li><strong>Usuário:</strong> "+fisica.getEmail()+"</li>\n" +
                    "        <li><strong>Senha:</strong> "+senha+"</li>\n" +
                    "    </ul>\n" +
                    "    <p>Por favor, mantenha essas informações em local seguro.</p>\n" +
                    "    <p>Atenciosamente,<br>Equipe da Sua Aplicação</p>\n" +
                    "</body>\n" +
                    "</html>\n"));

            try {
//                serviceSendEmail.enviarEmailHtmlOutlook("Acesso Gerado para Loja Virtual", String.valueOf(mensagemHtml), fisica.getEmail());
//                serviceSendEmail.sendEmail(juridica.getEmail(), "Acesso Gerado para Loja Virtual", mensagemHtml.toString());

            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Email não enviado: " + e);
            }

        }

        return fisica;
    }

    public CEPDto consultaCep(String cep){
        return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json/", CEPDto.class).getBody();
    }

    public ConsultaCNPJDto consuconsultaCnpjReceitaWs(String cnpj){
        return new RestTemplate().getForEntity("https://receitaws.com.br/v1/cnpj/" + cnpj, ConsultaCNPJDto.class).getBody();
    }
}
