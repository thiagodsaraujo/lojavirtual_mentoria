package dev.mentoria.lojavirtual_mentoria;


import dev.mentoria.lojavirtual_mentoria.controllers.PessoaController;
import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import dev.mentoria.lojavirtual_mentoria.model.enums.TipoPessoa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Random;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class PessoaUsuarioTests {


    @Autowired
    private PessoaController pessoaController;




    @Test
    @DisplayName("Single test successful")
    public void testCadastrarPessoaFisica() throws ExceptionMentoriaJava {

        Random cnpjRandomico = new Random();

        int cnpj = cnpjRandomico.nextInt(10000);


        PessoaJuridica pessoaJuridica = new PessoaJuridica();


        pessoaJuridica.setCnpj("" + cnpj);
        pessoaJuridica.setNome("Administrador");
        pessoaJuridica.setEmail("admin@gmail.com");
        pessoaJuridica.setTelefone("123456789012");
        pessoaJuridica.setInscEstadual("1234");
        pessoaJuridica.setRazaoSocial("123456789012");
        pessoaJuridica.setInscMunicipal("12345");
        pessoaJuridica.setNomeFantasia("NomeFantasia");
        pessoaJuridica.setTipoPessoa(String.valueOf(TipoPessoa.PJ));

        pessoaController.salvarPessoaJuridica(pessoaJuridica);
        //        PessoaFisica pessoaFisica = new PessoaFisica();
//
//
//        pessoaFisica.setCpf("123456789");
//        pessoaFisica.setNome("Monkey Luffy");
//        pessoaFisica.setEmail("luffy@gmail.com");
//        pessoaFisica.setTelefone("123456789012");
//        pessoaFisica.setEmpresa();

    }


}
