package dev.mentoria.lojavirtual_mentoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@EntityScan(basePackages = "dev.mentoria.lojavirtual_mentoria.model")
@ComponentScan(basePackages = {"dev.*"}) // procura pelos recursos no array dentro das pastas dev
@EnableJpaRepositories(basePackages = {"dev.mentoria.lojavirtual_mentoria.repository"}) //vai procurar os repositories nessa pasta em especifico
@EnableTransactionManagement
public class LojavirtualMentoriaApplication implements AsyncConfigurer {

	public static void main(String[] args) {

		System.out.println("Senha:" + new BCryptPasswordEncoder().encode("123"));

		SpringApplication.run(LojavirtualMentoriaApplication.class, args);
	}

	@Bean(name = "asyncExecutor")
	@Override
	public Executor getAsyncExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		// Configuração do pool de threads assíncronas
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Assincrono Thread: ");
		executor.initialize();

		return executor;
	}


}
