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
public class BeneficiarioDto {

    @Schema(description = "ID do beneficiário", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Nome do beneficiário", example = "Maria Silva")
    private String nome;

    @Schema(description = "Nome da ONG (se houver)", example = "ONG Ajuda Já")
    private String ong;

    @Schema(description = "Email do beneficiário", example = "maria.silva@example.com")
    private String email;

    @Schema(description = "Telefone do beneficiário", example = "(81) 99999-0000")
    private String telefone;

    @Schema(description = "Endereço completo do beneficiário", example = "Rua das Flores, 123, Recife - PE")
    private String endereco;
}
