package br.org.fiap.safepulse.services;

import br.org.fiap.safepulse.domain.entities.DoacaoDoadorProduto;
import br.org.fiap.safepulse.domain.entities.DoacaoDoadorProdutoId;
import br.org.fiap.safepulse.infra.repositories.DoacaoDoadorProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DoacaoDoadorProdutoService {

    private final DoacaoDoadorProdutoRepository doacaoDoadorProdutoRepository;

    public DoacaoDoadorProduto create(DoacaoDoadorProduto entity) {
        return doacaoDoadorProdutoRepository.save(entity);
    }

    public Optional<DoacaoDoadorProduto> getById(DoacaoDoadorProdutoId id) {
        return doacaoDoadorProdutoRepository.findById(id);
    }

    public List<DoacaoDoadorProduto> getAll() {
        return doacaoDoadorProdutoRepository.findAll();
    }

    public void delete(DoacaoDoadorProdutoId id) {
        doacaoDoadorProdutoRepository.deleteById(id);
    }

    public List<DoacaoDoadorProduto> findByDoacaoId(UUID doacaoId) {
        return doacaoDoadorProdutoRepository.findByIdDoacaoId(doacaoId);
    }

    public List<DoacaoDoadorProduto> findByDoadorId(UUID doadorId) {
        return doacaoDoadorProdutoRepository.findByIdDoadorId(doadorId);
    }

    public List<DoacaoDoadorProduto> findByProdutoId(UUID produtoId) {
        return doacaoDoadorProdutoRepository.findByIdProdutoId(produtoId);
    }

    public Integer sumQuantidadeByDoadorIdAndProdutoId(UUID doadorId, UUID produtoId) {
        return doacaoDoadorProdutoRepository.sumQuantidadeByDoadorIdAndProdutoId(doadorId, produtoId);
    }

    public Integer sumQuantidadeByProdutoId(UUID produtoId) {
        return doacaoDoadorProdutoRepository.sumQuantidadeByProdutoId(produtoId);
    }
}
