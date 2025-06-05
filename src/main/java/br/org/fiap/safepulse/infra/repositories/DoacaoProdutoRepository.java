package br.org.fiap.safepulse.infra.repositories;

import br.org.fiap.safepulse.domain.entities.DoacaoProduto;
import br.org.fiap.safepulse.domain.entities.DoacaoProdutoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoacaoProdutoRepository extends JpaRepository<DoacaoProduto, DoacaoProdutoId> {

    List<DoacaoProduto> findByIdDoacaoId(UUID doacaoId);

    List<DoacaoProduto> findByIdProdutoId(UUID produtoId);

    @Query("SELECT COALESCE(SUM(dp.quantidade), 0) FROM DoacaoProduto dp WHERE dp.doacao.id = :doacaoId")
    Integer sumQuantidadeByDoacaoId(@Param("doacaoId") UUID doacaoId);

    @Query("SELECT COALESCE(SUM(dp.quantidade), 0) FROM DoacaoProduto dp WHERE dp.produto.id = :produtoId")
    Integer sumQuantidadeByProdutoId(@Param("produtoId") UUID produtoId);
}
