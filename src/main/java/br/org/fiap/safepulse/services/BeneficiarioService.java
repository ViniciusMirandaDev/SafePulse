package br.org.fiap.safepulse.services;

import br.org.fiap.safepulse.domain.entities.Beneficiario;
import br.org.fiap.safepulse.infra.repositories.BeneficiarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BeneficiarioService {

    private final BeneficiarioRepository beneficiarioRepository;

    public Beneficiario create(Beneficiario beneficiario) {
        return beneficiarioRepository.save(beneficiario);
    }

    public Optional<Beneficiario> getById(UUID id) {
        return beneficiarioRepository.findById(id);
    }

    public List<Beneficiario> getAll() {
        return beneficiarioRepository.findAll();
    }

    public Beneficiario update(Beneficiario beneficiario) {
        return beneficiarioRepository.save(beneficiario);
    }

    public void delete(UUID id) {
        beneficiarioRepository.deleteById(id);
    }

    public Optional<Beneficiario> findByEmail(String email) {
        return beneficiarioRepository.findByEmail(email);
    }

    public List<Beneficiario> findByNome(String nome) {
        return beneficiarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Beneficiario> findByOng(String ong) {
        return beneficiarioRepository.findByOngContainingIgnoreCase(ong);
    }

    public boolean existsByTelefone(String telefone) {
        return beneficiarioRepository.existsByTelefone(telefone);
    }
}
