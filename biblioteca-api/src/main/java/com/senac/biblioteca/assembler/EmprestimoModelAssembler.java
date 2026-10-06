package com.senac.biblioteca.assembler;

import com.senac.biblioteca.controller.EmprestimoController;
import com.senac.biblioteca.entity.Emprestimo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class EmprestimoModelAssembler implements RepresentationModelAssembler<Emprestimo, EntityModel<Emprestimo>> {

    @Override
    public EntityModel<Emprestimo> toModel(Emprestimo item) {
        return EntityModel.of(
            item,
            linkTo(EmprestimoController.class).slash(item.getId()).withSelfRel(),
            linkTo(EmprestimoController.class).slash(item.getId()).withRel("update"),
            linkTo(EmprestimoController.class).slash(item.getId()).withRel("delete"),
            linkTo(EmprestimoController.class).withRel("emprestimos")
        );
    }
}
