package br.org.fiap.safepulse.controllers;

import br.org.fiap.safepulse.domain.dtos.DoadorCreateDto;
import br.org.fiap.safepulse.domain.dtos.DoadorDto;
import br.org.fiap.safepulse.domain.entities.Doador;
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
@RequestMapping("/api/doadores")
@Tag(name = "Doador", description = "Operações relacionadas a Doadores")
public class DoadorController {

    private final DoadorService doadorService;

    public DoadorController(DoadorService doadorService) {
        this.doadorService = doadorService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo doador")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Doador criado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<DoadorDto> create(@Valid @RequestBody DoadorCreateDto dto) {
        Doador entidade = Doador.builder()
                .nome(dto.getNome())
                .ong(dto.getOng())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .endereco(dto.getEndereco())
                .build();

        Doador salvo = doadorService.create(entidade);

        DoadorDto resposta = DoadorDto.builder()
                .id(salvo.getId())
                .nome(salvo.getNome())
                .ong(salvo.getOng())
                .email(salvo.getEmail())
                .telefone(salvo.getTelefone())
                .endereco(salvo.getEndereco())
                .build();

        return ResponseEntity.status(201).body(resposta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter um doador pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doador encontrado"),
            @ApiResponse(responseCode = "404", description = "Doador não encontrado")
    })
    public ResponseEntity<DoadorDto> getById(@PathVariable UUID id) {
        Optional<Doador> opt = doadorService.getById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Doador d = opt.get();
        DoadorDto dto = DoadorDto.builder()
                .id(d.getId())
                .nome(d.getNome())
                .ong(d.getOng())
                .email(d.getEmail())
                .telefone(d.getTelefone())
                .endereco(d.getEndereco())
                .build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Listar todos os doadores")
    @ApiResponse(responseCode = "200", description = "Lista de doadores retornada")
    public ResponseEntity<List<DoadorDto>> getAll() {
        List<DoadorDto> lista = doadorService.getAll().stream()
                .map(d -> DoadorDto.builder()
                        .id(d.getId())
                        .nome(d.getNome())
                        .ong(d.getOng())
                        .email(d.getEmail())
                        .telefone(d.getTelefone())
                        .endereco(d.getEndereco())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um doador existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doador atualizado"),
            @ApiResponse(responseCode = "404", description = "Doador não encontrado")
    })
    public ResponseEntity<DoadorDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody DoadorCreateDto dto) {

        Optional<Doador> existente = doadorService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Doador entidade = Doador.builder()
                .id(id)
                .nome(dto.getNome())
                .ong(dto.getOng())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .endereco(dto.getEndereco())
                .build();

        Doador atualizado = doadorService.update(entidade);

        DoadorDto resposta = DoadorDto.builder()
                .id(atualizado.getId())
                .nome(atualizado.getNome())
                .ong(atualizado.getOng())
                .email(atualizado.getEmail())
                .telefone(atualizado.getTelefone())
                .endereco(atualizado.getEndereco())
                .build();

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um doador pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Doador removido"),
            @ApiResponse(responseCode = "404", description = "Doador não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Optional<Doador> existente = doadorService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        doadorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/email")
    @Operation(summary = "Buscar doador por e-mail")
    @ApiResponse(responseCode = "200", description = "Doador retornado")
    public ResponseEntity<DoadorDto> findByEmail(@RequestParam String email) {
        Optional<Doador> opt = doadorService.findByEmail(email);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Doador d = opt.get();
        DoadorDto dto = DoadorDto.builder()
                .id(d.getId())
                .nome(d.getNome())
                .ong(d.getOng())
                .email(d.getEmail())
                .telefone(d.getTelefone())
                .endereco(d.getEndereco())
                .build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search/nome")
    @Operation(summary = "Buscar doadores por nome (parcial, case‐insensitive)")
    @ApiResponse(responseCode = "200", description = "Doadores retornados")
    public ResponseEntity<List<DoadorDto>> findByNome(@RequestParam String nome) {
        List<DoadorDto> lista = doadorService.findByNome(nome).stream()
                .map(d -> DoadorDto.builder()
                        .id(d.getId())
                        .nome(d.getNome())
                        .ong(d.getOng())
                        .email(d.getEmail())
                        .telefone(d.getTelefone())
                        .endereco(d.getEndereco())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/search/ong")
    @Operation(summary = "Buscar doadores por ONG (parcial, case‐insensitive)")
    @ApiResponse(responseCode = "200", description = "Doadores retornados")
    public ResponseEntity<List<DoadorDto>> findByOng(@RequestParam String ong) {
        List<DoadorDto> lista = doadorService.findByOng(ong).stream()
                .map(d -> DoadorDto.builder()
                        .id(d.getId())
                        .nome(d.getNome())
                        .ong(d.getOng())
                        .email(d.getEmail())
                        .telefone(d.getTelefone())
                        .endereco(d.getEndereco())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/exists/telefone")
    @Operation(summary = "Verificar existência de doador por telefone")
    @ApiResponse(responseCode = "200", description = "Existência retornada")
    public ResponseEntity<Boolean> existsByTelefone(@RequestParam String telefone) {
        boolean exists = doadorService.existsByTelefone(telefone);
        return ResponseEntity.ok(exists);
    }
}
