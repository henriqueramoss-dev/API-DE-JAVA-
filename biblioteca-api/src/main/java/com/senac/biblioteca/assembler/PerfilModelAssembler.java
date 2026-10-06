package com.senac.biblioteca.assembler;

import com.senac.biblioteca.controller.PerfilController;
import com.senac.biblioteca.entity.Perfil;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PerfilModelAssembler implements RepresentationModelAssembler<Perfil, EntityModel<Perfil>> {

    @Override
    public EntityModel<Perfil> toModel(Perfil item) {
        return EntityModel.of(
            item,
            linkTo(PerfilController.class).slash(item.getId()).withSelfRel(),
            linkTo(PerfilController.class).slash(item.getId()).withRel("update"),
            linkTo(PerfilController.class).slash(item.getId()).withRel("delete"),
            linkTo(PerfilController.class).withRel("perfis")
        );
    }
}
