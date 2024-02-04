package dev.mentoria.lojavirtual_mentoria.controllers;

import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.Acesso;
import dev.mentoria.lojavirtual_mentoria.model.CategoriaProduto;
import dev.mentoria.lojavirtual_mentoria.model.dto.CategoriaProdutoDTO;
import dev.mentoria.lojavirtual_mentoria.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cat")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @ResponseBody
    @PostMapping("/salvarCategoria")
    public ResponseEntity<CategoriaProdutoDTO> salvarCategoria(@RequestBody CategoriaProduto categoria) throws ExceptionMentoriaJava {

        if (categoria.getEmpresa() == null || categoria.getEmpresa().getId() == null || categoria.getEmpresa().getId() <= 0){
            throw new ExceptionMentoriaJava("A empresa deve ser informada.");
        }

        // para atualizar e cadastrar novo
        if (categoria.getId() == null && categoriaRepository.existeCategoria(categoria.getNomeDesc().toUpperCase())
                || categoriaRepository.countByNomeDesc(categoria.getNomeDesc().toUpperCase()) > 0){
            throw new ExceptionMentoriaJava("A categoria nesta empresa já existe");
        }


        CategoriaProduto categoriaSalva = categoriaRepository.save(categoria);

        CategoriaProdutoDTO categoriaProdutoDTO = new CategoriaProdutoDTO();

        categoriaProdutoDTO.setId(categoriaSalva.getId());
        categoriaProdutoDTO.setNomeDesc(categoriaSalva.getNomeDesc());
        categoriaProdutoDTO.setEmpresa(categoriaSalva.getEmpresa().getId().toString());


        return new ResponseEntity<CategoriaProdutoDTO>(categoriaProdutoDTO, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/listarCategorias")
    public ResponseEntity<List<CategoriaProduto>> listarAcesso(){

        var categoriaProdutoList = categoriaRepository.findAll();

        return new ResponseEntity<List<CategoriaProduto>>(categoriaProdutoList, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/buscarPorDesc/{desc}")
    public ResponseEntity<List<CategoriaProduto>> buscarPorDesc(@PathVariable("desc") String desc){

        List<CategoriaProduto> categoriaPesquisada =
                categoriaRepository.buscarCategoriaPorDescricacao(desc.toUpperCase());

        return new ResponseEntity<List<CategoriaProduto>>(categoriaPesquisada, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/obterCategoria/{id}")
    //Pode ter várias categorias com descrições iguais mas em empresas diferentes
    private ResponseEntity<CategoriaProduto> obterCategoria(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        var categoriaPorId = categoriaRepository.findById(id).orElse(null);

        if (categoriaPorId == null){
            throw new ExceptionMentoriaJava("Não existe Categoria com o ID: " + id);
        }

        return new ResponseEntity<CategoriaProduto>(categoriaPorId, HttpStatus.OK);
    }



    @ResponseBody
    @PostMapping(value = "/deleteAcesso") // DELETE DO OBJETO INTEIRO
    public ResponseEntity<?> deleteAcesso(@RequestBody CategoriaProduto categoriaProduto){

        if (categoriaRepository.findById(categoriaProduto.getId()).isPresent() == false){
            return new ResponseEntity<>("Não existe categoria no banco", HttpStatus.NO_CONTENT);
        }
        categoriaRepository.deleteById(categoriaProduto.getId());

        return new ResponseEntity<>("Categoria Removida", HttpStatus.OK);
    }



    @ResponseBody
//    @Secured({"ROLE_ADMIN", "ROLE_GERENTE"})
    @DeleteMapping(value = "/deletarCategoriaPorId/{id}") // DELETE DO OBJETO INTEIRO
    public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id    ){


        categoriaRepository.deleteById(id);

        return new ResponseEntity<>("Categoria Removida", HttpStatus.NO_CONTENT);
    }

}
