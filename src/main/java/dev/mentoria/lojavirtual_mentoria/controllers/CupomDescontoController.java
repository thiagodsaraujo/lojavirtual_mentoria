package dev.mentoria.lojavirtual_mentoria.controllers;


import dev.mentoria.lojavirtual_mentoria.model.CupomDesconto;
import dev.mentoria.lojavirtual_mentoria.repository.CupomDescontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cupomdesconto")
public class CupomDescontoController {

    @Autowired
    private CupomDescontoRepository cupDescontoRepository;

    @ResponseBody
    @GetMapping(value = "**/listaCupomDesc/{idEmpresa}")
    public ResponseEntity<List<CupomDesconto>> listaCupomDesc(@PathVariable("idEmpresa") Long idEmpresa){

        return new ResponseEntity<List<CupomDesconto>>(cupDescontoRepository.cupDescontoPorEmpresa(idEmpresa), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listaCupomDesc")
    public ResponseEntity<List<CupomDesconto>> listaCupomDesc(){

        return new ResponseEntity<List<CupomDesconto>>(cupDescontoRepository.findAll() , HttpStatus.OK);
    }

}
