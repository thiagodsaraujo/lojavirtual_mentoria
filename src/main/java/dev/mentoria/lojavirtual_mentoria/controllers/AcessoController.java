package dev.mentoria.lojavirtual_mentoria.controllers;


import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
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
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionMentoriaJava { //Recebe o JSON e converte para o Objeto

        // verificar se existe acesso com a descrição já cadastrada
        if (acesso.getId() == null){
            List<Acesso> acessos = acessoRepository.buscarAcessoPorDescricao(acesso.getDescricao().toUpperCase());

            if (!acessos.isEmpty()){
                throw new ExceptionMentoriaJava("Já existe Acesso com a descrição: " + acesso.getDescricao());
            }
        }


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

        var acesso = acessoRepository.buscarAcessoPorDescricao(desc.toUpperCase());

        return new ResponseEntity<>(acesso, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/obterAcesso/{id}")
    private ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        var acessoById = acessoRepository.findById(id).orElse(null);

        if (acessoById == null){
            throw new ExceptionMentoriaJava("Não existe Acesso com o ID: " + id);
        }

        return new ResponseEntity<Acesso>(acessoById, HttpStatus.OK);
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
