package com.senac.biblioteca.service;

import com.senac.biblioteca.entity.Categoria;
import com.senac.biblioteca.repository.CategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public Page<Categoria> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Categoria criar(Categoria categoria) {
        return repository.save(categoria);
    }

    public Optional<Categoria> atualizar(Long id, Categoria dados) {
        return repository.findById(id)
            .map(categoria -> {
                categoria.setNome(dados.getNome());
                return repository.save(categoria);
            });
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public Page<Categoria> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable);
    }
}
