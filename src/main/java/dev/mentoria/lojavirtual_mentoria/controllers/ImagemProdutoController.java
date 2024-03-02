package dev.mentoria.lojavirtual_mentoria.controllers;


import dev.mentoria.lojavirtual_mentoria.model.ImagemProduto;
import dev.mentoria.lojavirtual_mentoria.model.dto.ImagemProdutoDTO;
import dev.mentoria.lojavirtual_mentoria.repository.ImagemProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping(value = "/img")
public class ImagemProdutoController {

    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    @ResponseBody
    @PostMapping(value = "/salvarImagem")
    public ResponseEntity<ImagemProdutoDTO> salvarImagemProduto(@RequestBody ImagemProduto imagemProduto) {

        ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();

        imagemProdutoDTO.setId(imagemProduto.getId());
        imagemProdutoDTO.setImagemOriginal(imagemProduto.getImagemOriginal());
        imagemProdutoDTO.setImageMiniatura(imagemProduto.getImageMiniatura());
        imagemProdutoDTO.setProdutoId(imagemProduto.getProduto().getId());
        imagemProdutoDTO.setEmpresaId(imagemProduto.getEmpresa().getId());



        imagemProduto = imagemProdutoRepository.save(imagemProduto);// salva e já retorna o objeto salvo

        return new ResponseEntity<ImagemProdutoDTO>(imagemProdutoDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/listarImagens")
    public ResponseEntity<List<ImagemProduto>> listarImagens() {
        List<ImagemProduto> imagens = imagemProdutoRepository.findAll();

        return new ResponseEntity<List<ImagemProduto>>(imagens, HttpStatus.OK);
    }

    @GetMapping(value = "/listarImagens/{id}")
    public ResponseEntity<List<ImagemProduto>> listarImagensPorProduto(@PathVariable("id") Long id) {
        List<ImagemProduto> imagens = imagemProdutoRepository.buscarImagemProduto(id);

        return new ResponseEntity<List<ImagemProduto>>(imagens, HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping(value = "/obterImagem/{idProduto}")
    public ResponseEntity<List<ImagemProduto>> obterImagemPorProduto(@PathVariable("idProduto") Long idProduto) {


        List<ImagemProduto> imagens = imagemProdutoRepository.buscarImagemProduto(idProduto);

        return new ResponseEntity<List<ImagemProduto>>(imagens, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deletarImagemObj")
    public ResponseEntity<?> deletarImagemProduto(@RequestBody ImagemProduto imagemProduto) {

        if (!imagemProdutoRepository.existsById(imagemProduto.getId())) {
            return new ResponseEntity<String>("Imagem já foi removida ou não existe " +
                    "com esse id: " + imagemProduto.getId(),HttpStatus.BAD_REQUEST);
        }

        imagemProdutoRepository.deleteById(imagemProduto.getId());

        return new ResponseEntity("Imagem removida", HttpStatus.OK);

    }

    @DeleteMapping(value = "/deletarImagem/{id}")
    public ResponseEntity<?> deletarImagemProduto(@PathVariable("id") Long id) {

        // fazer as verificações se existe antes de deletar
        if (!imagemProdutoRepository.existsById(id)) {
            return new ResponseEntity<String>("Imagem já foi removida ou não existe com esse id: " + id , HttpStatus.BAD_REQUEST);
        }

        imagemProdutoRepository.deleteById(id);

        return new ResponseEntity<String>("Imagem com o id: " + id + " foi removida!", HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "deletarImagensProduto/{idProduto}")
    public ResponseEntity<?> deletarImagensProduto(@PathVariable("idProduto") Long idProduto) {

        imagemProdutoRepository.existsById(idProduto);

        imagemProdutoRepository.deleteImagens(idProduto);

        return new ResponseEntity<>("Imagens removidas do produto", HttpStatus.OK);
    }



}
