package br.org.fiap.safepulse.services;

import br.org.fiap.safepulse.domain.entities.DoacaoDoador;
import br.org.fiap.safepulse.domain.entities.DoacaoDoadorId;
import br.org.fiap.safepulse.infra.repositories.DoacaoDoadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DoacaoDoadorService {

    private final DoacaoDoadorRepository doacaoDoadorRepository;

    public DoacaoDoador create(DoacaoDoador doacaoDoador) {
        return doacaoDoadorRepository.save(doacaoDoador);
    }

    public Optional<DoacaoDoador> getById(DoacaoDoadorId id) {
        return doacaoDoadorRepository.findById(id);
    }

    public List<DoacaoDoador> getAll() {
        return doacaoDoadorRepository.findAll();
    }

    public void delete(DoacaoDoadorId id) {
        doacaoDoadorRepository.deleteById(id);
    }

    public List<DoacaoDoador> findByDoacaoId(UUID doacaoId) {
        return doacaoDoadorRepository.findByIdDoacaoId(doacaoId);
    }

    public List<DoacaoDoador> findByDoadorId(UUID doadorId) {
        return doacaoDoadorRepository.findByIdDoadorId(doadorId);
    }

    public List<br.org.fiap.safepulse.domain.entities.Doador> findDoadoresByDoacaoId(UUID doacaoId) {
        return doacaoDoadorRepository.findDoadoresByDoacaoId(doacaoId);
    }

    public List<br.org.fiap.safepulse.domain.entities.Doacao> findDoacoesByDoadorId(UUID doadorId) {
        return doacaoDoadorRepository.findDoacoesByDoadorId(doadorId);
    }
}
