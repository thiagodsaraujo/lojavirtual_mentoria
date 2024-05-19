package dev.mentoria.lojavirtual_mentoria;

import dev.mentoria.lojavirtual_mentoria.model.dto.ObjetoErroDTO;
import dev.mentoria.lojavirtual_mentoria.service.ServiceSendEmail;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
@ControllerAdvice
//Classe pode melhorar a medida que o sistema evolue
public class ControllerExcecoes extends ResponseEntityExceptionHandler {

    private final ServiceSendEmail serviceSendEmail;

    public ControllerExcecoes(ServiceSendEmail serviceSendEmail) {
        this.serviceSendEmail = serviceSendEmail;
    }


    @ExceptionHandler(ExceptionMentoriaJava.class)
    public ResponseEntity<Object> handleExceptionInternalCustom(ExceptionMentoriaJava ex){
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        objetoErroDTO.setError(ex.getMessage());
        objetoErroDTO.setCode(HttpStatus.OK.toString());

        return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.OK);

    }


//    Captura exceções do projeto
    @Override
    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        String msg = "";

        if (ex instanceof MethodArgumentNotValidException){
            List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();

            for (ObjectError error : list){
                msg+= error.getDefaultMessage() + "\n";
            }

         }

        else if (ex instanceof  HttpMessageNotReadableException){
            msg = "Não está sendo enviado dados para o corpo da requisição";
        }

        else {
            msg = ex.getMessage();
        }

        objetoErroDTO.setError(msg);
        objetoErroDTO.setCode(status.value() + " ===> " + status.getReasonPhrase());
        ex.printStackTrace();



        return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);

    }

//    Captura erro na parte de abnco

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLGrammarException.class})
    protected ResponseEntity<Object> handleDatabaseException(Exception exception) {
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        String msg;

        if (exception instanceof SQLException) {
            msg = "Erro SQL do Banco: " + ((SQLException) exception).getCause().getCause().getMessage();
        } else if (exception instanceof DataIntegrityViolationException) {
            msg = "Erro de Integridade de Dados do Banco: " + ((DataIntegrityViolationException) exception).getCause().getCause().getMessage();
        } else if (exception instanceof ConstraintViolationException) {
            msg = "Erro de Violação de Chave Estrangeira do Banco: " + ((ConstraintViolationException) exception).getCause().getCause().getMessage();
        } else if (exception instanceof SQLGrammarException) {
            msg = "Erro de Gramática SQL: " + exception.getMessage();
        }else if (exception instanceof HttpMessageNotReadableException){
            msg = "É nécessário o envio do corpo da requisição!";
        }
        else {
            msg = "Erro no Banco de Dados: " + exception.getMessage();
        }

        objetoErroDTO.setError(msg);
        objetoErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        return new ResponseEntity<>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Método para tratar AccessDeniedException
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        objetoErroDTO.setError("Usuário não tem permissão para acessar este recurso.");
        objetoErroDTO.setCode(HttpStatus.FORBIDDEN.toString());
        return new ResponseEntity<>(objetoErroDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<Object> handleServletException(ServletException ex, WebRequest request) {
        ObjetoErroDTO erroDTO = new ObjetoErroDTO("Erro no Servlet", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return handleExceptionInternal(ex, erroDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        ObjetoErroDTO erroDTO = new ObjetoErroDTO("Usuário não autenticado. Faça login para acessar este recurso.", HttpStatus.UNAUTHORIZED.toString());
        return handleExceptionInternal(ex, erroDTO, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }


// Código do professor
//    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class,
//    SQLGrammarException.class, })
//    protected ResponseEntity<Object> handleExceptionDataIntegrity(Exception exception){
//
//        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
//
//        String msg = "";
//
//        if (exception instanceof SQLException){
//            msg = "Error de SQL do Banco: "+ ((SQLException) exception).getCause().getCause().getMessage();
//        } else {
//            msg = exception.getMessage();
//        }
//        if (exception instanceof DataIntegrityViolationException){
//            msg = "Error de Integridade de dados do Banco: "+ ((DataIntegrityViolationException) exception).getCause().getCause().getMessage();
//        } else {
//            msg = exception.getMessage();
//        }
//        if (exception instanceof ConstraintViolationException){
//            msg = "Error Violação de Chave Estrangeira do Banco: "+ ((ConstraintViolationException) exception).getCause().getCause().getMessage();
//        } else {
//            msg = exception.getMessage();
//        }
//
//
//        objetoErroDTO.setError(msg);
//        objetoErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
//
//
//        return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }





}
