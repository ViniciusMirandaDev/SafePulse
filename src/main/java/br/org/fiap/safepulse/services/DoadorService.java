package br.org.fiap.safepulse.services;

import br.org.fiap.safepulse.domain.entities.Doador;
import br.org.fiap.safepulse.infra.repositories.DoadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DoadorService {

    private final DoadorRepository doadorRepository;

    public Doador create(Doador doador) {
        return doadorRepository.save(doador);
    }

    public Optional<Doador> getById(UUID id) {
        return doadorRepository.findById(id);
    }

    public List<Doador> getAll() {
        return doadorRepository.findAll();
    }

    public Doador update(Doador doador) {
        return doadorRepository.save(doador);
    }

    public void delete(UUID id) {
        doadorRepository.deleteById(id);
    }

    public Optional<Doador> findByEmail(String email) {
        return doadorRepository.findByEmail(email);
    }

    public List<Doador> findByNome(String nome) {
        return doadorRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Doador> findByOng(String ong) {
        return doadorRepository.findByOngContainingIgnoreCase(ong);
    }

    public boolean existsByTelefone(String telefone) {
        return doadorRepository.existsByTelefone(telefone);
    }
}
