package dev.mentoria.lojavirtual_mentoria.security;


import jakarta.servlet.http.HttpSessionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity implements HttpSessionListener {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{

        return http.
                securityMatcher(antMatcher("/loja_virtual_mentoria/**"))
                        .authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers("/listarAcessos").permitAll()).build();
    }




    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception{
        return (web) -> web
                .ignoring()
                .requestMatchers("/loja_virtual_mentoria/listarAcessos")
                .requestMatchers(HttpMethod.GET, "/salvarAcesso")
                .requestMatchers(HttpMethod.GET, "/listarAcessos")
                .requestMatchers(HttpMethod.POST, "/salvarAcesso");
    }


}
