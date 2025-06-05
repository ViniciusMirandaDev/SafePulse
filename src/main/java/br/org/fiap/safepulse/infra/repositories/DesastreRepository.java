package br.org.fiap.safepulse.infra.repositories;

import br.org.fiap.safepulse.domain.entities.Desastre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DesastreRepository
        extends JpaRepository<Desastre, UUID>,                 // herda métodos básicos (findAll, save, etc.)
        JpaSpecificationExecutor<Desastre> {

    List<Desastre> findByNomeContainingIgnoreCase(String nome);

    List<Desastre> findByDataDesastreBetween(LocalDate inicio, LocalDate fim);

    List<Desastre> findByLocalizacaoContainingIgnoreCase(String localizacao);

    @Query("SELECT COUNT(d) FROM Desastre d WHERE d.dataDesastre > :dataReferencia")
    long countByDataDesastreAfter(@Param("dataReferencia") LocalDate dataReferencia);
}
