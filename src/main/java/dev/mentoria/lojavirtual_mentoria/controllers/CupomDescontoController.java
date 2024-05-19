package dev.mentoria.lojavirtual_mentoria.controllers;


import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.CupomDesconto;
import dev.mentoria.lojavirtual_mentoria.repository.CupomDescontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cupomdesconto")
public class CupomDescontoController {


    @ResponseBody
    @PostMapping("/salvar-cupom")
    public ResponseEntity<CupomDesconto> salvarCupom(@Valid @RequestBody CupomDesconto cupomDesconto) throws ExceptionMentoriaJava {

        Optional<CupomDesconto> cupomDescontoOptional = cupDescontoRepository.findById(cupomDesconto.getId());

        if(cupomDescontoOptional != null){
            throw new ExceptionMentoriaJava("Cupom já cadastrado com o ID: " + cupomDesconto.getId() + " - " + cupomDesconto.getCodigoCupom());
        }

        if(cupomDesconto.getEmpresa() == null){
            throw new ExceptionMentoriaJava("Empresa não informada");
        }

        if(cupomDesconto.getDataValidadeCupom() == null){
            throw new ExceptionMentoriaJava("Data de validade do cupom não informada");
        }

        return new ResponseEntity<CupomDesconto>(cupDescontoRepository.save(cupomDesconto), HttpStatus.OK);
    }

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

    @ResponseBody
    @GetMapping(value = "**/buscarCupomDesc/{id}")
    public ResponseEntity<CupomDesconto> buscarCupomDesc(@PathVariable("id") Long id) throws ExceptionMentoriaJava{

        if (!cupDescontoRepository.findById(id).isPresent()){
            throw new ExceptionMentoriaJava("Cupom não encontrado com o ID: " + id);
        }

        return new ResponseEntity<CupomDesconto>(cupDescontoRepository.findById(id).get(), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deletarCupomDesc/{id}")
    public ResponseEntity<String> deletarCupomDesc(@PathVariable("id") Long id) throws ExceptionMentoriaJava{

        if (!cupDescontoRepository.findById(id).isPresent()){
            throw new ExceptionMentoriaJava("Cupom não encontrado com o ID: " + id);
        }

        cupDescontoRepository.deleteById(id);

        return new ResponseEntity<String>("Cupom deletado com sucesso", HttpStatus.OK);
    }

}
