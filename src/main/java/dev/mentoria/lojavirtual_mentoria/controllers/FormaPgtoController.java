package dev.mentoria.lojavirtual_mentoria.controllers;


import dev.mentoria.lojavirtual_mentoria.model.FormaPagamento;
import dev.mentoria.lojavirtual_mentoria.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/forma-pgto")
public class FormaPgtoController {


    @Autowired
    private FormaPagamentoRepository formaPgtoRepository;



    @ResponseBody
    @PostMapping("/salvar-forma-pgto")
    public ResponseEntity<FormaPagamento> salvarFormaPagamaneto(@RequestBody @Valid  FormaPagamento formaPagamento){

        return new ResponseEntity<FormaPagamento>(formaPgtoRepository.save(formaPagamento), org.springframework.http.HttpStatus.OK);
    }

    @GetMapping("/buscar-forma-pgto/{id}")
    public ResponseEntity<FormaPagamento> buscarFormaPagamento(@PathVariable Long id){

        return ResponseEntity.ok(formaPgtoRepository.findById(id).get());
    }

    @GetMapping("/buscar-forma-pgto")
    public ResponseEntity<List<FormaPagamento>> buscarFormaPagamento(){

        return ResponseEntity.ok(formaPgtoRepository.findAll());
    }
}
