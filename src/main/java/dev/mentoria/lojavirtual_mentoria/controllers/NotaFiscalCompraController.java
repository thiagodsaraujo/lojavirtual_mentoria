package dev.mentoria.lojavirtual_mentoria.controllers;

import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.NotaFiscalCompra;
import dev.mentoria.lojavirtual_mentoria.model.NotaFiscalVenda;
import dev.mentoria.lojavirtual_mentoria.model.dto.ObejtoRequisicaoRelatorioProdutoAlertaEstoque;
import dev.mentoria.lojavirtual_mentoria.model.dto.ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO;
import dev.mentoria.lojavirtual_mentoria.model.dto.ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO;
import dev.mentoria.lojavirtual_mentoria.repository.NotaFiscalCompraRepository;
import dev.mentoria.lojavirtual_mentoria.repository.NotaFiscalVendaRepository;
import dev.mentoria.lojavirtual_mentoria.service.NotaFiscalCompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notafiscalcompra")
public class NotaFiscalCompraController {

    private final NotaFiscalCompraRepository notaFiscalCompraRepository;

    private final NotaFiscalVendaRepository notaFiscalVendaRepository;


    private final NotaFiscalCompraService notaFiscalCompraService;


    public NotaFiscalCompraController(NotaFiscalCompraRepository notaFiscalCompraRepository, NotaFiscalVendaRepository notaFiscalVendaRepository, NotaFiscalCompraService notaFiscalCompraService) {
        this.notaFiscalCompraRepository = notaFiscalCompraRepository;
        this.notaFiscalVendaRepository = notaFiscalVendaRepository;
        this.notaFiscalCompraService = notaFiscalCompraService;
    }


    @ResponseBody
    @PostMapping("/relatorioProdCompraNotaFiscal")
    public ResponseEntity<List<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO>> relatorioProdCompraNotaFiscal(@RequestBody ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO
                                                                                                                            objetoRequisicaoRelatorioProdCompraNotaFiscalDTO) {
        List<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO> retorno =
                new ArrayList<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO>();

        retorno = notaFiscalCompraService.gerarRelatorioProdCompraNota(objetoRequisicaoRelatorioProdCompraNotaFiscalDTO);



        return new ResponseEntity<List<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO>>(retorno, HttpStatus.OK);

    }

    @ResponseBody
    @PostMapping(value = "/relatorioProdAlertaEstoque")
    public ResponseEntity<List<ObejtoRequisicaoRelatorioProdutoAlertaEstoque>> relatorioProdAlertaEstoque
            (@Valid @RequestBody ObejtoRequisicaoRelatorioProdutoAlertaEstoque obejtoRequisicaoRelatorioProdCompraNotaFiscalDto ){

        List<ObejtoRequisicaoRelatorioProdutoAlertaEstoque> retorno =
                new ArrayList<ObejtoRequisicaoRelatorioProdutoAlertaEstoque>();

        retorno = notaFiscalCompraService.gerarRelatorioAlertaEstoque(obejtoRequisicaoRelatorioProdCompraNotaFiscalDto);


        return new ResponseEntity<List<ObejtoRequisicaoRelatorioProdutoAlertaEstoque>>(retorno, HttpStatus.OK);

    }



    @GetMapping("/listarNotasFiscais")
    public ResponseEntity<List<NotaFiscalCompra>> listarNotasFiscais() {
        return new ResponseEntity<List<NotaFiscalCompra>>(notaFiscalCompraRepository.findAll(), HttpStatus.OK);
    }


