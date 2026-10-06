package com.senac.biblioteca.service;

import com.senac.biblioteca.entity.Autor;
import com.senac.biblioteca.entity.Categoria;
import com.senac.biblioteca.entity.Livro;
import com.senac.biblioteca.repository.AutorRepository;
import com.senac.biblioteca.repository.CategoriaRepository;
import com.senac.biblioteca.repository.LivroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class LivroService {

    private final LivroRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final AutorRepository autorRepository;

    public LivroService(
        LivroRepository repository,
        CategoriaRepository categoriaRepository,
        AutorRepository autorRepository
    ) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.autorRepository = autorRepository;
    }

    public Page<Livro> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Livro> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Livro> criar(Livro livro) {
        if (livro.getCategoria() == null || livro.getCategoria().getId() == null) {
            return Optional.empty();
        }

        Optional<Categoria> categoria = categoriaRepository.findById(livro.getCategoria().getId());

        if (categoria.isEmpty()) {
            return Optional.empty();
        }

        livro.setCategoria(categoria.get());

        Set<Autor> autores = new HashSet<>();
        for (Autor autor : livro.getAutores()) {
            if (autor.getId() == null) {
                return Optional.empty();
            }

            Optional<Autor> autorEncontrado = autorRepository.findById(autor.getId());

            if (autorEncontrado.isEmpty()) {
                return Optional.empty();
            }

            autores.add(autorEncontrado.get());
        }

        livro.setAutores(autores);
        return Optional.of(repository.save(livro));
    }

    public Optional<Livro> atualizar(Long id, Livro dados) {
        return repository.findById(id)
            .map(livro -> {
                livro.setTitulo(dados.getTitulo());
                livro.setIsbn(dados.getIsbn());
                livro.setAnoPublicacao(dados.getAnoPublicacao());
                return repository.save(livro);
            });
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public Page<Livro> buscarPorTitulo(String titulo, Pageable pageable) {
        return repository.findByTituloContainingIgnoreCase(titulo, pageable);
    }
}
