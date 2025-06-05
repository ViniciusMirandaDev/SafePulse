package br.org.fiap.safepulse.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiarioCreateDto {

    @Schema(description = "Nome do beneficiário", example = "Maria Silva")
    @NotBlank
    private String nome;

    @Schema(description = "Nome da ONG (se houver)", example = "ONG Ajuda Já")
    private String ong;

    @Schema(description = "Email do beneficiário", example = "maria.silva@example.com")
    @Email
    private String email;

    @Schema(description = "Telefone do beneficiário", example = "(81) 99999-0000")
    private String telefone;

    @Schema(description = "Endereço completo do beneficiário", example = "Rua das Flores, 123, Recife - PE")
    private String endereco;
}
