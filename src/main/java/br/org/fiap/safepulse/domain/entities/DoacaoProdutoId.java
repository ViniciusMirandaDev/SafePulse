package br.org.fiap.safepulse.domain.entities;

import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoacaoProdutoId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "doacao_id", nullable = false)
    private UUID doacaoId;

    @Column(name = "produto_id", nullable = false)
    private UUID produtoId;
}
