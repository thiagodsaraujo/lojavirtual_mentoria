package dev.mentoria.lojavirtual_mentoria.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;


// Filtro onde todas as requisições serão capturadas para autenticar
public class JWTApiAutenticacaoFilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            // Lógica antes da autenticação (se necessário)

            // Estabelecer a autenticação do usuário
            Authentication authentication = new JWTTokenAutenticacaoService()
                    .getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);

            // Colocar o processo de autenticação do Spring Security
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Prossiga para o próximo filtro na cadeia
            filterChain.doFilter(request, response);

        } catch (AuthenticationException e) {
            // Lidar com erros de autenticação
            SecurityContextHolder.clearContext();
            response.getWriter().write("Falha na autenticação: " + e.getMessage());
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        } catch (NoSuchElementException ex){
            ex.printStackTrace();
            System.out.println("Erro na classe: " + ex.getClass().getName());
            response.getWriter().write("Esse objeto não existe: \n" + ex.getMessage());
        }

        catch (Exception e) {
            // Lidar com outros erros
            e.printStackTrace();
            response.getWriter().write("Ocorreu um erro no sistema, avise o administrador: \n" + e.getMessage());

        }
    }
}

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
//            throws IOException, ServletException {
//
//        try {
//                    //CÓDIGO
//
//
//        // Estabelecer a autenticação do usuario
//
//        Authentication authentication = new JWTTokenAutenticacaoService()
//                .getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);
//
//        // Colocar o processo de autenticação do Spring Security
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        filterChain.doFilter(request, response);
//
//
//        } catch (Exception e){
//            e.printStackTrace();
//            response.getWriter().write("Ocorreu um erro no sistema, avise o administrador: \n" + e.getMessage() );
//        }
//
//    }