    @ResponseBody
    @PostMapping("/salvarNotaFiscal")
    public ResponseEntity<NotaFiscalCompra> salvarNotaFiscalCompra(@RequestBody @Valid NotaFiscalCompra notaFiscalCompra) throws ExceptionMentoriaJava {


        if(notaFiscalCompra.getId() == null) { // Se é um novo registro

            if (notaFiscalCompra.getDescricaoObs() == null) {
                var notaFiscalCompras =
                        notaFiscalCompraRepository.buscarNotaPorDescricao(notaFiscalCompra.getDescricaoObs().toUpperCase().trim());

                if (!notaFiscalCompras.isEmpty()) {
                    throw new ExceptionMentoriaJava("Descrição da Nota Fiscal já cadastrada:" + notaFiscalCompra.getDescricaoObs());
                }

            }
        }

        // Validações

        if (notaFiscalCompra.getPessoa() == null || notaFiscalCompra.getPessoa().getId() <= 0) {
            throw new ExceptionMentoriaJava("Pessoa Jurídica da Nota Fiscal deve ser informada");
        }

        if (notaFiscalCompraRepository.findByNumeroNota(notaFiscalCompra.getNumeroNota()) != null) {
            throw new ExceptionMentoriaJava("Número da Nota Fiscal já cadastrado: " + notaFiscalCompra.getNumeroNota());
        }

        if (notaFiscalCompra.getContaPagar() == null || notaFiscalCompra.getContaPagar().getId() <= 0) {
            throw new ExceptionMentoriaJava("Conta a Pagar da Nota Fiscal deve ser informada");
        }

        if (notaFiscalCompra.getEmpresa() == null || notaFiscalCompra.getEmpresa().getId() <= 0) {
            throw new ExceptionMentoriaJava("Empresa responsável da Nota Fiscal deve ser informada");
        }

        if (notaFiscalCompra.getValorTotal() == null) {
            throw new ExceptionMentoriaJava("Valor Total da Nota Fiscal deve ser informado");
        }


        return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompraRepository.save(notaFiscalCompra), HttpStatus.CREATED);
    }

    @GetMapping("/buscarNotaPorDescricao/{descricao}")
    public ResponseEntity<NotaFiscalCompra> buscarNotaPorDescricao(@PathVariable("descricao") String descricao) throws ExceptionMentoriaJava{

        var notaFiscalCompraList = notaFiscalCompraRepository.findByDescricaoObs(descricao);

        if (notaFiscalCompraList == null) {
            throw new ExceptionMentoriaJava("Nota Fiscal não encontrada");
        }

        return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompraList, HttpStatus.OK);


    }

    @GetMapping("/buscarNotaPorId/{id}")
    public ResponseEntity<NotaFiscalCompra> buscarNotaPorId(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        var notaFiscalCompra = notaFiscalCompraRepository.findById(id).orElse(null);

        if (notaFiscalCompra == null) {
            throw new ExceptionMentoriaJava("Nota Fiscal não encontrada");
        }

        return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompra, HttpStatus.OK);

    }

    @DeleteMapping(value = "/deletarNotaFiscal/{id}")
    public ResponseEntity<?> deletarNotaFiscal(@PathVariable("id") Long id) throws ExceptionMentoriaJava {
        // método de hard delete, não soft, poderia criar um novo atributo para marcar como deletado

        notaFiscalCompraRepository.deleteItemFilhoNotaFiscalCompra(id); // Deleta os filhos
        notaFiscalCompraRepository.deleteById(id); // deleta o pai, nessa ordem.

        return new ResponseEntity("Nota Fiscal deletada",HttpStatus.OK);
    }

    @GetMapping("/obterListaNotaFiscalCompraVenda/{idVenda}")
    public ResponseEntity<List<NotaFiscalVenda>> buscarListaNotaCompraDaVenda(@PathVariable("idVenda") Long idVenda) throws ExceptionMentoriaJava {

        var notaFiscalVendas = notaFiscalVendaRepository.buscaNotaPorVenda(idVenda);

        if (notaFiscalVendas == null) {
            throw new ExceptionMentoriaJava("Nota Fiscal não encontrada");
        }

        return new ResponseEntity<List<NotaFiscalVenda>>(notaFiscalVendas, HttpStatus.OK);

    }

    @GetMapping("/obterObjetoFiscalCompraVenda/{idVenda}")
    public ResponseEntity<NotaFiscalVenda> buscarNotaCompraDaVenda(@PathVariable("idVenda") Long idVenda) throws ExceptionMentoriaJava {

        var notaFiscalVendas = notaFiscalVendaRepository.buscaObjetoNotaPorVenda(idVenda);

        if (notaFiscalVendas == null) {
            throw new ExceptionMentoriaJava("Nota Fiscal não encontrada");
        }

        return new ResponseEntity<NotaFiscalVenda>(notaFiscalVendas, HttpStatus.OK);

    }


}
