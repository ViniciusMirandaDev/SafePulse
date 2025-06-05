package br.org.fiap.safepulse.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoadorCreateDto {

    @Schema(description = "Nome do doador", example = "João Pereira")
    @NotBlank
    private String nome;

    @Schema(description = "Nome da ONG (se houver)", example = "ONG Doe Esperança")
    private String ong;

    @Schema(description = "Email do doador", example = "joao.pereira@example.com")
    @Email
    private String email;

    @Schema(description = "Telefone do doador", example = "(81) 98888-1111")
    private String telefone;

    @Schema(description = "Endereço completo do doador", example = "Av. Boa Viagem, 456, Recife - PE")
    private String endereco;
}
