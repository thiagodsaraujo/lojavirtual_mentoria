package dev.mentoria.lojavirtual_mentoria.security;


import dev.mentoria.lojavirtual_mentoria.ApplicationContextLoad;
import dev.mentoria.lojavirtual_mentoria.model.Usuario;
import dev.mentoria.lojavirtual_mentoria.repository.UsuarioRepository;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static dev.mentoria.lojavirtual_mentoria.ApplicationContextLoad.getApplicationContext;


// Criar a autenticação e retornar também a autenticação JWT, será usado to.do o projeto
@Service
@Component
public class JWTTokenAutenticacaoService {


// Tempo de expiração de 30 dias em milissegundos - CHATGPT
    private static final long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(30);

//    Chave secreta, pode ser qualquer coisa, depende da empresa - key just for study
    private static final String SECRET = System.getenv("secret");

//    Prefixo do token
    private static final String TOKEN_PREFIX = "Bearer";

//    Cabeçalho do token de requisição
    private static final String HEADER_STRING = "Authorization";


//    Gerar o token e dar a resposta para o cliente com JWT
    public void addAuthentication(HttpServletResponse response, String username) throws Exception{

        // Montagem do Token //

        String JWT = Jwts
                .builder() // Chama o gerador de token
                .setSubject(username) // Adiciona o user no token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Tempo de expiração
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();


        // Ex: Bearer + 1231231928hajknadsui12hi3ni21niaunsdaiuhsda.213i1u3h12.123hu21i3123i
        String token = TOKEN_PREFIX + " " + JWT;

        // Adiciona o token ao cabeçalho da resposta para tela e o cliente, outra API, navegador, web, js etc.
        response.addHeader(HEADER_STRING, token);

        liberacaoCors(response);

        // Retornando no corpo da resposta - Mais usado para ver no postman - Testes
        response.getWriter().write("{\"Authorization\" : \"" + token + "\"}");

    }

    // Método - Retorna o Usuário validade com token ou caso na seja valido retorna null
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String token = request.getHeader(HEADER_STRING);


        try {

            if (token != null) {
                // se não for null - retorna o usuario
                // tem que retirar o token limpo, retirar o header

                String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();

                // Faz a validação do token do usuario na requisição

                String user = Jwts.parser().setSigningKey(SECRET)
                        .parseClaimsJws(tokenLimpo)
                        .getBody()
                        .getSubject(); // Admin ou o outro usuario

                if (user != null) {
//              Pesquisou no banco se o usuario realmente existe

                    Usuario usuario = ApplicationContextLoad
                            .getApplicationContext()
                            .getBean(UsuarioRepository.class)
                            .findUsuarioByLogin(user);

                    if (usuario != null) {
//                  Retornou para o spring fazer o trabalho de autorização dele
                        return new UsernamePasswordAuthenticationToken(usuario.getLogin(),
                                usuario.getPassword(),
                                usuario.getAuthorities());

                    }

                }

            }
        } catch (SignatureException e){
            response.getWriter().write("Token está inválido! ");

        } catch (ExpiredJwtException e){
            // O token está expirado
            response.getWriter().write("Token está expirado, efetue o login novamente! ");

            // Remover o token inválido (opcional)
            // Você pode adicionar lógica aqui para remover o token do usuário ou tomar outras medidas necessárias.

            // Redirecionar para a página de login - Testar se realmente ta funcionando esse login
            response.sendRedirect("/login");

        } catch (MalformedJwtException e) {
            response.getWriter().write("Token JWT mal formado ");
        } catch (PrematureJwtException e) {
            response.getWriter().write("Token JWT processado antes do momento válido ");
        } catch (UnsupportedJwtException e) {
            response.getWriter().write("Token JWT contém partes não suportadas ou não permitidas ");
        } catch (IllegalArgumentException e) {
            response.getWriter().write("Argumento inválido ao processar o token JWT ");
        } finally {
            liberacaoCors(response);
        }

        return null;
    }


    // Liberação contra erro de cors no navegador
    private void liberacaoCors(HttpServletResponse response){
        // Precisa fazer várias verificações - 4 parametros de liberação

        if (response.getHeader("Access-Control-Allow-Origin") == null){
            response.addHeader("Access-Control-Allow-Origin", "*");
        }

        if (response.getHeader("Access-Control-Allow-Headers") == null){
            response.addHeader("Access-Control-Allow-Headers", "*");
        }

        if (response.getHeader("Access-Control-Request-Headers") == null){
            response.addHeader("Access-Control-Request-Headers", "*");
        }

        if (response.getHeader("Access-Control-Allow-Methods") == null){
            response.addHeader("Access-Control-Allow-Methods", "*");
        }


    }



}
