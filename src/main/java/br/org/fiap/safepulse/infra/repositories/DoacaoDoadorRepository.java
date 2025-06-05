package br.org.fiap.safepulse.infra.repositories;

import br.org.fiap.safepulse.domain.entities.DoacaoDoador;
import br.org.fiap.safepulse.domain.entities.DoacaoDoadorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoacaoDoadorRepository extends JpaRepository<DoacaoDoador, DoacaoDoadorId> {

    List<DoacaoDoador> findByIdDoacaoId(UUID doacaoId);

    List<DoacaoDoador> findByIdDoadorId(UUID doadorId);

    @Query("SELECT dd.doador FROM DoacaoDoador dd WHERE dd.doacao.id = :doacaoId")
    List<br.org.fiap.safepulse.domain.entities.Doador> findDoadoresByDoacaoId(@Param("doacaoId") UUID doacaoId);

    @Query("SELECT dd.doacao FROM DoacaoDoador dd WHERE dd.doador.id = :doadorId")
    List<br.org.fiap.safepulse.domain.entities.Doacao> findDoacoesByDoadorId(@Param("doadorId") UUID doadorId);
}
