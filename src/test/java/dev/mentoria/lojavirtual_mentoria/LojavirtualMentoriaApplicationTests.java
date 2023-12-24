package dev.mentoria.lojavirtual_mentoria;

import dev.mentoria.lojavirtual_mentoria.model.Acesso;
import dev.mentoria.lojavirtual_mentoria.repository.AcessoRepository;
import dev.mentoria.lojavirtual_mentoria.service.AcessoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojavirtualMentoriaApplication.class)
class LojavirtualMentoriaApplicationTests {


	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private AcessoService acessoService;


	@Test
	public void testCadastrarAcesso() {

		Acesso acessoAdmin = new Acesso("ROLE_ADMIN");

		acessoRepository.save(acessoAdmin);

//		var countById = acessoRepository.countById(acessoAdmin.getId());
//
//		System.out.println(countById);
	}

}
