package dev.mentoria.lojavirtual_mentoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "dev.mentoria.lojavirtual_mentoria.model")
@ComponentScan(basePackages = {"dev.*"}) // procura pelos recursos no array dentro das pastas dev
@EnableJpaRepositories(basePackages = {"dev.mentoria.lojavirtual_mentoria.repository"}) //vai procurar os repositories nessa pasta em especifico
@EnableTransactionManagement
public class LojavirtualMentoriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojavirtualMentoriaApplication.class, args);
	}

}
