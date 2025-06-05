package br.org.fiap.safepulse.domain.entities;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "doacao_produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoacaoProduto {

    @EmbeddedId
    private DoacaoProdutoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("doacaoId")
    @JoinColumn(name = "doacao_id", nullable = false)
    private Doacao doacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("produtoId")
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;
}
