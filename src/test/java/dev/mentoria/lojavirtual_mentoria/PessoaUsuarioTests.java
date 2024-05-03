package dev.mentoria.lojavirtual_mentoria;


import dev.mentoria.lojavirtual_mentoria.controllers.CupomDescontoController;
import dev.mentoria.lojavirtual_mentoria.controllers.FormaPgtoController;
import dev.mentoria.lojavirtual_mentoria.controllers.PessoaController;
import dev.mentoria.lojavirtual_mentoria.model.Endereco;
import dev.mentoria.lojavirtual_mentoria.model.PessoaFisica;
import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import dev.mentoria.lojavirtual_mentoria.model.enums.TipoEndereco;
import dev.mentoria.lojavirtual_mentoria.repository.CupomDescontoRepository;
import dev.mentoria.lojavirtual_mentoria.repository.PessoaFisicaRepository;
import dev.mentoria.lojavirtual_mentoria.repository.PessoaJuridicaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class PessoaUsuarioTests {


    @Autowired
    private PessoaController pessoaController;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private CupomDescontoRepository cupRepository;

    @Autowired
    private CupomDescontoController cupDescontoController;

    @Autowired
    private FormaPgtoController formaPagamentoController;

    @Test
    public void testCupomDesconto() {
        cupDescontoController.listaCupomDesc();
        cupDescontoController.listaCupomDesc(1L);
    }

    @Test
    public void testFormaPagamento() {
        formaPagamentoController.listaFormaPagamento();
        formaPagamentoController.listaFormaPagamentoidEmpresa(1L);
    }




    @Test
    @DisplayName("Single test successful")
    public void testCadastrarPessoaFisica() throws ExceptionMentoriaJava {

        Random cnpjRandomico = new Random();

        int cnpj = cnpjRandomico.nextInt(10000);


        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        pessoaJuridica.setCnpj("" + cnpj);
        pessoaJuridica.setNome("Thiago Araújo");
        pessoaJuridica.setEmail("thiagoaraujoadvg354@gmail.com");
        pessoaJuridica.setTelefone("45999795800");
        pessoaJuridica.setInscEstadual("65556565656665");
        pessoaJuridica.setInscMunicipal("55554565656565");
        pessoaJuridica.setNomeFantasia("54556565665");
        pessoaJuridica.setRazaoSocial("4656656566");

        Endereco endereco1 = new Endereco();
        endereco1.setBairro("Jd Dias");
        endereco1.setCep("556556565");
        endereco1.setComplemento("Casa cinza");
        endereco1.setEmpresa(pessoaJuridica);
        endereco1.setCidade("Campina Grande");
        endereco1.setNumero("389");
        endereco1.setPessoa(pessoaJuridica);
        endereco1.setRuaLogradouro("Av. são joao sexto");
        endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
        endereco1.setUf("PR");
        endereco1.setCidade("Curitiba");


        Endereco endereco2 = new Endereco();

        endereco2.setBairro("Jd Maracana");
        endereco2.setCep("7878778");
        endereco2.setComplemento("Andar 4");
        endereco2.setCidade("João Pessoa");
        endereco2.setEmpresa(pessoaJuridica);
        endereco2.setNumero("555");
        endereco2.setPessoa(pessoaJuridica);
        endereco2.setRuaLogradouro("Av. maringá");
        endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
        endereco2.setUf("PR");
        endereco2.setCidade("Curitiba");

        pessoaJuridica.getEnderecos().add(endereco2);
        pessoaJuridica.getEnderecos().add(endereco1);

        pessoaJuridica = pessoaController.salvarPessoaJuridica(pessoaJuridica).getBody();

        assertEquals(true, pessoaJuridica.getId() > 0 );

        for (Endereco endereco : pessoaJuridica.getEnderecos()) {
            assertEquals(true, endereco.getId() > 0);
        }

        assertEquals(2, pessoaJuridica.getEnderecos().size());

    }

    @Test
    public void testCadPessoaFisica() throws ExceptionMentoriaJava {

        // Consultando e add a empresa para a pessoa.
        // A ideia que seja um gerador de lojas virtuais(?) e o usuario é associado aquela empresa dessa loja
        var empresaBanco = pessoaJuridicaRepository.existeCnpjCadastrado("476");


        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setCpf("612.864.550-06");
        pessoaFisica.setNome("Alex fernando3");
        pessoaFisica.setEmail("alex.fe8554998912r9559nando.egidio@gmail.com");
        pessoaFisica.setTelefone("459997958003121213121");
        pessoaFisica.setEmpresa(empresaBanco);

        Endereco endereco1 = new Endereco();
        endereco1.setBairro("Jd Dias");
        endereco1.setCep("556556565");
        endereco1.setComplemento("Casa cinza");
        endereco1.setNumero("389");
        endereco1.setPessoa(pessoaFisica);
        endereco1.setRuaLogradouro("Av. são joao sexto");
        endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
        endereco1.setUf("PR");
        endereco1.setCidade("Curitiba");
        endereco1.setEmpresa(empresaBanco);


        Endereco endereco2 = new Endereco();
        endereco2.setBairro("Jd Maracana");
        endereco2.setCep("7878778");
        endereco2.setComplemento("Andar 4");
        endereco2.setNumero("555");
        endereco2.setPessoa(pessoaFisica);
        endereco2.setRuaLogradouro("Av. maringá");
        endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
        endereco2.setUf("PR");
        endereco2.setCidade("Curitiba");
        endereco2.setEmpresa(empresaBanco);

        pessoaFisica.getEnderecos().add(endereco2);
        pessoaFisica.getEnderecos().add(endereco1);

        pessoaFisica = pessoaController.salvarPessoaFisica(pessoaFisica).getBody();

        assertEquals(true, pessoaFisica.getId() > 0 );

        for (Endereco endereco : pessoaFisica.getEnderecos()) {
            assertEquals(true, endereco.getId() > 0);
        }

        assertEquals(2, pessoaFisica.getEnderecos().size());

    }


}
