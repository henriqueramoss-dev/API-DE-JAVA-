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

/**
 * Camada de serviço de Livro.
 * Valida os relacionamentos com Categoria e Autor antes de salvar o livro.
 */
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

    // Lista livros com paginação.
    public Page<Livro> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // Busca um livro pelo identificador.
    public Optional<Livro> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Valida categoria e autores antes de persistir o novo livro.
    public Optional<Livro> criar(Livro livro) {
        // O livro precisa informar uma categoria já cadastrada.
        if (livro.getCategoria() == null || livro.getCategoria().getId() == null) {
            return Optional.empty();
        }

        Optional<Categoria> categoria = categoriaRepository.findById(livro.getCategoria().getId());

        if (categoria.isEmpty()) {
            return Optional.empty();
        }

        livro.setCategoria(categoria.get());

        // Substitui os ids recebidos pelos objetos Autor existentes no banco.
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

    // Atualiza os dados principais se o livro existir.
    public Optional<Livro> atualizar(Long id, Livro dados) {
        return repository.findById(id)
            .map(livro -> {
                livro.setTitulo(dados.getTitulo());
                livro.setIsbn(dados.getIsbn());
                livro.setAnoPublicacao(dados.getAnoPublicacao());
                return repository.save(livro);
            });
    }

    // Exclui o livro pelo id quando encontrado.
    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    // Consulta personalizada por parte do título, com paginação.
    public Page<Livro> buscarPorTitulo(String titulo, Pageable pageable) {
        return repository.findByTituloContainingIgnoreCase(titulo, pageable);
    }
}
