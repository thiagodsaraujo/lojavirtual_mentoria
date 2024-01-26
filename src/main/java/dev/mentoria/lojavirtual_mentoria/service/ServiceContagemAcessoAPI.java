package dev.mentoria.lojavirtual_mentoria.service;


import dev.mentoria.lojavirtual_mentoria.repository.AcessoEndPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ServiceContagemAcessoAPI {

    @Autowired
    private AcessoEndPointRepository endPointRepository;

    @Transactional
    public void atualizaAcessoEndPoint(String endpoint){
        endPointRepository.incrementarContadorDeAcesso(endpoint);
    }


}
