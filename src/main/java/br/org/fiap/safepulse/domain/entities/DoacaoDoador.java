package br.org.fiap.safepulse.domain.entities;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "doacao_doador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoacaoDoador {

    @EmbeddedId
    private DoacaoDoadorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("doacaoId")
    @JoinColumn(name = "doacao_id", nullable = false)
    private Doacao doacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("doadorId")
    @JoinColumn(name = "doador_id", nullable = false)
    private Doador doador;
}
