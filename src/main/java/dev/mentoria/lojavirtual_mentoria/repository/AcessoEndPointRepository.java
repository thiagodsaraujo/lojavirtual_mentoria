package dev.mentoria.lojavirtual_mentoria.repository;

import dev.mentoria.lojavirtual_mentoria.model.AcessoEndPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AcessoEndPointRepository extends JpaRepository<AcessoEndPoint, Long> {

    // Método para encontrar e atualizar a quantidade de acessos com base no endpoint
    @Modifying
    @Query(value = "UPDATE tbl_acesso_endpoint a SET a.qtdAcesso = a.qtdAcesso + 1 WHERE a.nomeEndPoint = ?1")
    void incrementarContadorDeAcesso(String endpoint);
}
