package br.org.fiap.safepulse.services;

import br.org.fiap.safepulse.domain.entities.Produto;
import br.org.fiap.safepulse.infra.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto create(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Optional<Produto> getById(UUID id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> getAll() {
        return produtoRepository.findAll();
    }

    public Produto update(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void delete(UUID id) {
        produtoRepository.deleteById(id);
    }

    public List<Produto> findByNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Produto> findByUnidade(String unidade) {
        return produtoRepository.findByUnidade(unidade);
    }

    public boolean existsByNome(String nome) {
        return produtoRepository.existsByNome(nome);
    }
}
