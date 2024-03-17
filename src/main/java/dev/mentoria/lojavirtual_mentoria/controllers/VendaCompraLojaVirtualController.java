package dev.mentoria.lojavirtual_mentoria.controllers;

import dev.mentoria.lojavirtual_mentoria.model.VendaCompraLojaVirtual;
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


    @PostMapping("/SalvarVenda")
    public ResponseEntity<VendaCompraLojaVirtual> salvarVendaLoja(@RequestBody @Valid VendaCompraLojaVirtual vendaCompraLojaVirtual){






        return new ResponseEntity<VendaCompraLojaVirtual>(vendaCompraRepository.save(vendaCompraLojaVirtual), HttpStatus.OK);
    }

    public ResponseEntity<VendaCompraLojaVirtual> buscarVendaLoja(Long id){

        return ResponseEntity.ok(vendaCompraRepository.findById(id).get());
    }



}
