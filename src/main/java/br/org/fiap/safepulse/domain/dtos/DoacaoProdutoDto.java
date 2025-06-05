package br.org.fiap.safepulse.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoacaoProdutoDto {

    @Schema(description = "ID da doação", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID doacaoId;

    @Schema(description = "ID do produto", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID produtoId;

    @Schema(description = "Quantidade do produto doado", example = "10")
    private Integer quantidade;
}
