package com.senac.biblioteca.service;

import com.senac.biblioteca.entity.Usuario;
import com.senac.biblioteca.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Page<Usuario> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Usuario criar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Optional<Usuario> atualizar(Long id, Usuario dados) {
        return repository.findById(id)
            .map(usuario -> {
                usuario.setNome(dados.getNome());
                usuario.setEmail(dados.getEmail());
                return repository.save(usuario);
            });
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public Page<Usuario> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable);
    }
}
