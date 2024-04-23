package dev.mentoria.lojavirtual_mentoria.controllers;

import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.AvaliacaoProduto;
import dev.mentoria.lojavirtual_mentoria.repository.AvaliacaoProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/avl-produtos")
public class AvaliacaoProdController {

    private final AvaliacaoProdutoRepository  avaliacaoProdutoRepository;


    public AvaliacaoProdController(AvaliacaoProdutoRepository avaliacaoProdutoRepository) {
        this.avaliacaoProdutoRepository = avaliacaoProdutoRepository;
    }

    @ResponseBody
    @PostMapping("/salvarAvaliacao")
    public ResponseEntity<AvaliacaoProduto> salvarAvaliacaoProduto(@RequestBody @Valid AvaliacaoProduto avaliacaoProduto) throws ExceptionMentoriaJava {

        avaliacaoProdutoValida(avaliacaoProduto);

        return ResponseEntity.ok(avaliacaoProdutoRepository.save(avaliacaoProduto));
    }

    @GetMapping("/avaliacao/{id}")
    public ResponseEntity<AvaliacaoProduto> buscarAvaliacaoProduto(@PathVariable Long id) throws ExceptionMentoriaJava {
        if (id == null || id <= 0) {
            throw new ExceptionMentoriaJava("Informe o id da avaliação!");
        }
        return ResponseEntity.ok(avaliacaoProdutoRepository.findById(id).orElse(null));
    }

    @GetMapping("/avaliacoes")
    public ResponseEntity<Iterable<AvaliacaoProduto>> buscarAvaliacoesProduto() {
        return ResponseEntity.ok(avaliacaoProdutoRepository.findAll());
    }

    @GetMapping("/avaliacoesPorProduto/{produtoId}")
    public ResponseEntity<Iterable<AvaliacaoProduto>> buscarAvaliacoesProdutoPorProduto(@PathVariable Long produtoId) throws ExceptionMentoriaJava {
        if (produtoId == null || produtoId <= 0) {
            throw new ExceptionMentoriaJava("Informe o id do produto!");
        }



        return ResponseEntity.ok(avaliacaoProdutoRepository.avaliacaoProduto(produtoId));
    }

    @GetMapping("/avaliacoesPorPessoa/{pessoaId}")
    public ResponseEntity<Iterable<AvaliacaoProduto>> buscarAvaliacoesProdutoPorPessoa(@PathVariable Long pessoaId) throws ExceptionMentoriaJava {
        if (pessoaId == null || pessoaId <= 0) {
            throw new ExceptionMentoriaJava("Informe o id da pessoa!");
        }

//        if (avaliacaoProdutoRepository.avaliacaoPessoa(pessoaId).spliterator().getExactSizeIfKnown() == 0) {
//            throw new ExceptionMentoriaJava("Nenhuma avaliação encontrada para a pessoa informada!");
//        } Duas formas de considerar se está vazio...

        if (avaliacaoProdutoRepository.avaliacaoPessoa(pessoaId).isEmpty()) {
            throw new ExceptionMentoriaJava("Nenhuma avaliação encontrada para a pessoa informada!");
        }

        return ResponseEntity.ok(avaliacaoProdutoRepository.avaliacaoPessoa(pessoaId));
    }

    @GetMapping("/avaliacoesPorPessoaProduto/{pessoaId}/{produtoId}")
    public ResponseEntity<Iterable<AvaliacaoProduto>> buscarAvaliacoesProdutoPorPessoaProduto(
            @PathVariable Long pessoaId, @PathVariable Long produtoId) throws ExceptionMentoriaJava {

        if (pessoaId == null || pessoaId <= 0) {
            throw new ExceptionMentoriaJava("Informe o id da pessoa!");
        }
        if (produtoId == null || produtoId <= 0) {
            throw new ExceptionMentoriaJava("Informe o id do produto!");
        }


        return ResponseEntity.ok(avaliacaoProdutoRepository.avaliacaoProdutoPessoa(pessoaId, produtoId));
    }

    @PostMapping("/atualizarAvaliacao")


    @ResponseBody
    @DeleteMapping("/deletarAvaliacao/{id}")
    public ResponseEntity<?> deletarAvaliacaoProduto(@PathVariable Long id) throws ExceptionMentoriaJava {
        if (id == null || id <= 0) {
            throw new ExceptionMentoriaJava("Informe o id da avaliação!");
        }

        avaliacaoProdutoRepository.deleteById(id);

        return new ResponseEntity<String>("Avaliação deletada com sucesso!", HttpStatus.OK);
    }

    private static void avaliacaoProdutoValida(AvaliacaoProduto avaliacaoProduto) throws ExceptionMentoriaJava {

        if (avaliacaoProduto.getEmpresa() == null ||(avaliacaoProduto.getEmpresa() != null && avaliacaoProduto.getEmpresa().getId() <= 0)) {
            throw new ExceptionMentoriaJava("Informe a empresa dona do registro!");
        }

        if (avaliacaoProduto.getPessoa() == null ||(avaliacaoProduto.getPessoa() != null && avaliacaoProduto.getPessoa().getId() <= 0)) {
            throw new ExceptionMentoriaJava("Informe a pessoa dona do registro!");
        }

        if (avaliacaoProduto.getProduto() == null ||(avaliacaoProduto.getProduto() != null && avaliacaoProduto.getProduto().getId() <= 0)) {
            throw new ExceptionMentoriaJava("Informe o produto!");
        }

        if (avaliacaoProduto.getNota() == null ||(avaliacaoProduto.getNota() != null && avaliacaoProduto.getNota() <= 0)) {
            throw new ExceptionMentoriaJava("Informe a nota! Deve ser entre 1 e 5");
        }

        if (avaliacaoProduto.getDescricao() == null ||(avaliacaoProduto.getDescricao() != null && avaliacaoProduto.getDescricao().isEmpty())) {
            throw new ExceptionMentoriaJava("Informe a descrição!");
        }
    }
}



