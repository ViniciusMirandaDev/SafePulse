package br.org.fiap.safepulse.infra.repositories;

import br.org.fiap.safepulse.domain.entities.Doador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoadorRepository extends JpaRepository<Doador, UUID> {

    Optional<Doador> findByEmail(String email);

    List<Doador> findByNomeContainingIgnoreCase(String nome);

    List<Doador> findByOngContainingIgnoreCase(String ong);

    boolean existsByTelefone(String telefone);
}
