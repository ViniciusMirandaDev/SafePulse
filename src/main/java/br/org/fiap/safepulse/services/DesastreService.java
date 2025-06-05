package br.org.fiap.safepulse.services;

import br.org.fiap.safepulse.domain.entities.Desastre;
import br.org.fiap.safepulse.infra.repositories.DesastreRepository;
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
public class DesastreService {

    private final DesastreRepository desastreRepository;

    public Desastre create(Desastre desastre) {
        return desastreRepository.save(desastre);
    }

    public Optional<Desastre> getById(UUID id) {
        return desastreRepository.findById(id);
    }

    public List<Desastre> getAll() {
        return desastreRepository.findAll();
    }

    public Desastre update(Desastre desastre) {
        return desastreRepository.save(desastre);
    }

    public void delete(UUID id) {
        desastreRepository.deleteById(id);
    }

    public List<Desastre> findByNome(String nome) {
        return desastreRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Desastre> findByDataRange(LocalDate inicio, LocalDate fim) {
        return desastreRepository.findByDataDesastreBetween(inicio, fim);
    }

    public List<Desastre> findByLocalizacao(String localizacao) {
        return desastreRepository.findByLocalizacaoContainingIgnoreCase(localizacao);
    }

    public long countAfterDate(LocalDate dataReferencia) {
        return desastreRepository.countByDataDesastreAfter(dataReferencia);
    }
}
