package br.org.fiap.safepulse.controllers;

import br.org.fiap.safepulse.domain.dtos.DoacaoCreateDto;
import br.org.fiap.safepulse.domain.dtos.DoacaoDto;
import br.org.fiap.safepulse.domain.entities.Doacao;
import br.org.fiap.safepulse.domain.entities.Desastre;
import br.org.fiap.safepulse.domain.entities.Beneficiario;
import br.org.fiap.safepulse.services.DoacaoService;
import br.org.fiap.safepulse.services.DesastreService;
import br.org.fiap.safepulse.services.BeneficiarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doacoes")
@Tag(name = "Doacao", description = "Operações relacionadas a Doações")
public class DoacaoController {

    private final DoacaoService doacaoService;
    private final DesastreService desastreService;
    private final BeneficiarioService beneficiarioService;

    public DoacaoController(
            DoacaoService doacaoService,
            DesastreService desastreService,
            BeneficiarioService beneficiarioService
    ) {
        this.doacaoService = doacaoService;
        this.desastreService = desastreService;
        this.beneficiarioService = beneficiarioService;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova doação")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Doação criada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<DoacaoDto> create(@Valid @RequestBody DoacaoCreateDto dto) {
        // Verifica existência de Desastre e Beneficiário
        Optional<Desastre> optD = desastreService.getById(dto.getDesastreId());
        Optional<Beneficiario> optB = beneficiarioService.getById(dto.getBeneficiarioId());
        if (optD.isEmpty() || optB.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Doacao entidade = Doacao.builder()
                .desastre(optD.get())
                .beneficiario(optB.get())
                .status(dto.getStatus())
                .build();

        Doacao salvo = doacaoService.create(entidade);

        DoacaoDto resposta = DoacaoDto.builder()
                .id(salvo.getId())
                .desastreId(salvo.getDesastre().getId())
                .beneficiarioId(salvo.getBeneficiario().getId())
                .status(salvo.getStatus())
                .build();

        return ResponseEntity.status(201).body(resposta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter uma doação pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doação encontrada"),
            @ApiResponse(responseCode = "404", description = "Doação não encontrada")
    })
    public ResponseEntity<DoacaoDto> getById(@PathVariable UUID id) {
        Optional<Doacao> opt = doacaoService.getById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Doacao d = opt.get();
        DoacaoDto dto = DoacaoDto.builder()
                .id(d.getId())
                .desastreId(d.getDesastre().getId())
                .beneficiarioId(d.getBeneficiario().getId())
                .status(d.getStatus())
                .build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Listar todas as doações")
    @ApiResponse(responseCode = "200", description = "Lista de doações retornada")
    public ResponseEntity<List<DoacaoDto>> getAll() {
        List<DoacaoDto> lista = doacaoService.getAll().stream()
                .map(d -> DoacaoDto.builder()
                        .id(d.getId())
                        .desastreId(d.getDesastre().getId())
                        .beneficiarioId(d.getBeneficiario().getId())
                        .status(d.getStatus())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma doação existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doação atualizada"),
            @ApiResponse(responseCode = "404", description = "Doação não encontrada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<DoacaoDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody DoacaoCreateDto dto) {

        Optional<Doacao> existente = doacaoService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Verifica existência de Desastre e Beneficiário
        Optional<Desastre> optD = desastreService.getById(dto.getDesastreId());
        Optional<Beneficiario> optB = beneficiarioService.getById(dto.getBeneficiarioId());
        if (optD.isEmpty() || optB.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Doacao entidade = Doacao.builder()
                .id(id)
                .desastre(optD.get())
                .beneficiario(optB.get())
                .status(dto.getStatus())
                .build();

        Doacao atualizado = doacaoService.update(entidade);

        DoacaoDto resposta = DoacaoDto.builder()
                .id(atualizado.getId())
                .desastreId(atualizado.getDesastre().getId())
                .beneficiarioId(atualizado.getBeneficiario().getId())
                .status(atualizado.getStatus())
                .build();

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover uma doação pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Doação removida"),
            @ApiResponse(responseCode = "404", description = "Doação não encontrada")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Optional<Doacao> existente = doacaoService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        doacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/desastre/{desastreId}")
    @Operation(summary = "Listar doações por ID de desastre")
    @ApiResponse(responseCode = "200", description = "Doações retornadas")
    public ResponseEntity<List<DoacaoDto>> findByDesastreId(@PathVariable UUID desastreId) {
        List<DoacaoDto> lista = doacaoService.findByDesastreId(desastreId).stream()
                .map(d -> DoacaoDto.builder()
                        .id(d.getId())
                        .desastreId(d.getDesastre().getId())
                        .beneficiarioId(d.getBeneficiario().getId())
                        .status(d.getStatus())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/search/beneficiario/{beneficiarioId}")
    @Operation(summary = "Listar doações por ID de beneficiário")
    @ApiResponse(responseCode = "200", description = "Doações retornadas")
    public ResponseEntity<List<DoacaoDto>> findByBeneficiarioId(@PathVariable UUID beneficiarioId) {
        List<DoacaoDto> lista = doacaoService.findByBeneficiarioId(beneficiarioId).stream()
                .map(d -> DoacaoDto.builder()
                        .id(d.getId())
                        .desastreId(d.getDesastre().getId())
                        .beneficiarioId(d.getBeneficiario().getId())
                        .status(d.getStatus())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/search/status")
    @Operation(summary = "Listar doações por status")
    @ApiResponse(responseCode = "200", description = "Doações retornadas")
    public ResponseEntity<List<DoacaoDto>> findByStatus(@RequestParam String status) {
        List<DoacaoDto> lista = doacaoService.findByStatus(status).stream()
                .map(d -> DoacaoDto.builder()
                        .id(d.getId())
                        .desastreId(d.getDesastre().getId())
                        .beneficiarioId(d.getBeneficiario().getId())
                        .status(d.getStatus())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/search/desastre/{desastreId}/status")
    @Operation(summary = "Listar doações por ID de desastre e status")
    @ApiResponse(responseCode = "200", description = "Doações retornadas")
    public ResponseEntity<List<DoacaoDto>> findByDesastreIdAndStatus(
            @PathVariable UUID desastreId,
            @RequestParam String status) {
        List<DoacaoDto> lista = doacaoService.findByDesastreIdAndStatus(desastreId, status).stream()
                .map(d -> DoacaoDto.builder()
                        .id(d.getId())
                        .desastreId(d.getDesastre().getId())
                        .beneficiarioId(d.getBeneficiario().getId())
                        .status(d.getStatus())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/search/beneficiario/{beneficiarioId}/status")
    @Operation(summary = "Listar doações por ID de beneficiário e status")
    @ApiResponse(responseCode = "200", description = "Doações retornadas")
    public ResponseEntity<List<DoacaoDto>> findByBeneficiarioIdAndStatus(
            @PathVariable UUID beneficiarioId,
            @RequestParam String status) {
        List<DoacaoDto> lista = doacaoService.findByBeneficiarioIdAndStatus(beneficiarioId, status).stream()
                .map(d -> DoacaoDto.builder()
                        .id(d.getId())
                        .desastreId(d.getDesastre().getId())
                        .beneficiarioId(d.getBeneficiario().getId())
                        .status(d.getStatus())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/count/status")
    @Operation(summary = "Contar doações por status")
    @ApiResponse(responseCode = "200", description = "Contagem retornada")
    public ResponseEntity<Long> countByStatus(@RequestParam String status) {
        long total = doacaoService.countByStatus(status);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/ids/beneficiario/{beneficiarioId}/apos")
    @Operation(summary = "Listar IDs de doações de um beneficiário após uma data de desastre")
    @ApiResponse(responseCode = "200", description = "IDs retornados")
    public ResponseEntity<List<UUID>> findIdsByBeneficiarioIdAndDataDesastreAfter(
            @PathVariable UUID beneficiarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataReferencia) {
        List<UUID> ids = doacaoService.findIdsByBeneficiarioIdAndDataDesastreAfter(beneficiarioId, dataReferencia);
        return ResponseEntity.ok(ids);
    }
}
