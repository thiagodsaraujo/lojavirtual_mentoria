package dev.mentoria.lojavirtual_mentoria.controllers;


import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.StatusRastreio;
import dev.mentoria.lojavirtual_mentoria.repository.StatusRastreioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/status-rastreio")
public class StatusRastreioController {

    // Todos restController retorna um json

    @Autowired
    private StatusRastreioRepository statusRastreioRepository;

    @GetMapping(value = "/listarRastreios/{idVenda}")
    public ResponseEntity<List<?>> listaStatusRastreio(@PathVariable("idVenda") Long idVenda) throws ExceptionMentoriaJava {

        List<StatusRastreio> statusRastreio = statusRastreioRepository.listaStatusRastreio(idVenda);

        if (statusRastreio.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body(Collections.singletonList("Não foi encontrado nenhum status de rastreio para a venda com id: " + idVenda));
//            throw new ExceptionMentoriaJava("Nenhum status de rastreio encontrado para a venda com id " + idVenda, NOT_FOUND);
        }

        return ResponseEntity.ok(statusRastreio);
    }




}
