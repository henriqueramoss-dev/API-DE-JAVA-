package com.senac.biblioteca.assembler;

import com.senac.biblioteca.controller.UsuarioController;
import com.senac.biblioteca.entity.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Responsável por transformar um objeto Usuario em um EntityModel HATEOAS.
 *
 * O EntityModel mantém os dados da entidade e acrescenta links que ajudam
 * o cliente da API a navegar entre as operações disponíveis.
 */
@Component
public class UsuarioModelAssembler
        implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    /**
     * Adiciona os links HATEOAS à resposta da entidade.
     *
     * self   -> consulta o próprio recurso
     * update -> indica a rota utilizada para atualização
     * delete -> indica a rota utilizada para exclusão
     * usuarios -> retorna para a listagem paginada do recurso
     */
    @Override
    public EntityModel<Usuario> toModel(Usuario item) {
        return EntityModel.of(
            item,
            linkTo(UsuarioController.class).slash(item.getId()).withSelfRel(),
            linkTo(UsuarioController.class).slash(item.getId()).withRel("update"),
            linkTo(UsuarioController.class).slash(item.getId()).withRel("delete"),
            linkTo(UsuarioController.class).withRel("usuarios")
        );
    }
}
