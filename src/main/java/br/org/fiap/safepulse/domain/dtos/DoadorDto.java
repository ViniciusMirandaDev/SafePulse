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
public class DoadorDto {

    @Schema(description = "ID do doador", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Nome do doador", example = "João Pereira")
    private String nome;

    @Schema(description = "Nome da ONG (se houver)", example = "ONG Doe Esperança")
    private String ong;

    @Schema(description = "Email do doador", example = "joao.pereira@example.com")
    private String email;

    @Schema(description = "Telefone do doador", example = "(81) 98888-1111")
    private String telefone;

    @Schema(description = "Endereço completo do doador", example = "Av. Boa Viagem, 456, Recife - PE")
    private String endereco;
}
