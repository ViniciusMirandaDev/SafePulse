package br.org.fiap.safepulse.controllers;

import br.org.fiap.safepulse.domain.dtos.ProdutoCreateDto;
import br.org.fiap.safepulse.domain.dtos.ProdutoDto;
import br.org.fiap.safepulse.domain.entities.Produto;
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
@RequestMapping("/api/produtos")
@Tag(name = "Produto", description = "Operações relacionadas a Produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<ProdutoDto> create(@Valid @RequestBody ProdutoCreateDto dto) {
        Produto entidade = Produto.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .unidade(dto.getUnidade())
                .build();

        Produto salvo = produtoService.create(entidade);

        ProdutoDto resposta = ProdutoDto.builder()
                .id(salvo.getId())
                .nome(salvo.getNome())
                .descricao(salvo.getDescricao())
                .unidade(salvo.getUnidade())
                .build();

        return ResponseEntity.status(201).body(resposta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter um produto pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoDto> getById(@PathVariable UUID id) {
        Optional<Produto> opt = produtoService.getById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Produto p = opt.get();
        ProdutoDto dto = ProdutoDto.builder()
                .id(p.getId())
                .nome(p.getNome())
                .descricao(p.getDescricao())
                .unidade(p.getUnidade())
                .build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada")
    public ResponseEntity<List<ProdutoDto>> getAll() {
        List<ProdutoDto> lista = produtoService.getAll().stream()
                .map(p -> ProdutoDto.builder()
                        .id(p.getId())
                        .nome(p.getNome())
                        .descricao(p.getDescricao())
                        .unidade(p.getUnidade())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um produto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProdutoCreateDto dto) {

        Optional<Produto> existente = produtoService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Produto entidade = Produto.builder()
                .id(id)
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .unidade(dto.getUnidade())
                .build();

        Produto atualizado = produtoService.update(entidade);

        ProdutoDto resposta = ProdutoDto.builder()
                .id(atualizado.getId())
                .nome(atualizado.getNome())
                .descricao(atualizado.getDescricao())
                .unidade(atualizado.getUnidade())
                .build();

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um produto pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Optional<Produto> existente = produtoService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/nome")
    @Operation(summary = "Buscar produtos por nome (parcial, case‐insensitive)")
    @ApiResponse(responseCode = "200", description = "Produtos retornados")
    public ResponseEntity<List<ProdutoDto>> findByNome(@RequestParam String nome) {
        List<ProdutoDto> lista = produtoService.findByNome(nome).stream()
                .map(p -> ProdutoDto.builder()
                        .id(p.getId())
                        .nome(p.getNome())
                        .descricao(p.getDescricao())
                        .unidade(p.getUnidade())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/search/unidade")
    @Operation(summary = "Buscar produtos por unidade")
    @ApiResponse(responseCode = "200", description = "Produtos retornados")
    public ResponseEntity<List<ProdutoDto>> findByUnidade(@RequestParam String unidade) {
        List<ProdutoDto> lista = produtoService.findByUnidade(unidade).stream()
                .map(p -> ProdutoDto.builder()
                        .id(p.getId())
                        .nome(p.getNome())
                        .descricao(p.getDescricao())
                        .unidade(p.getUnidade())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/exists/nome")
    @Operation(summary = "Verificar existência de produto por nome")
    @ApiResponse(responseCode = "200", description = "Existência retornada")
    public ResponseEntity<Boolean> existsByNome(@RequestParam String nome) {
        boolean exists = produtoService.existsByNome(nome);
        return ResponseEntity.ok(exists);
    }
}
