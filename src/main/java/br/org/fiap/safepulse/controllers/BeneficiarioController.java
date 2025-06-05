package br.org.fiap.safepulse.controllers;

import br.org.fiap.safepulse.domain.dtos.BeneficiarioCreateDto;
import br.org.fiap.safepulse.domain.dtos.BeneficiarioDto;
import br.org.fiap.safepulse.domain.entities.Beneficiario;
import br.org.fiap.safepulse.services.BeneficiarioService;
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
@RequestMapping("/api/beneficiarios")
@Tag(name = "Beneficiario", description = "Operações relacionadas a Beneficiários")
public class BeneficiarioController {

    private final BeneficiarioService beneficiarioService;

    public BeneficiarioController(BeneficiarioService beneficiarioService) {
        this.beneficiarioService = beneficiarioService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo beneficiário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Beneficiário criado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<BeneficiarioDto> create(@Valid @RequestBody BeneficiarioCreateDto dto) {
        Beneficiario entidade = Beneficiario.builder()
                .nome(dto.getNome())
                .ong(dto.getOng())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .endereco(dto.getEndereco())
                .build();

        Beneficiario salvo = beneficiarioService.create(entidade);

        BeneficiarioDto resposta = BeneficiarioDto.builder()
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
    @Operation(summary = "Obter um beneficiário pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Beneficiário encontrado"),
            @ApiResponse(responseCode = "404", description = "Beneficiário não encontrado")
    })
    public ResponseEntity<BeneficiarioDto> getById(@PathVariable UUID id) {
        Optional<Beneficiario> opt = beneficiarioService.getById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Beneficiario b = opt.get();
        BeneficiarioDto dto = BeneficiarioDto.builder()
                .id(b.getId())
                .nome(b.getNome())
                .ong(b.getOng())
                .email(b.getEmail())
                .telefone(b.getTelefone())
                .endereco(b.getEndereco())
                .build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Listar todos os beneficiários")
    @ApiResponse(responseCode = "200", description = "Lista de beneficiários retornada")
    public ResponseEntity<List<BeneficiarioDto>> getAll() {
        List<BeneficiarioDto> lista = beneficiarioService.getAll().stream()
                .map(b -> BeneficiarioDto.builder()
                        .id(b.getId())
                        .nome(b.getNome())
                        .ong(b.getOng())
                        .email(b.getEmail())
                        .telefone(b.getTelefone())
                        .endereco(b.getEndereco())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um beneficiário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Beneficiário atualizado"),
            @ApiResponse(responseCode = "404", description = "Beneficiário não encontrado")
    })
    public ResponseEntity<BeneficiarioDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody BeneficiarioCreateDto dto) {

        Optional<Beneficiario> existente = beneficiarioService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Beneficiario entidade = Beneficiario.builder()
                .id(id)
                .nome(dto.getNome())
                .ong(dto.getOng())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .endereco(dto.getEndereco())
                .build();

        Beneficiario atualizado = beneficiarioService.update(entidade);

        BeneficiarioDto resposta = BeneficiarioDto.builder()
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
    @Operation(summary = "Remover um beneficiário pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Beneficiário removido"),
            @ApiResponse(responseCode = "404", description = "Beneficiário não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Optional<Beneficiario> existente = beneficiarioService.getById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        beneficiarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/email")
    @Operation(summary = "Buscar beneficiário por e-mail")
    @ApiResponse(responseCode = "200", description = "Beneficiário retornado")
    public ResponseEntity<BeneficiarioDto> findByEmail(@RequestParam String email) {
        Optional<Beneficiario> opt = beneficiarioService.findByEmail(email);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Beneficiario b = opt.get();
        BeneficiarioDto dto = BeneficiarioDto.builder()
                .id(b.getId())
                .nome(b.getNome())
                .ong(b.getOng())
                .email(b.getEmail())
                .telefone(b.getTelefone())
                .endereco(b.getEndereco())
                .build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search/nome")
    @Operation(summary = "Buscar beneficiários por nome (parcial, case‐insensitive)")
    @ApiResponse(responseCode = "200", description = "Beneficiários retornados")
    public ResponseEntity<List<BeneficiarioDto>> findByNome(@RequestParam String nome) {
        List<BeneficiarioDto> lista = beneficiarioService.findByNome(nome).stream()
                .map(b -> BeneficiarioDto.builder()
                        .id(b.getId())
                        .nome(b.getNome())
                        .ong(b.getOng())
                        .email(b.getEmail())
                        .telefone(b.getTelefone())
                        .endereco(b.getEndereco())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/search/ong")
    @Operation(summary = "Buscar beneficiários por ONG (parcial, case‐insensitive)")
    @ApiResponse(responseCode = "200", description = "Beneficiários retornados")
    public ResponseEntity<List<BeneficiarioDto>> findByOng(@RequestParam String ong) {
        List<BeneficiarioDto> lista = beneficiarioService.findByOng(ong).stream()
                .map(b -> BeneficiarioDto.builder()
                        .id(b.getId())
                        .nome(b.getNome())
                        .ong(b.getOng())
                        .email(b.getEmail())
                        .telefone(b.getTelefone())
                        .endereco(b.getEndereco())
                        .build()
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/exists/telefone")
    @Operation(summary = "Verificar existência de beneficiário por telefone")
    @ApiResponse(responseCode = "200", description = "Existência retornada")
    public ResponseEntity<Boolean> existsByTelefone(@RequestParam String telefone) {
        boolean exists = beneficiarioService.existsByTelefone(telefone);
        return ResponseEntity.ok(exists);
    }
}
