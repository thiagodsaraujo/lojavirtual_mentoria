package dev.mentoria.lojavirtual_mentoria.AcessosTest;

import dev.mentoria.lojavirtual_mentoria.controllers.AcessoController;
import dev.mentoria.lojavirtual_mentoria.model.Acesso;
import dev.mentoria.lojavirtual_mentoria.repository.AcessoRepository;
import dev.mentoria.lojavirtual_mentoria.service.AcessoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class AcessoRepositoryTests {


    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoController acessoController;


    @Test
    @DisplayName("Single test successful")
    public void testCadastrarAcesso() {

        var roleSupervirsor2 = "ROLE_SUPERVIRSOR2";
        Acesso acesso01 = new Acesso(roleSupervirsor2);

        acessoController.salvarAcesso(acesso01).getBody();

//        var descSalvaNoBanco = acessoRepository.buscarAcessoPorDescricao(roleSupervirsor2);
//
//        assertEquals(roleSupervirsor2, descSalvaNoBanco);

        // Gravou no banco de dados
        assertTrue(acesso01.getId() > 0);

        // Validar dados da forma correta
        assertEquals(roleSupervirsor2, acesso01.getDescricao());


    }
    @Test
    public void testGetById(){

        var id = 1;
        var acesso = acessoRepository.findById((long) id).get();

        assertEquals(acesso.getId(), id);

    }

    @Test
    public void testListarAcessos(){

        List<Acesso> acessoList = acessoService.listarAcessos();
        System.out.println(acessoList.size());

    }


}
