package dev.mentoria.lojavirtual_mentoria.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mentoria.lojavirtual_mentoria.model.dto.ObjetoErroDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        if (!response.isCommitted()) {
            logger.info("Handle method called in CustomAccessDeniedHandler");
            ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
            objetoErroDTO.setError("Usuário não tem permissão para acessar este recurso.");
            objetoErroDTO.setCode(HttpStatus.FORBIDDEN.toString());

            response.setContentType("application/json");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(new ObjectMapper().writeValueAsString(objetoErroDTO));
        } else {
            logger.warn("Response already committed in CustomAccessDeniedHandler");
        }
    }
}

