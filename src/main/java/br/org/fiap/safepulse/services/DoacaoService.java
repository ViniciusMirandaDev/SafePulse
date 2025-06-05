package br.org.fiap.safepulse.services;

import br.org.fiap.safepulse.domain.entities.Doacao;
import br.org.fiap.safepulse.infra.repositories.DoacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DoacaoService {

    private final DoacaoRepository doacaoRepository;

    public Doacao create(Doacao doacao) {
        return doacaoRepository.save(doacao);
    }

    public Optional<Doacao> getById(UUID id) {
        return doacaoRepository.findById(id);
    }

    public List<Doacao> getAll() {
        return doacaoRepository.findAll();
    }

    public Doacao update(Doacao doacao) {
        return doacaoRepository.save(doacao);
    }

    public void delete(UUID id) {
        doacaoRepository.deleteById(id);
    }

    public List<Doacao> findByDesastreId(UUID desastreId) {
        return doacaoRepository.findByDesastreId(desastreId);
    }

    public List<Doacao> findByBeneficiarioId(UUID beneficiarioId) {
        return doacaoRepository.findByBeneficiarioId(beneficiarioId);
    }

    public List<Doacao> findByStatus(String status) {
        return doacaoRepository.findByStatus(status);
    }

    public List<Doacao> findByDesastreIdAndStatus(UUID desastreId, String status) {
        return doacaoRepository.findByDesastreIdAndStatus(desastreId, status);
    }

    public List<Doacao> findByBeneficiarioIdAndStatus(UUID beneficiarioId, String status) {
        return doacaoRepository.findByBeneficiarioIdAndStatus(beneficiarioId, status);
    }

    public long countByStatus(String status) {
        return doacaoRepository.countByStatus(status);
    }

    public List<UUID> findIdsByBeneficiarioIdAndDataDesastreAfter(UUID beneficiarioId, LocalDate dataReferencia) {
        return doacaoRepository.findIdsByBeneficiarioIdAndDataDesastreAfter(beneficiarioId, dataReferencia);
    }
}
