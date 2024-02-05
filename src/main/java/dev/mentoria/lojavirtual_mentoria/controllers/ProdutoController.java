package dev.mentoria.lojavirtual_mentoria.controllers;


import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import dev.mentoria.lojavirtual_mentoria.model.Produto;
import dev.mentoria.lojavirtual_mentoria.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RestController
@RequestMapping(value = "/prod")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @ResponseBody // * Poder dar um retorno da API //
    @PostMapping(value = "/salvarProduto") // Mapeando a url para receber JSON, DE QUALQUER LUGAR QUE VIER O SALVARACESSO VAI SALVAR COM OS DOIS ** ANTES DA /
    public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoriaJava { //Recebe o JSON e converte para o Objeto

        if (produto.getEmpresa() == null || produto.getEmpresa().getId() <= 0){
            throw new ExceptionMentoriaJava("Empresa deve ser cadastrada!");
        }

        // verificar se existe acesso com a descrição já cadastrada
        if (produto.getId() == null){
            List<Produto> produtos = produtoRepository.buscarProdutoPorNomeNaEmpresa(produto.getNome().toUpperCase(), produto.getEmpresa().getId());

            if (!produtos.isEmpty()){
                throw new ExceptionMentoriaJava("Já existe Produto com o nome: " + produto.getNome() + " na empresa: " + produto.getEmpresa().getId());
            }
        }

        if (produto.getCategoriaProduto() == null || produto.getCategoriaProduto().getId() <= 0){
            throw new ExceptionMentoriaJava("Categoria do produto deve ser cadastrada!");
        }

        if (produto.getMarcaProduto() == null || produto.getMarcaProduto().getId() <= 0){
            throw new ExceptionMentoriaJava("Marca do produto deve ser cadastrada!");
        }


         Produto produtoSalvo= produtoRepository.save(produto);

        return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/listarProdutos")
    public ResponseEntity<List<Produto>> listarAcesso(){

        List<Produto> produtoList = produtoRepository.findAll();

        return new ResponseEntity<List<Produto>>(produtoList, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/buscarEmpresaPorProduto/{id}")
    public ResponseEntity<PessoaJuridica> buscarEmpresa(@PathVariable("id") Long id){

        var produtoId = produtoRepository.getById(id);

        var empresa = produtoId.getEmpresa();

        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/buscarPorNome/{nome}")
    public ResponseEntity<List<Produto>> buscarPorNome(@PathVariable("nome") String nome){

        var listaProduto = produtoRepository.buscarProdutoPorNome(nome.toUpperCase());

        return new ResponseEntity<>(listaProduto, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/obterProduto/{id}")
    private ResponseEntity<Produto> obterAcesso(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        var acessoById = produtoRepository.findById(id).orElse(null);

        if (acessoById == null){
            throw new ExceptionMentoriaJava("Não existe Acesso com o ID: " + id);
        }

        return new ResponseEntity<Produto>(acessoById, HttpStatus.OK);
    }



    @ResponseBody
    @PostMapping(value = "/deleteAcesso") // DELETE DO OBJETO INTEIRO
    public ResponseEntity<?> deletarProduto(@RequestBody Produto produto){

        produtoRepository.deleteById(produto.getId());

        return new ResponseEntity<>("Produto Removido", HttpStatus.OK);
    }



    @ResponseBody
    @DeleteMapping(value = "/deleteProdutoPorId/{id}") // DELETE DO OBJETO INTEIRO
    public ResponseEntity<String> deletarProdutoPorId(@PathVariable("id") Long id){

        var produto  = produtoRepository.getById(id);

        produtoRepository.deleteById(id);

        return new ResponseEntity<>("Produto Removido", HttpStatus.OK);
    }

}
