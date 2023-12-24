package dev.mentoria.lojavirtual_mentoria.service;


import dev.mentoria.lojavirtual_mentoria.model.Acesso;
import dev.mentoria.lojavirtual_mentoria.repository.AcessoRepository;
import org.springframework.stereotype.Service;

@Service
public class AcessoService {


    private final AcessoRepository acessoRepository;

    public AcessoService(AcessoRepository acessoRepository) {
        this.acessoRepository = acessoRepository;
    }

    public Acesso save(Acesso acesso){
//        Validações antes de salvar
        return acessoRepository.save(acesso);
    }

}
