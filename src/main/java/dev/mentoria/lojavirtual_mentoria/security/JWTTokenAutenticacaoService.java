package dev.mentoria.lojavirtual_mentoria.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;


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
                .signWith(SignatureAlgorithm.ES512, SECRET).compact();

        String token = TOKEN_PREFIX + " " + JWT;

        // Adiciona o token ao cabeçalho da resposta para tela e o cliente, outra API, navegador, web, js etc.
        response.addHeader(HEADER_STRING, token);

        // Retornando no corpo da resposta - Mais usado para ver no postman - Testes
        response.getWriter().write("{\"Authorization\" : \"" + token + "\"}");

    }



}
