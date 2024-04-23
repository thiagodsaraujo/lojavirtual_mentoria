package dev.mentoria.lojavirtual_mentoria.service;

import dev.mentoria.lojavirtual_mentoria.model.Usuario;
import dev.mentoria.lojavirtual_mentoria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
@Service
public class TarefasAutomatizadasService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

    // inicia com 5 segundos depois de subir o projeto e roda a cada 24hrs
    @Scheduled(initialDelay = 5000, fixedDelay = 86400000)
//    @Scheduled(cron = "0 0 12 * * *", zone = "America/Sao_Paulo") // vai rodar tod.o dia as 11 da manha - melhor para prod
    public void notificarUserTrocaSenha() throws MessagingException, UnsupportedEncodingException, InterruptedException {

        var usuariosSenhaVencida = usuarioRepository.usuarioSenhaVencida();

        for (Usuario usuario : usuariosSenhaVencida){
            StringBuilder msg = new StringBuilder();
            msg.append("Olá ").append(usuario.getPessoa().getNome()).append("<br/>");
            msg.append("Está na hora de trocar sua senha, já se passou 90 dias de validade.").append("</br>");
            msg.append("Troque sua senha na loja virtual - JDEV");


//            serviceSendEmail.enviarEmailHtmlOutlook("Troca de Senha - Loja Virtual", msg.toString(), usuario.getLogin());

            // Esperar 3s para liberar recurso da máquina
            Thread.sleep(3000);

        }

    }

}
