package dev.mentoria.lojavirtual_mentoria.controllers;

import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import dev.mentoria.lojavirtual_mentoria.repository.PessoaJuridicaRepository;
import dev.mentoria.lojavirtual_mentoria.service.PessoaUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pj")
public class PessoaController {


    private final  PessoaJuridicaRepository pessoaRepository;

    private final PessoaUserService pessoaUserService;

    public PessoaController(PessoaJuridicaRepository pessoaRepository, PessoaUserService pessoaUserService) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaUserService = pessoaUserService;
    }


    @ResponseBody
    @PostMapping(value = "/salvarpj")
    public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava {

        if (pessoaJuridica == null){
            throw new ExceptionMentoriaJava("Pessoa Juridica não pode ser nula");
        }

        if (pessoaJuridica.getId() == null && pessoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null){ // Pessoa nova
            throw new ExceptionMentoriaJava("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
        }

        if (pessoaJuridica.getId() == null && pessoaRepository.existeInscEstadualCadastrado(pessoaJuridica.getInscEstadual()) != null){ // Pessoa nova
            throw new ExceptionMentoriaJava("Já existe Inscrição Estadual cadastrada com o número: " + pessoaJuridica.getCnpj());
        }

        // Não está enviando e-mail
        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);

       return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
    }

}
