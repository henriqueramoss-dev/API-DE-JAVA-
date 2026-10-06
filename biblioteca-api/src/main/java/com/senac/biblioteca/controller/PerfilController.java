package com.senac.biblioteca.controller;

import com.senac.biblioteca.entity.Perfil;
import com.senac.biblioteca.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/perfis")
@Tag(name = "Perfis", description = "Endpoints para gerenciamento dos perfis dos usuários")
public class PerfilController {

    private final PerfilService service;

    public PerfilController(PerfilService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar perfis", description = "Retorna os perfis cadastrados de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Perfis listados com sucesso")
    public Page<Perfil> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar perfil por ID", description = "Retorna um perfil pelo seu identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    public ResponseEntity<Perfil> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar perfil", description = "Cria um perfil para um usuário já cadastrado.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Perfil criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário não encontrado")
    })
    public ResponseEntity<Perfil> criar(@Valid @RequestBody Perfil perfil) {
        return service.criar(perfil)
            .map(criado -> ResponseEntity.status(HttpStatus.CREATED).body(criado))
            .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar perfil", description = "Atualiza telefone, endereço e data de nascimento.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    public ResponseEntity<Perfil> atualizar(@PathVariable Long id, @Valid @RequestBody Perfil perfil) {
        return service.atualizar(id, perfil)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir perfil", description = "Exclui um perfil pelo seu identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Perfil excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!service.excluir(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar perfis por telefone", description = "Busca perfis pelo telefone usando paginação.")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public Page<Perfil> buscarPorTelefone(@RequestParam String telefone, Pageable pageable) {
        return service.buscarPorTelefone(telefone, pageable);
    }
}
