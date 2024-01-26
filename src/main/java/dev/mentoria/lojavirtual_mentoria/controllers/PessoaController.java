package dev.mentoria.lojavirtual_mentoria.controllers;

import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.Endereco;
import dev.mentoria.lojavirtual_mentoria.model.PessoaFisica;
import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import dev.mentoria.lojavirtual_mentoria.model.dto.CEPDto;
import dev.mentoria.lojavirtual_mentoria.model.dto.ConsultaCNPJDto;
import dev.mentoria.lojavirtual_mentoria.model.enums.TipoPessoa;
import dev.mentoria.lojavirtual_mentoria.repository.EnderecoRepository;
import dev.mentoria.lojavirtual_mentoria.repository.PessoaFisicaRepository;
import dev.mentoria.lojavirtual_mentoria.repository.PessoaJuridicaRepository;
import dev.mentoria.lojavirtual_mentoria.service.PessoaUserService;
import dev.mentoria.lojavirtual_mentoria.service.ServiceContagemAcessoAPI;
import dev.mentoria.lojavirtual_mentoria.util.ValidaCNPJ;
import dev.mentoria.lojavirtual_mentoria.util.ValidaCPF;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pj")
public class PessoaController {


    private final  PessoaJuridicaRepository pessoaRepository;

    private final PessoaUserService pessoaUserService;

    private final PessoaFisicaRepository pessoaFisicaRepository;

    private final EnderecoRepository enderecoRepository;

    private final ServiceContagemAcessoAPI serviceContagemAcessoAPI;



    public PessoaController(PessoaJuridicaRepository pessoaRepository, PessoaUserService pessoaUserService, PessoaFisicaRepository pessoaFisicaRepository, EnderecoRepository enderecoRepository, ServiceContagemAcessoAPI serviceContagemAcessoAPI) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaUserService = pessoaUserService;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.enderecoRepository = enderecoRepository;
        this.serviceContagemAcessoAPI = serviceContagemAcessoAPI;
    }


    @ResponseBody
    @GetMapping(value = "/consultaPfNome/{nome}")
    public ResponseEntity<List<PessoaFisica>> consultaPfPorNome(@PathVariable("nome") String nome){

        serviceContagemAcessoAPI.atualizaAcessoEndPoint("consultanomepf");

        var pfByNomeList = pessoaFisicaRepository.findPFByNomeList(nome.trim().toUpperCase());


        return new ResponseEntity<List<PessoaFisica>>(pfByNomeList, HttpStatus.OK );

    }


    @ResponseBody
    @GetMapping(value = "/consultapfcpf/{cpf}")
    public ResponseEntity<List<PessoaFisica>> consultaPfPorCpf(@PathVariable("cpf") String cpf){

        serviceContagemAcessoAPI.atualizaAcessoEndPoint("consultacpf");

        return new ResponseEntity<List<PessoaFisica>>(pessoaFisicaRepository.existeCpfCadastradoList(cpf), HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping(value = "/consultaPjNome/{nome}")
    public ResponseEntity<List<PessoaJuridica>> consultaPjPorNome(@PathVariable("nome") String nome){

        serviceContagemAcessoAPI.atualizaAcessoEndPoint("consultanomepj");

        var pjByNomeList = pessoaRepository.findPJByNomeList(nome.trim().toUpperCase());

        return new ResponseEntity<List<PessoaJuridica>>(pjByNomeList, HttpStatus.OK );

    }


    @ResponseBody
    @GetMapping(value = "/consultaCnpj/{cnpj}")
    public ResponseEntity<List<PessoaJuridica>> consultaPjPorCnpj(@PathVariable("cnpj") String cnpj){

        serviceContagemAcessoAPI.atualizaAcessoEndPoint("consultacnpj");

        return new ResponseEntity<List<PessoaJuridica>>(pessoaRepository.existeCnpjCadastradoList(cnpj), HttpStatus.OK);

    }


    @ResponseBody
    @GetMapping(value = "/consultaCep/{cep}")
    public ResponseEntity<CEPDto> consultaCep(@PathVariable("cep") String cep){

        serviceContagemAcessoAPI.atualizaAcessoEndPoint("consultacep");


        return new ResponseEntity<CEPDto>(pessoaUserService.consultaCep(cep), HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping(value = "/consultacnpjReceitaws/{cnpj}")
    public ResponseEntity<ConsultaCNPJDto> consultaCpnjReceitaWs(@PathVariable("cnpj") String cnpj){

        serviceContagemAcessoAPI.atualizaAcessoEndPoint("consultacep");


        return new ResponseEntity<ConsultaCNPJDto>(pessoaUserService.consuconsultaCnpjReceitaWs(cnpj), HttpStatus.OK);

    }


    @ResponseBody
    @PostMapping(value = "/salvarpj")
    public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava {

        if (pessoaJuridica == null){
            throw new ExceptionMentoriaJava("Pessoa Juridica não pode ser nula");
        }

        if (pessoaJuridica.getTipoPessoa() == null){
            throw new ExceptionMentoriaJava("Informe o tipo da pessoa: JURIDICA, FISICA, OU FORNECEDOR");
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

        // Pessoa nova e cadastrar o endereço conforme o cep
        if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0){

            for (int p = 0 ; p < pessoaJuridica.getEnderecos().size(); p++){
                // Para cada endereco e consultar o CEP
                CEPDto cepDto = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());

                pessoaJuridica.getEnderecos().get(p).setBairro(cepDto.getBairro());
                pessoaJuridica.getEnderecos().get(p).setCidade(cepDto.getLocalidade());
                pessoaJuridica.getEnderecos().get(p).setComplemento(cepDto.getComplemento());
                pessoaJuridica.getEnderecos().get(p).setRuaLogradouro(cepDto.getLogradouro());
                pessoaJuridica.getEnderecos().get(p).setUf(cepDto.getUf());

            }

            }
        else { // Para atualizar se nao for pessoa nova

            for (int p = 0 ; p < pessoaJuridica.getEnderecos().size(); p++){
                Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();

                if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())){
                    CEPDto cepDto = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());

                    pessoaJuridica.getEnderecos().get(p).setBairro(cepDto.getBairro());
                    pessoaJuridica.getEnderecos().get(p).setCidade(cepDto.getLocalidade());
                    pessoaJuridica.getEnderecos().get(p).setComplemento(cepDto.getComplemento());
                    pessoaJuridica.getEnderecos().get(p).setRuaLogradouro(cepDto.getLogradouro());
                    pessoaJuridica.getEnderecos().get(p).setUf(cepDto.getUf());

                }
            }

        }

        // Não está enviando e-mail
        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);

       return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
    }


    @ResponseBody
    @PostMapping(value = "/salvarpf")
    public ResponseEntity<PessoaFisica> salvarPessoaFisica(@RequestBody @Valid PessoaFisica pessoaFisica) throws ExceptionMentoriaJava {

        if (pessoaFisica == null){
            throw new ExceptionMentoriaJava("Pessoa Fisica não pode ser nula");
        }

        if (pessoaFisica.getTipoPessoa() == null){
            pessoaFisica.setTipoPessoa(TipoPessoa.PF.name());
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
