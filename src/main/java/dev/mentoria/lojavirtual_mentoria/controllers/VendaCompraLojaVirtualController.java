package dev.mentoria.lojavirtual_mentoria.controllers;

import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.Endereco;
import dev.mentoria.lojavirtual_mentoria.model.PessoaFisica;
import dev.mentoria.lojavirtual_mentoria.model.VendaCompraLojaVirtual;
import dev.mentoria.lojavirtual_mentoria.model.dto.VendaCompraLojaVirtualDTO;
import dev.mentoria.lojavirtual_mentoria.repository.EnderecoRepository;
import dev.mentoria.lojavirtual_mentoria.repository.NotaFiscalVendaRepository;
import dev.mentoria.lojavirtual_mentoria.repository.PessoaFisicaRepository;
import dev.mentoria.lojavirtual_mentoria.repository.VendaCompraLojaVirtualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/venda-compra")
public class VendaCompraLojaVirtualController {

    @Autowired
    private VendaCompraLojaVirtualRepository vendaCompraRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaController pessoaController;

    @Autowired
    private NotaFiscalVendaRepository notaFiscalVendaRepository;


    @PostMapping("/SalvarVenda")
    public ResponseEntity<VendaCompraLojaVirtualDTO> salvarVendaLoja(@RequestBody @Valid VendaCompraLojaVirtual vendaCompraLojaVirtual) throws ExceptionMentoriaJava {

        // validações para a venda

        vendaCompraLojaVirtual.getPessoa().setEmpresa(vendaCompraLojaVirtual.getEmpresa()); // tem que associar a empresa a pessoa
        PessoaFisica pessoaFisica = pessoaController.salvarPessoaFisica(vendaCompraLojaVirtual.getPessoa()).getBody();
        vendaCompraLojaVirtual.setPessoa(pessoaFisica);

        // foi necessário salvar os endereços separadamente para que o id fosse gerado e não gerar erro de chave estrangeira/persistencia
        // quando lidamos com vários objetos relacionados é normal fazer essas associações para salvar antes de persistir o objeto principal
        vendaCompraLojaVirtual.getEnderecoEntrega().setPessoa(pessoaFisica);
        vendaCompraLojaVirtual.getEnderecoEntrega().setEmpresa(vendaCompraLojaVirtual.getEmpresa());

        var enderecoCobranca = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoEntrega());

        vendaCompraLojaVirtual.setEnderecoCobranca(enderecoCobranca);

        vendaCompraLojaVirtual.getEnderecoEntrega().setPessoa(pessoaFisica);
        vendaCompraLojaVirtual.getEnderecoEntrega().setEmpresa(vendaCompraLojaVirtual.getEmpresa());

        var enderecoEntrega = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoEntrega());
        vendaCompraLojaVirtual.setEnderecoEntrega(enderecoEntrega);


        vendaCompraLojaVirtual.getNotaFiscalVenda().setEmpresa(vendaCompraLojaVirtual.getEmpresa());

        //Salva primeiro a venda e todos os dados
        vendaCompraLojaVirtual = vendaCompraRepository.saveAndFlush(vendaCompraLojaVirtual);

        // associar a venda gravada no banco com a nota fiscal

        vendaCompraLojaVirtual.getNotaFiscalVenda().setVendaCompraLojaVirtual(vendaCompraLojaVirtual);

        // Persiste novamente a venda com a nota fiscal novamente pra ficar amarrada na venda
        notaFiscalVendaRepository.saveAndFlush(vendaCompraLojaVirtual.getNotaFiscalVenda());


        VendaCompraLojaVirtualDTO vendaCompraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();
        vendaCompraLojaVirtualDTO.setValorDesconto(vendaCompraLojaVirtual.getValorDesconto());
        vendaCompraLojaVirtualDTO.setValorTotal(vendaCompraLojaVirtual.getValorTotal());

        return new ResponseEntity<VendaCompraLojaVirtualDTO>(vendaCompraLojaVirtualDTO, HttpStatus.OK);
        // passar o objeto de retorno não é o ideial, deve escolher os dados que deve ser retornado para o cliente/tela
        // vamos preparar um dto para retornar os dados que o cliente precisa
    }

    public ResponseEntity<VendaCompraLojaVirtual> buscarVendaLoja(Long id){

        return ResponseEntity.ok(vendaCompraRepository.findById(id).get());
    }



}
