package br.org.fiap.safepulse.controllers;

import br.org.fiap.safepulse.domain.dtos.DoacaoProdutoCreateDto;
import br.org.fiap.safepulse.domain.dtos.DoacaoProdutoDto;
import br.org.fiap.safepulse.domain.entities.DoacaoProduto;
import br.org.fiap.safepulse.domain.entities.DoacaoProdutoId;
import br.org.fiap.safepulse.domain.entities.Doacao;
import br.org.fiap.safepulse.domain.entities.Produto;
import br.org.fiap.safepulse.services.DoacaoProdutoService;
import br.org.fiap.safepulse.services.DoacaoService;
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
@RequestMapping("/api/doacoes-produtos")
@Tag(name = "DoacaoProduto", description = "Operações relacionadas a Doação↔Produto")
public class DoacaoProdutoController {

    private final DoacaoProdutoService doacaoProdutoService;
    private final DoacaoService doacaoService;
    private final ProdutoService produtoService;

    public DoacaoProdutoController(
            DoacaoProdutoService doacaoProdutoService,
            DoacaoService doacaoService,
            ProdutoService produtoService
    ) {
        this.doacaoProdutoService = doacaoProdutoService;
        this.doacaoService = doacaoService;
        this.produtoService = produtoService;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova associação Doação↔Produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Associação criada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<DoacaoProdutoDto> create(@Valid @RequestBody DoacaoProdutoCreateDto dto) {
        Optional<Doacao> optDo = doacaoService.getById(dto.getDoacaoId());
        Optional<Produto> optPr = produtoService.getById(dto.getProdutoId());
        if (optDo.isEmpty() || optPr.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        DoacaoProduto entidade = DoacaoProduto.builder()
                .doacao(optDo.get())
                .produto(optPr.get())
                .quantidade(dto.getQuantidade())
                .build();

        DoacaoProduto salvo = doacaoProdutoService.create(entidade);

        DoacaoProdutoDto resposta = DoacaoProdutoDto.builder()
                .doacaoId(salvo.getDoacao().getId())
                .produtoId(salvo.getProduto().getId())
                .quantidade(salvo.getQuantidade())
                .build();

        return ResponseEntity.status(201).body(resposta);
    }

    @GetMapping("/{doacaoId}/{produtoId}")
    @Operation(summary = "Obter associação Doação↔Produto pelo ID composto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Associação encontrada"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    public ResponseEntity<DoacaoProdutoDto> getById(
            @PathVariable UUID doacaoId,
            @PathVariable UUID produtoId) {
        DoacaoProdutoId id = new DoacaoProdutoId(doacaoId, produtoId);
        Optional<DoacaoProduto> opt = doacaoProdutoService.getById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        DoacaoProduto d = opt.get();
        DoacaoProdutoDto dto = DoacaoProdutoDto.builder()
                .doacaoId(d.getDoacao().getId())
                .produtoId(d.getProduto().getId())
                .quantidade(d.getQuantidade())
                .build();

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Listar todas as associações Doação↔Produto")
    @ApiResponse(responseCode = "200", description = "Lista retornada")
    public ResponseEntity<List<DoacaoProdutoDto>> getAll() {
        List<DoacaoProdutoDto> lista = doacaoProdutoService.getAll().stream()
                .map(d -> DoacaoProdutoDto.builder()
                        .doacaoId(d.getDoacao().getId())
                        .produtoId(d.getProduto().getId())
                        .quantidade(d.getQuantidade())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{doacaoId}/{produtoId}")
    @Operation(summary = "Remover associação Doação↔Produto pelo ID composto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Associação removida"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    public ResponseEntity<Void> delete(
            @PathVariable UUID doacaoId,
            @PathVariable UUID produtoId) {
        DoacaoProdutoId id = new DoacaoProdutoId(doacaoId, produtoId);
        Optional<DoacaoProduto> existente = doacaoProdutoService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        doacaoProdutoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/doacao/{doacaoId}")
    @Operation(summary = "Buscar associações por ID de doação")
    @ApiResponse(responseCode = "200", description = "Associações retornadas")
    public ResponseEntity<List<DoacaoProdutoDto>> findByDoacaoId(@PathVariable UUID doacaoId) {
        List<DoacaoProdutoDto> lista = doacaoProdutoService.findByDoacaoId(doacaoId).stream()
                .map(d -> DoacaoProdutoDto.builder()
                        .doacaoId(d.getDoacao().getId())
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
    public ResponseEntity<List<DoacaoProdutoDto>> findByProdutoId(@PathVariable UUID produtoId) {
        List<DoacaoProdutoDto> lista = doacaoProdutoService.findByProdutoId(produtoId).stream()
                .map(d -> DoacaoProdutoDto.builder()
                        .doacaoId(d.getDoacao().getId())
                        .produtoId(d.getProduto().getId())
                        .quantidade(d.getQuantidade())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/sum/doacao/{doacaoId}")
    @Operation(summary = "Somar quantidades de produtos em uma doação específica")
    @ApiResponse(responseCode = "200", description = "Soma retornada")
    public ResponseEntity<Integer> sumQuantidadeByDoacaoId(@PathVariable UUID doacaoId) {
        Integer soma = doacaoProdutoService.sumQuantidadeByDoacaoId(doacaoId);
        return ResponseEntity.ok(soma);
    }

    @GetMapping("/sum/produto/{produtoId}")
    @Operation(summary = "Somar quantidades de um produto específico em todas as doações")
    @ApiResponse(responseCode = "200", description = "Soma retornada")
    public ResponseEntity<Integer> sumQuantidadeByProdutoId(@PathVariable UUID produtoId) {
        Integer soma = doacaoProdutoService.sumQuantidadeByProdutoId(produtoId);
        return ResponseEntity.ok(soma);
    }
}
