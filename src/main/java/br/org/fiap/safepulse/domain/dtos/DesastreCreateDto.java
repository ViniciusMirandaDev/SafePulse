package br.org.fiap.safepulse.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesastreCreateDto {

    @Schema(description = "Nome do desastre", example = "Enchente 2025")
    @NotBlank
    private String nome;

    @Schema(description = "Descrição do desastre", example = "Enchente histórica na região metropolitana")
    private String descricao;

    @Schema(description = "Localização do desastre", example = "Recife, PE")
    private String localizacao;

    @Schema(description = "Data em que o desastre ocorreu", format = "yyyy-MM-dd", example = "2025-06-10")
    @NotNull
    private LocalDate dataDesastre;
}
