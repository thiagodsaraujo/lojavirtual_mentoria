package dev.mentoria.lojavirtual_mentoria.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// Filtro onde todas as requisições serão capturadas para autenticar
public class JWTApiAutenticacaoFilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        // Estabelecer a autenticação do usuario

        Authentication authentication = new JWTTokenAutenticacaoService()
                .getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);

        // Colocar o processo de autenticação do Spring Security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);


    }
}
