package br.org.fiap.safepulse.services;

import br.org.fiap.safepulse.domain.entities.DoacaoProduto;
import br.org.fiap.safepulse.domain.entities.DoacaoProdutoId;
import br.org.fiap.safepulse.infra.repositories.DoacaoProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DoacaoProdutoService {

    private final DoacaoProdutoRepository doacaoProdutoRepository;

    public DoacaoProduto create(DoacaoProduto doacaoProduto) {
        return doacaoProdutoRepository.save(doacaoProduto);
    }

    public Optional<DoacaoProduto> getById(DoacaoProdutoId id) {
        return doacaoProdutoRepository.findById(id);
    }

    public List<DoacaoProduto> getAll() {
        return doacaoProdutoRepository.findAll();
    }

    public void delete(DoacaoProdutoId id) {
        doacaoProdutoRepository.deleteById(id);
    }

    public List<DoacaoProduto> findByDoacaoId(UUID doacaoId) {
        return doacaoProdutoRepository.findByIdDoacaoId(doacaoId);
    }

    public List<DoacaoProduto> findByProdutoId(UUID produtoId) {
        return doacaoProdutoRepository.findByIdProdutoId(produtoId);
    }

    public Integer sumQuantidadeByDoacaoId(UUID doacaoId) {
        return doacaoProdutoRepository.sumQuantidadeByDoacaoId(doacaoId);
    }

    public Integer sumQuantidadeByProdutoId(UUID produtoId) {
        return doacaoProdutoRepository.sumQuantidadeByProdutoId(produtoId);
    }
}
