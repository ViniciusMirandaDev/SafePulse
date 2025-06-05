package br.org.fiap.safepulse.controllers;

import br.org.fiap.safepulse.domain.dtos.DoacaoDoadorProdutoCreateDto;
import br.org.fiap.safepulse.domain.dtos.DoacaoDoadorProdutoDto;
import br.org.fiap.safepulse.domain.entities.DoacaoDoadorProduto;
import br.org.fiap.safepulse.domain.entities.DoacaoDoadorProdutoId;
import br.org.fiap.safepulse.domain.entities.Doacao;
import br.org.fiap.safepulse.domain.entities.Doador;
import br.org.fiap.safepulse.domain.entities.Produto;
import br.org.fiap.safepulse.services.DoacaoDoadorProdutoService;
import br.org.fiap.safepulse.services.DoacaoService;
import br.org.fiap.safepulse.services.DoadorService;
import br.org.fiap.safepulse.services.ProdutoService;
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
@RequestMapping("/api/doacoes-doadores-produtos")
@Tag(name = "DoacaoDoadorProduto", description = "Operações relacionadas a Doação↔Doador↔Produto")
public class DoacaoDoadorProdutoController {

    private final DoacaoDoadorProdutoService doacaoDoadorProdutoService;
    private final DoacaoService doacaoService;
    private final DoadorService doadorService;
    private final ProdutoService produtoService;

