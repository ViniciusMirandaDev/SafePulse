package br.org.fiap.safepulse.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoCreateDto {

    @Schema(description = "Nome do produto", example = "Cobertor Térmico")
    @NotBlank
    private String nome;

    @Schema(description = "Descrição do produto", example = "Cobertor térmico para uso em abrigos")
    private String descricao;

    @Schema(description = "Unidade de medida (p.ex.: 'un', 'kg')", example = "un")
    private String unidade;
}
