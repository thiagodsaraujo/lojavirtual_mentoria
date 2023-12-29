package dev.mentoria.lojavirtual_mentoria.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mentoria.lojavirtual_mentoria.model.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {


    // Configurando o gerenciador de autenticações
    public JWTLoginFilter(String url, AuthenticationManager authenticationManager){

        // Obrigado autenticar a url
        super(new AntPathRequestMatcher(url));

        // Gerenciador de autenticação
        setAuthenticationManager(authenticationManager);
    }

//    protected JWTLoginFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
//        super(requiresAuthenticationRequestMatcher);
//    }


//    Retorna o usuario ao processar a autenticação
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        // Obtém o usuario
        Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

        // Retorna o usuario com login e senha
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        try {
            new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        super.unsuccessfulAuthentication(request, response, failed);

        if (failed instanceof BadCredentialsException){
            response.getWriter().write("Usuario ou senha não encontrado!");
        } else {
            response.getWriter().write("Falha ao logar: " + failed.getMessage());
        }

    }
}
