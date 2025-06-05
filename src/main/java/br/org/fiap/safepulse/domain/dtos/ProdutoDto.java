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
public class ProdutoDto {

    @Schema(description = "ID do produto", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Nome do produto", example = "Cobertor Térmico")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Cobertor térmico para uso em abrigos")
    private String descricao;

    @Schema(description = "Unidade de medida (p.ex.: 'un', 'kg')", example = "un")
    private String unidade;
}
