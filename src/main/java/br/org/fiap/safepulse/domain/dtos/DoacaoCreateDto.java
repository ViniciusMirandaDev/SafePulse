package br.org.fiap.safepulse.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoacaoCreateDto {

    @Schema(description = "ID do desastre associado", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    @NotNull
    private UUID desastreId;

    @Schema(description = "ID do beneficiário associado", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    @NotNull
    private UUID beneficiarioId;

    @Schema(description = "Status da doação", example = "PENDENTE")
    private String status;
}
