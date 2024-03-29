package dev.mentoria.lojavirtual_mentoria;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mentoria.lojavirtual_mentoria.controllers.AcessoController;
import dev.mentoria.lojavirtual_mentoria.model.Acesso;
import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import dev.mentoria.lojavirtual_mentoria.repository.AcessoRepository;
import dev.mentoria.lojavirtual_mentoria.repository.PessoaJuridicaRepository;
import dev.mentoria.lojavirtual_mentoria.service.PessoaUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@Profile("test") - aqui seria para rodar o profile de teste, com bd de test etc etc
@SpringBootTest(classes = LojavirtualMentoriaApplication.class)
public class LojaVirtualMentoriaApplicationTests {



    @Autowired
    private AcessoController acessoController;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private PessoaUserService pessoaUserService;

    @Autowired
    private PessoaJuridicaRepository juridicaRepository;

    /*Teste do end-point de salvar*/
    @Test
    public void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();

        acesso.setDescricao("ROLE_ADMIN");

        ObjectMapper objectMapper = new ObjectMapper();



        ResultActions retornoApi = mockMvc
                .perform(MockMvcRequestBuilders.post("/salvarAcesso")
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());

        /*Conveter o retorno da API para um obejto de acesso*/

        Acesso objetoRetorno = objectMapper.
                readValue(retornoApi.andReturn().getResponse().getContentAsString(),
                        Acesso.class);

        assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());


    }



    @Test
    public void testRestApiDeleteAcesso() throws JsonProcessingException, Exception {

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();

        acesso.setDescricao("ROLE_TESTE_DELETE");

        acesso = acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
                .perform(MockMvcRequestBuilders.post("/deleteAcesso")
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());
        System.out.println("Status de retorno: " + retornoApi.andReturn().getResponse().getStatus());

        assertEquals("Acesso Removido", retornoApi.andReturn().getResponse().getContentAsString());
        assertEquals(200, retornoApi.andReturn().getResponse().getStatus());


    }



    @Test
    public void testRestApiDeletePorIDAcesso() throws JsonProcessingException, Exception {

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();

        acesso.setDescricao("ROLE_TESTE_DELETE_ID");

        acesso = acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
                .perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/" + acesso.getId()) //mudou o verbo para delete
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());
        System.out.println("Status de retorno: " + retornoApi.andReturn().getResponse().getStatus());

        assertEquals("Acesso Removido", retornoApi.andReturn().getResponse().getContentAsString());
        assertEquals(200, retornoApi.andReturn().getResponse().getStatus());


    }



    @Test
    public void testRestApiObterAcessoID() throws JsonProcessingException, Exception {

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();

        acesso.setDescricao("ROLE_OBTER_ID");

        acesso = acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
                .perform(MockMvcRequestBuilders.get("/obterAcesso/" + acesso.getId())
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        assertEquals(200, retornoApi.andReturn().getResponse().getStatus());


// Se o objeto genérico retornado, é igual ao objeto salvo no bd
        Acesso acessoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);

        assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());

        assertEquals(acesso.getId(), acessoRetorno.getId());

    }



    @Test
    public void testRestApiObterAcessoDesc() throws JsonProcessingException, Exception {

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();

        acesso.setDescricao("ROLE_TESTE_OBTER_LIST");

        acesso = acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
                .perform(MockMvcRequestBuilders.get("/buscarPorDesc/OBTER_LIST")
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        assertEquals(200, retornoApi.andReturn().getResponse().getStatus());


        List<Acesso> retornoApiList = objectMapper.
                readValue(retornoApi.andReturn()
                                .getResponse().getContentAsString(),
                        new TypeReference<List<Acesso>>() {});

        assertEquals(1, retornoApiList.size());

        assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());


        acessoRepository.deleteById(acesso.getId());

    }


    @Test
    @DisplayName("Single test successful")
    public void testCadastrarPessoaFisica(){

        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        pessoaJuridica.setCnpj("123456789");
        pessoaJuridica.setNome("Empresa Luffy");
        pessoaJuridica.setEmail("luffy@gmail.com");
        pessoaJuridica.setTelefone("123456789012");
        pessoaJuridica.setInscEstadual("1234");
        pessoaJuridica.setInscMunicipal("12345");
        pessoaJuridica.setNomeFantasia("NomeFantasia");

        juridicaRepository.save(pessoaJuridica);



        //        PessoaFisica pessoaFisica = new PessoaFisica();
//
//
//        pessoaFisica.setCpf("123456789");
//        pessoaFisica.setNome("Monkey Luffy");
//        pessoaFisica.setEmail("luffy@gmail.com");
//        pessoaFisica.setTelefone("123456789012");
//        pessoaFisica.setEmpresa();

    }


//    @Test
//    public void testCadastraAcesso() {
//
//        Acesso acesso = new Acesso();
//
//        acesso.setDescricao("ROLE_ADMIN");
//
//        assertEquals(true, acesso.getId() == null);
//
//        /*Gravou no banco de dados*/
//        acesso = acessoController.salvarAcesso(acesso).getBody();
//
//        assertEquals(true, acesso.getId() > 0);
//
//        /*Validar dados salvos da forma correta*/
//        assertEquals("ROLE_ADMIN", acesso.getDescricao());
//
//        /*Teste de carregamento*/
//
//        Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
//
//        assertEquals(acesso.getId(), acesso2.getId());
//
//
//        /*Teste de delete*/
//
//        acessoRepository.deleteById(acesso2.getId());
//
//        acessoRepository.flush(); /*Roda esse SQL de delete no banco de dados*/
//
//        Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
//
//        assertEquals(true, acesso3 == null);
//
//
//        /*Teste de query*/
//
//        acesso = new Acesso();
//
//        acesso.setDescricao("ROLE_ALUNO");
//
//        acesso = acessoController.salvarAcesso(acesso).getBody();
//
//        List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());
//
//        assertEquals(1, acessos.size());
//
//        acessoRepository.deleteById(acesso.getId());
//
//
//
//    }

}
