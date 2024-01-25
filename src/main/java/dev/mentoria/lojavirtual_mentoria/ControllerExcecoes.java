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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
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

//    @ExceptionHandler(ExceptionMentoriaJava.class)
//    public ResponseEntity<ObjetoErroDTO> handleExceptionMentoriaJava(ExceptionMentoriaJava ex) {
//        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
//        objetoErroDTO.setError(ex.getMessage());
//        objetoErroDTO.setCode(HttpStatus.OK.toString());
//
//        return ResponseEntity.status(HttpStatus.OK).body(objetoErroDTO);
//    }


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

//        try {
////            Mandar e-mail para o responsável caso aconteça erro.
//            serviceSendEmail.enviarEmailHtmlOutlook("Erro na loja virtual" , ExceptionUtils.getMessage(ex), "responsavelprojeto@gmail.com");
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }


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
