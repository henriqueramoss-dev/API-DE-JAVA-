package com.senac.biblioteca.service;

import com.senac.biblioteca.entity.Autor;
import com.senac.biblioteca.repository.AutorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    public Page<Autor> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Autor> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Autor criar(Autor autor) {
        return repository.save(autor);
    }

    public Optional<Autor> atualizar(Long id, Autor dados) {
        return repository.findById(id)
            .map(autor -> {
                autor.setNome(dados.getNome());
                return repository.save(autor);
            });
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public Page<Autor> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable);
    }
}
