package dev.mentoria.lojavirtual_mentoria.controllers;


import dev.mentoria.lojavirtual_mentoria.model.Acesso;
import dev.mentoria.lojavirtual_mentoria.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RestController
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @ResponseBody // * Poder dar um retorno da API //
    @PostMapping(value = "/salvarAcesso") // Mapeando a url para receber JSON, DE QUALQUER LUGAR QUE VIER O SALVARACESSO VAI SALVAR COM OS DOIS ** ANTES DA /
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso){ //Recebe o JSON e converte para o Objeto

        Acesso acessoSalvo = acessoService.save(acesso);

        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/listarAcessos")
    public ResponseEntity<List<Acesso>> listarAcesso(){

        var acessoList = acessoService.listarAcessos();

        return new ResponseEntity<List<Acesso>>(acessoList, HttpStatus.OK);
    }




}
