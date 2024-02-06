package dev.mentoria.lojavirtual_mentoria.controllers;


import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.MarcaProduto;
import dev.mentoria.lojavirtual_mentoria.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RestController
@RequestMapping("/marca")
public class MarcaProdutoController {

    @Autowired
    private MarcaRepository marcaRepository;

    @ResponseBody
    @PostMapping(value = "/salvarMarcaProduto")
    public ResponseEntity<MarcaProduto> salvarMarcaProduto(@RequestBody @Valid  MarcaProduto marcaProduto) throws ExceptionMentoriaJava { //Recebe o JSON e converte para o Objeto

        if (marcaProduto.getId() == null){
            List<MarcaProduto> marcas = marcaRepository.findByNomeDescAllIgnoreCase(marcaProduto.getNomeDesc().toUpperCase());

            if (!marcas.isEmpty()){
                throw new ExceptionMentoriaJava("Já existe marca com a descrição: " + marcaProduto.getNomeDesc());
            }
        }


        MarcaProduto marcaSalva = marcaRepository.save(marcaProduto);

        return new ResponseEntity<MarcaProduto>(marcaSalva, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/listarMarcas")
    public ResponseEntity<List<MarcaProduto>> listarMarcas(){

        var marcaProdutoList = marcaRepository.findAll();

        return new ResponseEntity<List<MarcaProduto>>(marcaProdutoList, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/buscarMarcaPorDesc/{desc}")
    public ResponseEntity<List<MarcaProduto>> buscarPorDesc(@PathVariable("desc") String desc){

        var marcaDescricao = marcaRepository.findByNomeDescAllIgnoreCase(desc.toUpperCase().trim());

        return new ResponseEntity<>(marcaDescricao, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/obterMarca/{id}")
    private ResponseEntity<MarcaProduto> obterMarcaProduto(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        var marcaById = marcaRepository.findById(id).orElse(null);

        if (marcaById == null){
            throw new ExceptionMentoriaJava("Não existe Marca do produto com o ID: " + id);
        }

        return new ResponseEntity<MarcaProduto>(marcaById, HttpStatus.OK);
    }



    @ResponseBody
    @PostMapping(value = "/deletarMarca")
    // deleta pelo json, não pelo html com {id} por exemplo
    public ResponseEntity<?> deleteMarca(@RequestBody MarcaProduto marca){

        marcaRepository.deleteById(marca.getId());

        return new ResponseEntity<>("Marca Removida", HttpStatus.OK);
    }



    @ResponseBody
//    @Secured({"ROLE_ADMIN", "ROLE_GERENTE"})
    @DeleteMapping(value = "/deleteMarcaPorId/{id}")
    public ResponseEntity<?> deleteMarcaPorId(@PathVariable("id") Long id    ){

        marcaRepository.deleteById(id);

        return new ResponseEntity<>("Marca Removida", HttpStatus.OK);
    }

}
