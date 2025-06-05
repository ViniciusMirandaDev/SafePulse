package br.org.fiap.safepulse.infra.repositories;

import br.org.fiap.safepulse.domain.entities.DoacaoDoadorProduto;
import br.org.fiap.safepulse.domain.entities.DoacaoDoadorProdutoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoacaoDoadorProdutoRepository extends JpaRepository<DoacaoDoadorProduto, DoacaoDoadorProdutoId> {

    List<DoacaoDoadorProduto> findByIdDoacaoId(UUID doacaoId);

    List<DoacaoDoadorProduto> findByIdDoadorId(UUID doadorId);

    List<DoacaoDoadorProduto> findByIdProdutoId(UUID produtoId);

    @Query("SELECT COALESCE(SUM(ddp.quantidade), 0) FROM DoacaoDoadorProduto ddp WHERE ddp.doador.id = :doadorId AND ddp.produto.id = :produtoId")
    Integer sumQuantidadeByDoadorIdAndProdutoId(@Param("doadorId") UUID doadorId,
                                                @Param("produtoId") UUID produtoId);

    @Query("SELECT COALESCE(SUM(ddp.quantidade), 0) FROM DoacaoDoadorProduto ddp WHERE ddp.produto.id = :produtoId")
    Integer sumQuantidadeByProdutoId(@Param("produtoId") UUID produtoId);
}
