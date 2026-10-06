package com.senac.biblioteca.assembler;

import com.senac.biblioteca.controller.AutorController;
import com.senac.biblioteca.entity.Autor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class AutorModelAssembler implements RepresentationModelAssembler<Autor, EntityModel<Autor>> {

    @Override
    public EntityModel<Autor> toModel(Autor item) {
        return EntityModel.of(
            item,
            linkTo(AutorController.class).slash(item.getId()).withSelfRel(),
            linkTo(AutorController.class).slash(item.getId()).withRel("update"),
            linkTo(AutorController.class).slash(item.getId()).withRel("delete"),
            linkTo(AutorController.class).withRel("autores")
        );
    }
}
