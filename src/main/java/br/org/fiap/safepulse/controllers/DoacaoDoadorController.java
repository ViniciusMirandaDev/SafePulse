package br.org.fiap.safepulse.controllers;

import br.org.fiap.safepulse.domain.dtos.DoacaoDoadorCreateDto;
import br.org.fiap.safepulse.domain.dtos.DoacaoDoadorDto;
import br.org.fiap.safepulse.domain.entities.DoacaoDoador;
import br.org.fiap.safepulse.domain.entities.DoacaoDoadorId;
import br.org.fiap.safepulse.domain.entities.Doacao;
import br.org.fiap.safepulse.domain.entities.Doador;
import br.org.fiap.safepulse.services.DoacaoDoadorService;
import br.org.fiap.safepulse.services.DoacaoService;
import br.org.fiap.safepulse.services.DoadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doacoes-doadores")
@Tag(name = "DoacaoDoador", description = "Operações relacionadas a Doação↔Doador")
public class DoacaoDoadorController {

    private final DoacaoDoadorService doacaoDoadorService;
    private final DoacaoService doacaoService;
    private final DoadorService doadorService;

    public DoacaoDoadorController(
            DoacaoDoadorService doacaoDoadorService,
            DoacaoService doacaoService,
            DoadorService doadorService
    ) {
        this.doacaoDoadorService = doacaoDoadorService;
        this.doacaoService = doacaoService;
        this.doadorService = doadorService;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova associação Doação↔Doador")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Associação criada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<DoacaoDoadorDto> create(@Valid @RequestBody DoacaoDoadorCreateDto dto) {
        Optional<Doacao> optDo = doacaoService.getById(dto.getDoacaoId());
        Optional<Doador> optDr = doadorService.getById(dto.getDoadorId());
        if (optDo.isEmpty() || optDr.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        DoacaoDoador entidade = DoacaoDoador.builder()
                .doacao(optDo.get())
                .doador(optDr.get())
                .build();

        DoacaoDoador salvo = doacaoDoadorService.create(entidade);

        DoacaoDoadorDto resposta = DoacaoDoadorDto.builder()
                .doacaoId(salvo.getDoacao().getId())
                .doadorId(salvo.getDoador().getId())
                .build();

        return ResponseEntity.status(201).body(resposta);
    }

    @GetMapping("/{doacaoId}/{doadorId}")
    @Operation(summary = "Obter associação Doação↔Doador pelo ID composto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Associação encontrada"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    public ResponseEntity<DoacaoDoadorDto> getById(
            @PathVariable UUID doacaoId,
            @PathVariable UUID doadorId) {
        DoacaoDoadorId id = new DoacaoDoadorId(doacaoId, doadorId);
        Optional<DoacaoDoador> opt = doacaoDoadorService.getById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        DoacaoDoador d = opt.get();
        DoacaoDoadorDto dto = DoacaoDoadorDto.builder()
                .doacaoId(d.getDoacao().getId())
                .doadorId(d.getDoador().getId())
                .build();

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Listar todas as associações Doação↔Doador")
    @ApiResponse(responseCode = "200", description = "Lista retornada")
    public ResponseEntity<List<DoacaoDoadorDto>> getAll() {
        List<DoacaoDoadorDto> lista = doacaoDoadorService.getAll().stream()
                .map(d -> DoacaoDoadorDto.builder()
                        .doacaoId(d.getDoacao().getId())
                        .doadorId(d.getDoador().getId())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{doacaoId}/{doadorId}")
    @Operation(summary = "Remover associação Doação↔Doador pelo ID composto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Associação removida"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    public ResponseEntity<Void> delete(
            @PathVariable UUID doacaoId,
            @PathVariable UUID doadorId) {
        DoacaoDoadorId id = new DoacaoDoadorId(doacaoId, doadorId);
        Optional<DoacaoDoador> existente = doacaoDoadorService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        doacaoDoadorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/doacao/{doacaoId}")
    @Operation(summary = "Buscar associações por ID de doação")
    @ApiResponse(responseCode = "200", description = "Associações retornadas")
    public ResponseEntity<List<DoacaoDoadorDto>> findByDoacaoId(@PathVariable UUID doacaoId) {
        List<DoacaoDoadorDto> lista = doacaoDoadorService.findByDoacaoId(doacaoId).stream()
                .map(d -> DoacaoDoadorDto.builder()
                        .doacaoId(d.getDoacao().getId())
                        .doadorId(d.getDoador().getId())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/search/doador/{doadorId}")
    @Operation(summary = "Buscar associações por ID de doador")
    @ApiResponse(responseCode = "200", description = "Associações retornadas")
    public ResponseEntity<List<DoacaoDoadorDto>> findByDoadorId(@PathVariable UUID doadorId) {
        List<DoacaoDoadorDto> lista = doacaoDoadorService.findByDoadorId(doadorId).stream()
                .map(d -> DoacaoDoadorDto.builder()
                        .doacaoId(d.getDoacao().getId())
                        .doadorId(d.getDoador().getId())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
}
