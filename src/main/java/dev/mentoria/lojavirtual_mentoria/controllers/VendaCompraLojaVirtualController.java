package dev.mentoria.lojavirtual_mentoria.controllers;

import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.*;
import dev.mentoria.lojavirtual_mentoria.model.dto.ItemVendaDTO;
import dev.mentoria.lojavirtual_mentoria.model.dto.VendaCompraLojaVirtualDTO;
import dev.mentoria.lojavirtual_mentoria.repository.*;
import dev.mentoria.lojavirtual_mentoria.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    @Autowired
    private StatusRastreioRepository statusRastreioRepository;

    @Autowired
    private VendaService vendaService;

    @GetMapping("/listarVendas")
    public ResponseEntity<List<VendaCompraLojaVirtual>> listarVendas() {
        return ResponseEntity.ok(vendaCompraRepository.findAll());
    }

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


        // associação dos produtos com a venda
        for (int i = 0; i < vendaCompraLojaVirtual.getItemVendaLojas().size(); i++) {
            vendaCompraLojaVirtual.getItemVendaLojas().get(i).setEmpresa(vendaCompraLojaVirtual.getEmpresa());
            vendaCompraLojaVirtual.getItemVendaLojas().get(i).setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
        }

        //Salva primeiro a venda e todos os dados
        vendaCompraLojaVirtual = vendaCompraRepository.saveAndFlush(vendaCompraLojaVirtual);

        StatusRastreio statusRastreio = new StatusRastreio();

        statusRastreio.setCentroDistribuicao("Loja Local");
        statusRastreio.setCidade("Local");
        statusRastreio.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        statusRastreio.setEstado("Local");
        statusRastreio.setStatus("Inicio Compra");
        statusRastreio.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);

        statusRastreioRepository.save(statusRastreio);

        // associar a venda gravada no banco com a nota fiscal

        vendaCompraLojaVirtual.getNotaFiscalVenda().setVendaCompraLojaVirtual(vendaCompraLojaVirtual);

        // Persiste novamente a venda com a nota fiscal novamente pra ficar amarrada na venda
        notaFiscalVendaRepository.saveAndFlush(vendaCompraLojaVirtual.getNotaFiscalVenda());


        return retornoVendaPDto(vendaCompraLojaVirtual);
        // passar o objeto de retorno não é o ideial, deve escolher os dados que deve ser retornado para o cliente/tela
        // vamos preparar um dto para retornar os dados que o cliente precisa
    }


    @ResponseBody
    @GetMapping("/buscarVenda/{id}")
    public ResponseEntity<VendaCompraLojaVirtualDTO> consultarVendaID(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        VendaCompraLojaVirtual vendaCompraLojaVirtual = vendaCompraRepository.findByIdExclusao(id);

        if(vendaCompraLojaVirtual == null){ // Talvez dê um bug aqui pois código diferente do professor.
            vendaCompraLojaVirtual = new VendaCompraLojaVirtual();
        }

        return retornoVendaPDto(vendaCompraLojaVirtual);
    }

    private ResponseEntity<VendaCompraLojaVirtualDTO> retornoVendaPDto(VendaCompraLojaVirtual vendaCompraLojaVirtual) {
        VendaCompraLojaVirtualDTO vendaCompraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

        vendaCompraLojaVirtualDTO.setId(vendaCompraLojaVirtual.getId());
        vendaCompraLojaVirtualDTO.setPessoa(vendaCompraLojaVirtual.getPessoa());

        vendaCompraLojaVirtualDTO.setEntrega(vendaCompraLojaVirtual.getEnderecoEntrega());
        vendaCompraLojaVirtualDTO.setCobranca(vendaCompraLojaVirtual.getEnderecoCobranca());

        vendaCompraLojaVirtualDTO.setValorDesc(vendaCompraLojaVirtual.getValorDesconto());
        vendaCompraLojaVirtualDTO.setValorFrete(vendaCompraLojaVirtual.getValorFrete());

        vendaCompraLojaVirtualDTO.setExcluido(vendaCompraLojaVirtual.getExcluido());


        vendaCompraLojaVirtualDTO.setValorTotal(vendaCompraLojaVirtual.getValorTotal());

        for (ItemVendaLoja item : vendaCompraLojaVirtual.getItemVendaLojas()) {

            ItemVendaDTO itemVendaDTO = new ItemVendaDTO();

            itemVendaDTO.setQuantidade(item.getQuantidade());
            itemVendaDTO.setProduto(item.getProduto());
            itemVendaDTO.getProduto().getNome();
            vendaCompraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
        }

        return new ResponseEntity<VendaCompraLojaVirtualDTO>(vendaCompraLojaVirtualDTO, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/exclusaoTotalBanco/{idVenda}")
    public ResponseEntity<String> exclusaoTotalBanco(@PathVariable(value = "idVenda") Long idVenda) {

        vendaService.exclusaoTotalVendaBanco(idVenda);

        return new ResponseEntity<>("Venda excluída com sucesso", HttpStatus.OK);
    }


    @ResponseBody
    @DeleteMapping("/exclusaoTotalBanco/v2/{idVenda}")
    public ResponseEntity<String> exclusaoSoftTotalBanco(@PathVariable(value = "idVenda") Long idVenda) {

        vendaService.softExclusaoVenda(idVenda);

        return new ResponseEntity<>("Foi realizada uma exclusão soft da venda com sucesso", HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/ativaRegistro/{idVenda}")
    public ResponseEntity<String> ativaRegistroVendaBanco(@PathVariable(value = "idVenda") Long idVenda) {

        vendaService.ativaRegistroVendaBanco(idVenda);

        return new ResponseEntity<>("Foi retornada uma venda do BD com sucesso", HttpStatus.OK);
    }

    // faça um método semelhante para consultar pedidos entre datas
    @ResponseBody
    @GetMapping(value = "/consultaVendaData/{dataInicio}/{dataFim}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>>
    consultaVendaPorData(@PathVariable("dataInicio") String dataInicio,
                         @PathVariable("dataFim") String dataFim) throws ParseException {

        List<VendaCompraLojaVirtual> compraLojaVirtual = null;


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date dtINicio = dateFormat.parse(dataInicio);
        Date dtFim = dateFormat.parse(dataFim);



        // funciona com o repository
//        compraLojaVirtual = vendaCompraRepository.vendaPorData(dtINicio, dtFim);
        compraLojaVirtual = vendaService.consultaVendaFaixaData(dataInicio, dataFim);

        if (compraLojaVirtual == null) {
            compraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
        }

        List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();

        for (VendaCompraLojaVirtual vcl : compraLojaVirtual) {

            VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

            compraLojaVirtualDTO.setValorTotal(vcl.getValorTotal());
            compraLojaVirtualDTO.setPessoa(vcl.getPessoa());

            compraLojaVirtualDTO.setEntrega(vcl.getEnderecoEntrega());
            compraLojaVirtualDTO.setCobranca(vcl.getEnderecoCobranca());

            compraLojaVirtualDTO.setValorDesc(vcl.getValorDesconto());
            compraLojaVirtualDTO.setValorFrete(vcl.getValorFrete());
            compraLojaVirtualDTO.setId(vcl.getId());

            for (ItemVendaLoja item : vcl.getItemVendaLojas()) {

                ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
                itemVendaDTO.setQuantidade(item.getQuantidade());
                itemVendaDTO.setProduto(item.getProduto());

                compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
            }

            compraLojaVirtualDTOList.add(compraLojaVirtualDTO);

        }

        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(compraLojaVirtualDTOList, HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping(value = "/consultaVendaDinamica/{valor}/{tipoConsulta}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>>
    consultaVendaPorProduto(@PathVariable("valor") String valor,
                            @PathVariable("tipoConsulta") String tipoConsulta) {

        List<VendaCompraLojaVirtual> compraLojaVirtual = null;

        if (tipoConsulta.equalsIgnoreCase("POR_ID_PROD")) {
            compraLojaVirtual = vendaCompraRepository.vendaPorProduto(Long.parseLong(valor));
        } else if(tipoConsulta.equalsIgnoreCase("POR_NOME_PROD")){
            compraLojaVirtual = vendaCompraRepository.vendaPorNomeProduto(valor.toUpperCase().trim());
        } else if(tipoConsulta.equalsIgnoreCase("POR_NOME_CLIENTE")){
            compraLojaVirtual = vendaCompraRepository.vendaPorNomeCliente(valor.toUpperCase().trim());
        } else if(tipoConsulta.equalsIgnoreCase("POR_ID_CLIENTE")){
            compraLojaVirtual = vendaCompraRepository.vendaPorIdCliente(Long.parseLong(valor));
        } else if (tipoConsulta.equalsIgnoreCase("POR_END_COBRANCA")) {
            compraLojaVirtual = vendaCompraRepository.vendaPorEndCobranca(valor.toUpperCase().trim());
        } else if (tipoConsulta.equalsIgnoreCase("POR_END_ENTREGA")) {
            compraLojaVirtual = vendaCompraRepository.vendaPorEndEntrega(valor.toUpperCase().trim());
        }else if (tipoConsulta.equalsIgnoreCase("POR_CEP")) {
            compraLojaVirtual = vendaCompraRepository.vendaPorCep(valor.toUpperCase().trim());
        } else if (tipoConsulta.equalsIgnoreCase("POR_CPF")) {
            compraLojaVirtual = vendaCompraRepository.vendaPorCpfClienteIgual(valor.toUpperCase().trim());
        } else if (tipoConsulta.equalsIgnoreCase("POR_CNPJ")) {
            compraLojaVirtual = vendaCompraRepository.vendaPorCnpjCLienteIgual(valor.toUpperCase().trim());
        }



        if (compraLojaVirtual == null) {
            compraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
        }

        List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();

        for (VendaCompraLojaVirtual vcl : compraLojaVirtual) {

            VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

            compraLojaVirtualDTO.setValorTotal(vcl.getValorTotal());
            compraLojaVirtualDTO.setPessoa(vcl.getPessoa());

            compraLojaVirtualDTO.setEntrega(vcl.getEnderecoEntrega());
            compraLojaVirtualDTO.setCobranca(vcl.getEnderecoCobranca());

            compraLojaVirtualDTO.setValorDesc(vcl.getValorDesconto());
            compraLojaVirtualDTO.setValorFrete(vcl.getValorFrete());
            compraLojaVirtualDTO.setId(vcl.getId());

            for (ItemVendaLoja item : vcl.getItemVendaLojas()) {

                ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
                itemVendaDTO.setQuantidade(item.getQuantidade());
                itemVendaDTO.setProduto(item.getProduto());

                compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
            }

            compraLojaVirtualDTOList.add(compraLojaVirtualDTO);

        }

        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(compraLojaVirtualDTOList, HttpStatus.OK);
    }


    @GetMapping(value = "/vendaPorCliente/{idCliente}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>> vendaPorCliente(@PathVariable("idCliente") Long idCliente) {

        List<VendaCompraLojaVirtual> compraLojaVirtual = vendaCompraRepository.vendaPorCliente(idCliente);

        if (compraLojaVirtual == null) {
            compraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
        }

        List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();

        for (VendaCompraLojaVirtual vcl : compraLojaVirtual) {

            VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

            compraLojaVirtualDTO.setValorTotal(vcl.getValorTotal());
            compraLojaVirtualDTO.setPessoa(vcl.getPessoa());

            compraLojaVirtualDTO.setEntrega(vcl.getEnderecoEntrega());
            compraLojaVirtualDTO.setCobranca(vcl.getEnderecoCobranca());

            compraLojaVirtualDTO.setValorDesc(vcl.getValorDesconto());
            compraLojaVirtualDTO.setValorFrete(vcl.getValorFrete());
            compraLojaVirtualDTO.setId(vcl.getId());

            for (ItemVendaLoja item : vcl.getItemVendaLojas()) {

                ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
                itemVendaDTO.setQuantidade(item.getQuantidade());
                itemVendaDTO.setProduto(item.getProduto());

                compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
            }

            compraLojaVirtualDTOList.add(compraLojaVirtualDTO);

        }

        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(compraLojaVirtualDTOList, HttpStatus.OK);
    }







    }