    public DoacaoDoadorProdutoController(
            DoacaoDoadorProdutoService doacaoDoadorProdutoService,
            DoacaoService doacaoService,
            DoadorService doadorService,
            ProdutoService produtoService
    ) {
        this.doacaoDoadorProdutoService = doacaoDoadorProdutoService;
        this.doacaoService = doacaoService;
        this.doadorService = doadorService;
        this.produtoService = produtoService;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova associação Doação↔Doador↔Produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Associação criada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<DoacaoDoadorProdutoDto> create(
            @Valid @RequestBody DoacaoDoadorProdutoCreateDto dto) {

        Optional<Doacao> optDo = doacaoService.getById(dto.getDoacaoId());
        Optional<Doador> optDr = doadorService.getById(dto.getDoadorId());
        Optional<Produto> optPr = produtoService.getById(dto.getProdutoId());
        if (optDo.isEmpty() || optDr.isEmpty() || optPr.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        DoacaoDoadorProduto entidade = DoacaoDoadorProduto.builder()
                .doacao(optDo.get())
                .doador(optDr.get())
                .produto(optPr.get())
                .quantidade(dto.getQuantidade())
                .build();

        DoacaoDoadorProduto salvo = doacaoDoadorProdutoService.create(entidade);

        DoacaoDoadorProdutoDto resposta = DoacaoDoadorProdutoDto.builder()
                .doacaoId(salvo.getDoacao().getId())
                .doadorId(salvo.getDoador().getId())
                .produtoId(salvo.getProduto().getId())
                .quantidade(salvo.getQuantidade())
                .build();

        return ResponseEntity.status(201).body(resposta);
    }

    @GetMapping("/{doacaoId}/{doadorId}/{produtoId}")
    @Operation(summary = "Obter associação Doação↔Doador↔Produto pelo ID composto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Associação encontrada"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    public ResponseEntity<DoacaoDoadorProdutoDto> getById(
            @PathVariable UUID doacaoId,
            @PathVariable UUID doadorId,
            @PathVariable UUID produtoId) {
        DoacaoDoadorProdutoId id = new DoacaoDoadorProdutoId(doacaoId, doadorId, produtoId);
        Optional<DoacaoDoadorProduto> opt = doacaoDoadorProdutoService.getById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        DoacaoDoadorProduto d = opt.get();
        DoacaoDoadorProdutoDto dto = DoacaoDoadorProdutoDto.builder()
                .doacaoId(d.getDoacao().getId())
                .doadorId(d.getDoador().getId())
                .produtoId(d.getProduto().getId())
                .quantidade(d.getQuantidade())
                .build();

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Listar todas as associações Doação↔Doador↔Produto")
    @ApiResponse(responseCode = "200", description = "Lista retornada")
    public ResponseEntity<List<DoacaoDoadorProdutoDto>> getAll() {
        List<DoacaoDoadorProdutoDto> lista = doacaoDoadorProdutoService.getAll().stream()
                .map(d -> DoacaoDoadorProdutoDto.builder()
                        .doacaoId(d.getDoacao().getId())
                        .doadorId(d.getDoador().getId())
                        .produtoId(d.getProduto().getId())
                        .quantidade(d.getQuantidade())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{doacaoId}/{doadorId}/{produtoId}")
    @Operation(summary = "Remover associação Doação↔Doador↔Produto pelo ID composto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Associação removida"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    public ResponseEntity<Void> delete(
            @PathVariable UUID doacaoId,
            @PathVariable UUID doadorId,
            @PathVariable UUID produtoId) {
        DoacaoDoadorProdutoId id = new DoacaoDoadorProdutoId(doacaoId, doadorId, produtoId);
        Optional<DoacaoDoadorProduto> existente = doacaoDoadorProdutoService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        doacaoDoadorProdutoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/doacao/{doacaoId}")
    @Operation(summary = "Buscar associações por ID de doação")
    @ApiResponse(responseCode = "200", description = "Associações retornadas")
    public ResponseEntity<List<DoacaoDoadorProdutoDto>> findByDoacaoId(@PathVariable UUID doacaoId) {
        List<DoacaoDoadorProdutoDto> lista = doacaoDoadorProdutoService.findByDoacaoId(doacaoId).stream()
                .map(d -> DoacaoDoadorProdutoDto.builder()
                        .doacaoId(d.getDoacao().getId())
                        .doadorId(d.getDoador().getId())
                        .produtoId(d.getProduto().getId())
                        .quantidade(d.getQuantidade())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/search/doador/{doadorId}")
    @Operation(summary = "Buscar associações por ID de doador")
    @ApiResponse(responseCode = "200", description = "Associações retornadas")
    public ResponseEntity<List<DoacaoDoadorProdutoDto>> findByDoadorId(@PathVariable UUID doadorId) {
        List<DoacaoDoadorProdutoDto> lista = doacaoDoadorProdutoService.findByDoadorId(doadorId).stream()
                .map(d -> DoacaoDoadorProdutoDto.builder()
                        .doacaoId(d.getDoacao().getId())
                        .doadorId(d.getDoador().getId())
                        .produtoId(d.getProduto().getId())
                        .quantidade(d.getQuantidade())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/search/produto/{produtoId}")
    @Operation(summary = "Buscar associações por ID de produto")
    @ApiResponse(responseCode = "200", description = "Associações retornadas")
    public ResponseEntity<List<DoacaoDoadorProdutoDto>> findByProdutoId(@PathVariable UUID produtoId) {
        List<DoacaoDoadorProdutoDto> lista = doacaoDoadorProdutoService.findByProdutoId(produtoId).stream()
                .map(d -> DoacaoDoadorProdutoDto.builder()
                        .doacaoId(d.getDoacao().getId())
                        .doadorId(d.getDoador().getId())
                        .produtoId(d.getProduto().getId())
                        .quantidade(d.getQuantidade())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/sum/doador/{doadorId}/produto/{produtoId}")
    @Operation(summary = "Somar quantidades de um produto em todas as doações de um mesmo doador")
    @ApiResponse(responseCode = "200", description = "Soma retornada")
    public ResponseEntity<Integer> sumQuantidadeByDoadorIdAndProdutoId(
            @PathVariable UUID doadorId,
            @PathVariable UUID produtoId) {
        Integer soma = doacaoDoadorProdutoService.sumQuantidadeByDoadorIdAndProdutoId(doadorId, produtoId);
        return ResponseEntity.ok(soma);
    }

    @GetMapping("/sum/produto/{produtoId}")
    @Operation(summary = "Somar quantidades totais de um produto em todas as doações")
    @ApiResponse(responseCode = "200", description = "Soma retornada")
    public ResponseEntity<Integer> sumQuantidadeByProdutoId(@PathVariable UUID produtoId) {
        Integer soma = doacaoDoadorProdutoService.sumQuantidadeByProdutoId(produtoId);
        return ResponseEntity.ok(soma);
    }
}
