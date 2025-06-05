package br.org.fiap.safepulse.controllers;

import br.org.fiap.safepulse.domain.dtos.DesastreCreateDto;
import br.org.fiap.safepulse.domain.dtos.DesastreDto;
import br.org.fiap.safepulse.domain.entities.Desastre;
import br.org.fiap.safepulse.services.DesastreService;
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
@RequestMapping("/api/desastres")
@Tag(name = "Desastre", description = "Operações relacionadas a Desastres")
public class DesastreController {

    private final DesastreService desastreService;

    public DesastreController(DesastreService desastreService) {
        this.desastreService = desastreService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo desastre")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Desastre criado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<DesastreDto> create(@Valid @RequestBody DesastreCreateDto dto) {
        Desastre entidade = Desastre.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .localizacao(dto.getLocalizacao())
                .dataDesastre(dto.getDataDesastre())
                .build();

        Desastre salvo = desastreService.create(entidade);

        DesastreDto resposta = DesastreDto.builder()
                .id(salvo.getId())
                .nome(salvo.getNome())
                .descricao(salvo.getDescricao())
                .localizacao(salvo.getLocalizacao())
                .dataDesastre(salvo.getDataDesastre())
                .build();

        return ResponseEntity.status(201).body(resposta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter um desastre pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Desastre encontrado"),
            @ApiResponse(responseCode = "404", description = "Desastre não encontrado")
    })
    public ResponseEntity<DesastreDto> getById(@PathVariable UUID id) {
        Optional<Desastre> opt = desastreService.getById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Desastre d = opt.get();
        DesastreDto dto = DesastreDto.builder()
                .id(d.getId())
                .nome(d.getNome())
                .descricao(d.getDescricao())
                .localizacao(d.getLocalizacao())
                .dataDesastre(d.getDataDesastre())
                .build();
        return ResponseEntity.ok(dto);
    }

    /**
     * Lista todos os desastres ou filtra pelos parâmetros opcionais:
     * - nome (contains, ignore-case)
     * - localizacao (contains, ignore-case)
     * - intervalo de datas (inicio/fim)
     *
     * Exemplos:
     * GET /desastres
     * GET /desastres?nome=fogo
     * GET /desastres?localizacao=rio&inicio=2025-06-01&fim=2025-06-10
     */
    @GetMapping
    @Operation(summary = "Listar desastres com filtros opcionais")
    @ApiResponse(responseCode = "200", description = "Lista de desastres retornada")
    public ResponseEntity<List<DesastreDto>> getAll(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "localizacao", required = false) String localizacao,
            @RequestParam(value = "inicio", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(value = "fim", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        List<Desastre> entidades = desastreService.getByFilters(nome, localizacao, inicio, fim);

        List<DesastreDto> dtos = entidades.stream()
                .map(d -> DesastreDto.builder()
                        .id(d.getId())
                        .nome(d.getNome())
                        .descricao(d.getDescricao())
                        .localizacao(d.getLocalizacao())
                        .dataDesastre(d.getDataDesastre())
                        .build()
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um desastre existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Desastre atualizado"),
            @ApiResponse(responseCode = "404", description = "Desastre não encontrado")
    })
    public ResponseEntity<DesastreDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody DesastreCreateDto dto) {

        Optional<Desastre> existente = desastreService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Desastre entidade = Desastre.builder()
                .id(id)
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .localizacao(dto.getLocalizacao())
                .dataDesastre(dto.getDataDesastre())
                .build();

        Desastre atualizado = desastreService.update(entidade);

        DesastreDto resposta = DesastreDto.builder()
                .id(atualizado.getId())
                .nome(atualizado.getNome())
                .descricao(atualizado.getDescricao())
                .localizacao(atualizado.getLocalizacao())
                .dataDesastre(atualizado.getDataDesastre())
                .build();

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um desastre pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Desastre removido"),
            @ApiResponse(responseCode = "404", description = "Desastre não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Optional<Desastre> existente = desastreService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        desastreService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/nome")
    @Operation(summary = "Buscar desastres por nome (parcial, case‐insensitive)")
    @ApiResponse(responseCode = "200", description = "Desastres retornados")
    public ResponseEntity<List<DesastreDto>> findByNome(@RequestParam String nome) {
        List<DesastreDto> lista = desastreService.findByNome(nome).stream()
                .map(d -> DesastreDto.builder()
                        .id(d.getId())
                        .nome(d.getNome())
                        .descricao(d.getDescricao())
                        .localizacao(d.getLocalizacao())
                        .dataDesastre(d.getDataDesastre())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/search/datarange")
    @Operation(summary = "Buscar desastres em um intervalo de datas")
    @ApiResponse(responseCode = "200", description = "Desastres retornados")
    public ResponseEntity<List<DesastreDto>> findByDataRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        List<DesastreDto> lista = desastreService.findByDataRange(inicio, fim).stream()
                .map(d -> DesastreDto.builder()
                        .id(d.getId())
                        .nome(d.getNome())
                        .descricao(d.getDescricao())
                        .localizacao(d.getLocalizacao())
                        .dataDesastre(d.getDataDesastre())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/search/localizacao")
    @Operation(summary = "Buscar desastres por localização (parcial, case‐insensitive)")
    @ApiResponse(responseCode = "200", description = "Desastres retornados")
    public ResponseEntity<List<DesastreDto>> findByLocalizacao(@RequestParam String localizacao) {
        List<DesastreDto> lista = desastreService.findByLocalizacao(localizacao).stream()
                .map(d -> DesastreDto.builder()
                        .id(d.getId())
                        .nome(d.getNome())
                        .descricao(d.getDescricao())
                        .localizacao(d.getLocalizacao())
                        .dataDesastre(d.getDataDesastre())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/count/apos")
    @Operation(summary = "Contar desastres após uma data de referência")
    @ApiResponse(responseCode = "200", description = "Contagem retornada")
    public ResponseEntity<Long> countAfterDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataReferencia) {
        long total = desastreService.countAfterDate(dataReferencia);
        return ResponseEntity.ok(total);
    }
}
