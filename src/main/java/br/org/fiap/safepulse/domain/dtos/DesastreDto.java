package br.org.fiap.safepulse.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesastreDto {

    @Schema(description = "ID do desastre", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Nome do desastre", example = "Enchente 2025")
    private String nome;

    @Schema(description = "Descrição do desastre", example = "Enchente histórica na região metropolitana")
    private String descricao;

    @Schema(description = "Localização do desastre", example = "Recife, PE")
    private String localizacao;

    @Schema(description = "Data em que o desastre ocorreu", format = "yyyy-MM-dd", example = "2025-06-10")
    private LocalDate dataDesastre;
}
