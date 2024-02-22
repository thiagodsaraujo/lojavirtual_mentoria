package dev.mentoria.lojavirtual_mentoria.controllers;


import com.sun.xml.bind.v2.TODO;
import dev.mentoria.lojavirtual_mentoria.ExceptionMentoriaJava;
import dev.mentoria.lojavirtual_mentoria.model.ImagemProduto;
import dev.mentoria.lojavirtual_mentoria.model.PessoaJuridica;
import dev.mentoria.lojavirtual_mentoria.model.Produto;
import dev.mentoria.lojavirtual_mentoria.repository.ProdutoRepository;
import dev.mentoria.lojavirtual_mentoria.service.ServiceSendEmail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Controller
@RestController
@RequestMapping(value = "/prod")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

//    private final ServiceSendEmail serviceSendEmail;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @ResponseBody // * Poder dar um retorno da API //
    @PostMapping(value = "/salvarProduto") // Mapeando a url para receber JSON, DE QUALQUER LUGAR QUE VIER O SALVARACESSO VAI SALVAR COM OS DOIS ** ANTES DA /
    public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoriaJava, IOException { //Recebe o JSON e converte para o Objeto

        validarProduto(produto);

        // Processar o cadastro de imagens
        // associar quem é pai e quem é filho para associar a FK no banco
        if (produto.getId() == null){ // produto novo
            processarImagens(produto);
        }

         Produto produtoSalvo= produtoRepository.save(produto);

        if (
                produto.getAlertaQtdEstoque() && produto.getQtdEstoque() <= 1){
            // TODO: 22/02/2024  : // enviar email para o Gerente/usuario da quantidade pequena em estoque, mudar o serviço de envio de email para aws
            StringBuilder html = new StringBuilder()
                    .append("<html>")
                    .append("<body>")
                    .append("<h1>Alerta de Estoque</h1>")
                    .append("<p>Produto: " + produto.getNome() + "</p>")
                    .append("<p> ID: " + produto.getId() + "</p>")
                    .append("<p>Quantidade em Estoque: " + produto.getQtdEstoque() + "</p>")
                    .append("</body>")
                    .append("</html>");

            if (produto.getEmpresa().getEmail() != null && !produto.getEmpresa().getEmail().trim().isEmpty()){
                //serviceSendEmail.sendEmail(produto.getEmpresa().getEmail(), "Alerta de Estoque", html.toString());
            }
        }

        return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.OK);
    }


    private void validarProduto(Produto produto) throws ExceptionMentoriaJava {

        if (produto.getNome().length() < 10){
            throw new ExceptionMentoriaJava("Nome do produto deve ter mais de 10 letras.");
        }

        if(produto.getQtdEstoque() < 1 || produto.getQtdEstoque() == null){
            throw new ExceptionMentoriaJava("Quantidade em estoque deve ser informada!");
        }

        if(produto.getTipoUnidade().trim().isEmpty() || produto.getTipoUnidade() == null){
            throw new ExceptionMentoriaJava("Tipo de unidade do produto deve ser informado!");
        }

        if (produto.getEmpresa() == null || produto.getEmpresa().getId() <= 0){
            throw new ExceptionMentoriaJava("Empresa deve ser cadastrada!");
        }

        // verificar se existe acesso com a descrição já cadastrada
        if (produto.getId() == null){

            List<Produto> produtos = produtoRepository.buscarProdutoPorNomeNaEmpresa(produto.getNome().toUpperCase(), produto.getEmpresa().getId());

            if (!produtos.isEmpty()){
                throw new ExceptionMentoriaJava("Já existe Produto com o nome: " + produto.getNome() + " na empresa: " + produto.getEmpresa().getId());
            }
        }

        if (produto.getCategoriaProduto() == null || produto.getCategoriaProduto().getId() <= 0){
            throw new ExceptionMentoriaJava("Categoria do produto deve ser cadastrada!");
        }

        if (produto.getMarcaProduto() == null || produto.getMarcaProduto().getId() <= 0){
            throw new ExceptionMentoriaJava("Marca do produto deve ser cadastrada!");
        }

        if (produto.getImagens() == null || produto.getImagens().isEmpty()
        || produto.getImagens().size() == 0){
            throw new ExceptionMentoriaJava("Imagem do produto deve ser cadastrada!");
        }

        if (produto.getImagens().size() < 3){
            throw new ExceptionMentoriaJava("Deve ser informado no mínimo 3 imagens do produto!");
        }

        if (produto.getImagens().size() > 6){
            throw new ExceptionMentoriaJava("Deve ser informado no máximo 6 imagens do produto!");
        }
    }

    // Extrai o processamento de imagens para um método separado
    private void processarImagens(Produto produto) throws IOException {
        for (ImagemProduto imagem : produto.getImagens()) {
            imagem.setProduto(produto); // Configurar o pai da imagem
            imagem.setEmpresa(produto.getEmpresa()); // Configurar a empresa da imagem

            String base64Image = "";

            if (imagem.getImagemOriginal().contains("data:image")) {
                base64Image = imagem.getImagemOriginal().split(",")[1];
            } else {
                base64Image = imagem.getImagemOriginal();
            }

            byte[] imagemByte = DatatypeConverter.parseBase64Binary(base64Image);

            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));

            if (bufferedImage != null) {
                int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

                int largura = Integer.parseInt("800");
                int altura = Integer.parseInt("600");

                BufferedImage resizedImage = new BufferedImage(largura, altura, type);

                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(bufferedImage, 0, 0, largura, altura, null);
                g.dispose();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, "png", baos);

                String miniImgBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());

                imagem.setImageMiniatura(miniImgBase64);

                // Liberar recursos
                bufferedImage.flush();
                resizedImage.flush();
                baos.flush();
                baos.close();
            }
        }
    }


    @GetMapping(value = "/listarProdutos")
    public ResponseEntity<List<Produto>> listarAcesso(){

        List<Produto> produtoList = produtoRepository.findAll();

        return new ResponseEntity<List<Produto>>(produtoList, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/buscarEmpresaPorProduto/{id}")
    public ResponseEntity<PessoaJuridica> buscarEmpresa(@PathVariable("id") Long id){

        var produtoId = produtoRepository.getById(id);

        var empresa = produtoId.getEmpresa();

        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/buscarPorNome/{nome}")
    public ResponseEntity<List<Produto>> buscarPorNome(@PathVariable("nome") String nome){

        var listaProduto = produtoRepository.buscarProdutoPorNome(nome.toUpperCase());

        return new ResponseEntity<>(listaProduto, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/obterProduto/{id}")
    private ResponseEntity<Produto> obterAcesso(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        var acessoById = produtoRepository.findById(id).orElse(null);

        if (acessoById == null){
            throw new ExceptionMentoriaJava("Não existe Acesso com o ID: " + id);
        }

        return new ResponseEntity<Produto>(acessoById, HttpStatus.OK);
    }



    @ResponseBody
    @PostMapping(value = "/deleteAcesso") // DELETE DO OBJETO INTEIRO
    public ResponseEntity<?> deletarProduto(@RequestBody Produto produto){

        produtoRepository.deleteById(produto.getId());

        return new ResponseEntity<>("Produto Removido", HttpStatus.OK);
    }



    @ResponseBody
    @DeleteMapping(value = "/deleteProdutoPorId/{id}") // DELETE DO OBJETO INTEIRO
    public ResponseEntity<String> deletarProdutoPorId(@PathVariable("id") Long id){

        var produto  = produtoRepository.getById(id);

        produtoRepository.deleteById(id);

        return new ResponseEntity<>("Produto Removido", HttpStatus.OK);
    }

}
