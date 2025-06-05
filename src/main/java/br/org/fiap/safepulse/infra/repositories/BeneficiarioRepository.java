package br.org.fiap.safepulse.infra.repositories;

import br.org.fiap.safepulse.domain.entities.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, UUID> {

    Optional<Beneficiario> findByEmail(String email);

    List<Beneficiario> findByNomeContainingIgnoreCase(String nome);

    List<Beneficiario> findByOngContainingIgnoreCase(String ong);

    boolean existsByTelefone(String telefone);
}
