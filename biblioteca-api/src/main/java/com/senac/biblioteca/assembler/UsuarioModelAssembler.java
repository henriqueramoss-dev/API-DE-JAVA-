package com.senac.biblioteca.assembler;

import com.senac.biblioteca.controller.UsuarioController;
import com.senac.biblioteca.entity.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

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
