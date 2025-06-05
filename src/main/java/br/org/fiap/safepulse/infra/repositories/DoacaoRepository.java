package br.org.fiap.safepulse.infra.repositories;

import br.org.fiap.safepulse.domain.entities.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, UUID> {

    List<Doacao> findByDesastreId(UUID desastreId);

    List<Doacao> findByBeneficiarioId(UUID beneficiarioId);

    List<Doacao> findByStatus(String status);

    List<Doacao> findByDesastreIdAndStatus(UUID desastreId, String status);

    List<Doacao> findByBeneficiarioIdAndStatus(UUID beneficiarioId, String status);

    long countByStatus(String status);

    @Query("SELECT d.id FROM Doacao d WHERE d.beneficiario.id = :beneficiarioId AND d.desastre.dataDesastre > :dataReferencia")
    List<UUID> findIdsByBeneficiarioIdAndDataDesastreAfter(@Param("beneficiarioId") UUID beneficiarioId,
                                                           @Param("dataReferencia") LocalDate dataReferencia);
}
