package com.senac.biblioteca.controller;

import com.senac.biblioteca.assembler.PerfilModelAssembler;
import com.senac.biblioteca.entity.Perfil;
import com.senac.biblioteca.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/perfis")
@Tag(name = "Perfis", description = "Endpoints para gerenciamento dos perfis dos usuários")
public class PerfilController {

    private final PerfilService service;
    private final PerfilModelAssembler assembler;
    private final PagedResourcesAssembler<Perfil> pagedResourcesAssembler;

    public PerfilController(
        PerfilService service,
        PerfilModelAssembler assembler,
        PagedResourcesAssembler<Perfil> pagedResourcesAssembler
    ) {
        this.service = service;
        this.assembler = assembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    @Operation(
        summary = "Listar perfis",
        description = "Retorna os perfis cadastrados de forma paginada."
    )
    @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso")
    public ResponseEntity<PagedModel<EntityModel<Perfil>>> listar(
        @ParameterObject @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable
    ) {
        Page<Perfil> pagina = service.listar(pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(pagina, assembler));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar perfil por ID",
        description = "Retorna um perfil a partir do seu identificador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    public ResponseEntity<EntityModel<Perfil>> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Cadastrar perfil",
        description = "Cria um perfil para um usuário já cadastrado."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Perfil criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<EntityModel<Perfil>> criar(@Valid @RequestBody Perfil item) {
        return service.criar(item)
            .map(assembler::toModel)
            .map(model -> ResponseEntity.status(HttpStatus.CREATED).body(model))
            .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar perfil",
        description = "Atualiza telefone, endereço e data de nascimento."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    public ResponseEntity<EntityModel<Perfil>> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody Perfil item
    ) {
        return service.atualizar(id, item)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir perfil",
        description = "Exclui um perfil pelo seu identificador."
    )
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
    @Operation(
        summary = "Buscar perfis por telefone",
        description = "Busca perfis pelo telefone usando paginação."
    )
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public ResponseEntity<PagedModel<EntityModel<Perfil>>> buscar(
        @RequestParam String telefone,
        @ParameterObject @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable
    ) {
        Page<Perfil> pagina = service.buscarPorTelefone(telefone, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(pagina, assembler));
    }
}
