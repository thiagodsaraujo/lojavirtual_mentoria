package dev.mentoria.lojavirtual_mentoria.controllers;

import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.NotaItemProduto;
import dev.mentoria.lojavirtual_mentoria.repository.NotaItemProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notaItemProduto")
public class NotaItemProdutoController {

    @Autowired
    NotaItemProdutoRepository notaItemProdutoRepository;


    @ResponseBody
    @PostMapping(value = "/salvarNotaItemProduto")
    public ResponseEntity<NotaItemProduto>
    salvarNotaItemProduto(@RequestBody @Valid NotaItemProduto notaItemProduto)
            throws ExceptionMentoriaJava {


        if (notaItemProduto.getId() == null) {

            if (notaItemProduto.getProduto() == null || notaItemProduto.getProduto().getId() <= 0) {
                throw new ExceptionMentoriaJava("O produto deve ser informado.");
            }


            if (notaItemProduto.getNotaFiscalCompra() == null || notaItemProduto.getNotaFiscalCompra().getId() <= 0) {
                throw new ExceptionMentoriaJava("A nota fisca deve ser informada.");
            }


            if (notaItemProduto.getEmpresa() == null || notaItemProduto.getEmpresa().getId() <= 0) {
                throw new ExceptionMentoriaJava("A empresa deve ser informada.");
            }

            List<NotaItemProduto> notaExistente = notaItemProdutoRepository.
                    buscaItemPorProdutoNotaFiscalId(notaItemProduto.getProduto().getId(),
                            notaItemProduto.getNotaFiscalCompra().getId());

            if (!notaExistente.isEmpty()) {
                throw new ExceptionMentoriaJava("Já existe este produto cadastrado para esta nota.");
            }

        }

        if (notaItemProduto.getQuantidade() <=0) {
            throw new ExceptionMentoriaJava("A quantidade do produto deve ser informada.");
        }


        NotaItemProduto notaItemSalva = notaItemProdutoRepository.save(notaItemProduto); // AQUI SÓ SALVA

        notaItemSalva = notaItemProdutoRepository.findById(notaItemProduto.getId()).get(); // AQUI RETORNA O OBJETO SALVO

        return new ResponseEntity<NotaItemProduto>(notaItemSalva, HttpStatus.CREATED);


    }

    @ResponseBody
    @GetMapping(value = "/listarNotaItemProduto")
    public ResponseEntity<List<NotaItemProduto>> listarNotaItemProduto() {
        return new ResponseEntity<List<NotaItemProduto>>(notaItemProdutoRepository.findAll(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/buscarNotaItemPorId/{id}")
    public ResponseEntity<NotaItemProduto> buscarNotaItemPorId(@PathVariable("id") Long id) throws ExceptionMentoriaJava {
        var notaItemProduto = notaItemProdutoRepository.findById(id);

        if (notaItemProduto.isEmpty()) {
            throw new ExceptionMentoriaJava("Nota Item Produto não encontrado");
        }

        return new ResponseEntity<NotaItemProduto>(notaItemProduto.get(), HttpStatus.OK);
    }



    @ResponseBody
    @DeleteMapping(value = "/deleteNotaItemPorId/{id}")
    public ResponseEntity<?> deleteNotaItemPorId(@PathVariable("id") Long id) {


        notaItemProdutoRepository.deleteById(id);

        return new ResponseEntity("Nota Item Produto Removido",HttpStatus.OK);
    }


}
