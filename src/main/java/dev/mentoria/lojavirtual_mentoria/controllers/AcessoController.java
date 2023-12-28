package dev.mentoria.lojavirtual_mentoria.controllers;


import dev.mentoria.lojavirtual_mentoria.model.Acesso;
import dev.mentoria.lojavirtual_mentoria.repository.AcessoRepository;
import dev.mentoria.lojavirtual_mentoria.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RestController
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

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

    @ResponseBody
    @GetMapping(value = "/buscarPorDesc/{desc}")
    public ResponseEntity<List<Acesso>> buscarPorDesc(@PathVariable("desc") String desc){

        var acesso = acessoRepository.buscarAcessoPorDescricao(desc);

        return new ResponseEntity<>(acesso, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/obterAcesso/{id}")
    private ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id){

        var acessoById = acessoRepository.findById(id);

        return new ResponseEntity<Acesso>(acessoById.get(), HttpStatus.OK);
    }



    @ResponseBody
    @PostMapping(value = "/deleteAcesso") // DELETE DO OBJETO INTEIRO
    public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso){

        acessoRepository.deleteById(acesso.getId());

        return new ResponseEntity<>("Acesso Removido", HttpStatus.OK);
    }



    @ResponseBody
//    @Secured({"ROLE_ADMIN", "ROLE_GERENTE"})
    @DeleteMapping(value = "/deleteAcessoPorId/{id}") // DELETE DO OBJETO INTEIRO
    public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id    ){
        acessoRepository.deleteById(id);

        return new ResponseEntity<>("Acesso Removido", HttpStatus.OK);
    }

}
