package br.org.fiap.safepulse.services;

import br.org.fiap.safepulse.domain.entities.Desastre;
import br.org.fiap.safepulse.infra.repositories.DesastreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.Predicate;   // ← usa jakarta, não javax
import java.time.LocalDate;
import java.util.ArrayList;
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

    /**
     * Busca Desastre aplicando filtros opcionais:
     * - nome (contains, case-insensitive)
     * - localizacao (contains, case-insensitive)
     * - intervalo de datas (inicio/fim)
     *
     * Se todos os parâmetros forem null (ou vazios), retorna todos os registros.
     */
    public List<Desastre> getByFilters(String nome,
                                       String localizacao,
                                       LocalDate inicio,
                                       LocalDate fim) {

        Specification<Desastre> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nome != null && !nome.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("nome")),
                                "%" + nome.toLowerCase() + "%"
                        )
                );
            }

            if (localizacao != null && !localizacao.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("localizacao")),
                                "%" + localizacao.toLowerCase() + "%"
                        )
                );
            }

            if (inicio != null && fim != null) {
                predicates.add(
                        cb.between(root.get("dataDesastre"), inicio, fim)
                );
            } else if (inicio != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("dataDesastre"), inicio)
                );
            } else if (fim != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("dataDesastre"), fim)
                );
            }

            if (predicates.isEmpty()) {
                return cb.conjunction();
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return desastreRepository.findAll(spec);
    }
}
