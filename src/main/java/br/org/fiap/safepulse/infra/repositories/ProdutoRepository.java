package br.org.fiap.safepulse.infra.repositories;

import br.org.fiap.safepulse.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByUnidade(String unidade);

    boolean existsByNome(String nome);
}
