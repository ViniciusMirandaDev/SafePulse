package br.org.fiap.safepulse.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoacaoDto {

    @Schema(description = "ID da doação", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "ID do desastre associado", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID desastreId;

    @Schema(description = "ID do beneficiário associado", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID beneficiarioId;

    @Schema(description = "Status da doação", example = "PENDENTE")
    private String status;
}
