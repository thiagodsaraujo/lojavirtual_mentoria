package dev.mentoria.lojavirtual_mentoria.AcessosTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mentoria.lojavirtual_mentoria.controllers.AcessoController;
import dev.mentoria.lojavirtual_mentoria.model.Acesso;
import dev.mentoria.lojavirtual_mentoria.service.AcessoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class AcessoMockTest {

    @InjectMocks
    private AcessoController acessoController;

    @Mock
    private AcessoService acessoService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Transactional
    void testSalvarAcesso() {
        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_USUARIO");

        when(acessoService.save(any(Acesso.class))).thenReturn(acesso);

        ResponseEntity<Acesso> responseEntity = acessoController.salvarAcesso(acesso);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(acesso, responseEntity.getBody());
    }

    @Test
    void testListarAcesso() {

        Acesso acesso1 = new Acesso();
        acesso1.setDescricao("ROLE_ADMININISTRADOR");

        Acesso acesso2 = new Acesso();
        acesso2.setDescricao("ROLE_USUARIO");

        List<Acesso> acessoList = Arrays.asList(acesso1, acesso2);

        when(acessoService.listarAcessos()).thenReturn(acessoList);

        ResponseEntity<List<Acesso>> responseEntity = acessoController.listarAcesso();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(acessoList, responseEntity.getBody());
    }
}