package dev.mentoria.lojavirtual_mentoria.controllers;

import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.PessoaFisica;
import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import dev.mentoria.lojavirtual_mentoria.repository.PessoaFisicaRepository;
import dev.mentoria.lojavirtual_mentoria.repository.PessoaJuridicaRepository;
import dev.mentoria.lojavirtual_mentoria.service.PessoaUserService;
import dev.mentoria.lojavirtual_mentoria.util.ValidaCNPJ;
import dev.mentoria.lojavirtual_mentoria.util.ValidaCPF;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pj")
public class PessoaController {


    private final  PessoaJuridicaRepository pessoaRepository;

    private final PessoaUserService pessoaUserService;

    private final PessoaFisicaRepository pessoaFisicaRepository;

    public PessoaController(PessoaJuridicaRepository pessoaRepository, PessoaUserService pessoaUserService, PessoaFisicaRepository pessoaFisicaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaUserService = pessoaUserService;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
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

        if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())){
            throw new ExceptionMentoriaJava("CNPJ Inválido!");
        }

        // Não está enviando e-mail
        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);

       return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
    }


    @ResponseBody
    @PostMapping(value = "/salvarpf")
    public ResponseEntity<PessoaFisica> salvarPessoaFisica(@RequestBody PessoaFisica pessoaFisica) throws ExceptionMentoriaJava {

        if (pessoaFisica == null){
            throw new ExceptionMentoriaJava("Pessoa Fisica não pode ser nula");
        }

        if (pessoaFisica.getId() == null && pessoaFisicaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null){ // Pessoa nova
            throw new ExceptionMentoriaJava("Já existe CNPJ cadastrado com o número: " + pessoaFisica.getCpf());
        }

        if (!ValidaCPF.isCPF(pessoaFisica.getCpf())){
            throw new ExceptionMentoriaJava("CPF Inválido!");
        }

        // Não está enviando e-mail
        pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);

        return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
    }

}
