package dev.mentoria.lojavirtual_mentoria.controllers;


import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.ContaPagar;
import dev.mentoria.lojavirtual_mentoria.repository.ContaPagarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RestController
@RequestMapping(value = "/contaPagar")
public class ContaPagarController {

    private final ContaPagarRepository contaPagarRepository;

    public ContaPagarController(ContaPagarRepository contaPagarRepository) {
        this.contaPagarRepository = contaPagarRepository;
    }

    @ResponseBody // * Poder dar um retorno da API //
    @PostMapping(value = "/salvarContaPagar")
    // Mapeando a url para receber JSON, DE QUALQUER LUGAR QUE VIER O SALVARACESSO VAI SALVAR COM OS DOIS ** ANTES DA /
    public ResponseEntity<ContaPagar> salvarContaPagar(@RequestBody @Valid ContaPagar contaPagar) throws ExceptionMentoriaJava { //Recebe o JSON e converte para o Objeto

        if (contaPagar.getPessoa() == null || contaPagar.getPessoa().getId() <= 0) {
            throw new ExceptionMentoriaJava("Pessoa deve ser cadastrada!");
        }

        if (contaPagar.getEmpresa() == null || contaPagar.getEmpresa().getId() <= 0) {
            throw new ExceptionMentoriaJava("Empresa deve ser cadastrada!");
        }

        if (contaPagar.getPessoa_fornecedor() == null || contaPagar.getPessoa_fornecedor().getId() <= 0) {
            throw new ExceptionMentoriaJava("Fornecedor deve ser cadastrado!");
        }

        if (contaPagar.getId() == null){
            List<ContaPagar> contas = contaPagarRepository.buscarContaDesc(contaPagar.getDescricao().toUpperCase());

            if (!contas.isEmpty()){
                throw new ExceptionMentoriaJava("Já existe conta com a descrição: " + contaPagar.getDescricao());
            }
        }


        if (contaPagar.getValorTotal() == null) {
            throw new ExceptionMentoriaJava("Valor deve ser maior que zero!");
        }


        ContaPagar contaPagarSalva = contaPagarRepository.save(contaPagar);

        return new ResponseEntity<ContaPagar>(contaPagarSalva, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/listarTodasContasPagar")
    public ResponseEntity<List<ContaPagar>> listarAcesso() {

        List<ContaPagar> contaPagarList = contaPagarRepository.findAll();

        return new ResponseEntity<List<ContaPagar>>(contaPagarList, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/buscarPorNome/{desc}")
    public ResponseEntity<List<ContaPagar>> buscarPorNome(@PathVariable("desc") String desc) {

        var listaContasPagar = contaPagarRepository.buscarContaDesc(desc.toUpperCase());

        return new ResponseEntity<>(listaContasPagar, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/buscarContaPorForn/{id}")
    public ResponseEntity<List<ContaPagar>> buscarContaPorFornecedor(@PathVariable("id") Long idForn) {

        var listaContasPagar = contaPagarRepository.buscarContaFornecedor(idForn);

        return new ResponseEntity<>(listaContasPagar, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/obterContaPagar/{id}")
    public ResponseEntity<ContaPagar> obterContaPagar(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        ContaPagar contaPagar = contaPagarRepository.findById(id).orElse(null);

        if (contaPagar == null) {
            throw new ExceptionMentoriaJava("Não existe Conta pagar com o ID: " + id);
        }

        return new ResponseEntity<ContaPagar>(contaPagar, HttpStatus.OK);
    }


    @ResponseBody
    @DeleteMapping(value = "/deleteContaPagar") // DELETE DO OBJETO INTEIRO
    public ResponseEntity<?> deletarContaPagar(@RequestBody ContaPagar contaPagar) {

        contaPagarRepository.deleteById(contaPagar.getId());

        return new ResponseEntity<>("Conta a pagar removida", HttpStatus.OK);
    }


    @ResponseBody
    @DeleteMapping(value = "/deleteContaPagarId/{id}") // DELETE DO OBJETO INTEIRO
    public ResponseEntity<String> deletarContaPorId(@PathVariable("id") Long id) {

        var contaPagarId = contaPagarRepository.getById(id);

        contaPagarRepository.deleteById(id);

        return new ResponseEntity<>("Conta a Pagar Removida", HttpStatus.OK);
    }

}
