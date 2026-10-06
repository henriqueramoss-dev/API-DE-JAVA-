package com.senac.biblioteca.service;

import com.senac.biblioteca.entity.Perfil;
import com.senac.biblioteca.entity.Usuario;
import com.senac.biblioteca.repository.PerfilRepository;
import com.senac.biblioteca.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilService {

    private final PerfilRepository repository;
    private final UsuarioRepository usuarioRepository;

    public PerfilService(PerfilRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<Perfil> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Perfil> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Perfil> criar(Perfil perfil) {
        if (perfil.getUsuario() == null || perfil.getUsuario().getId() == null) {
            return Optional.empty();
        }

        Optional<Usuario> usuario = usuarioRepository.findById(perfil.getUsuario().getId());

        if (usuario.isEmpty()) {
            return Optional.empty();
        }

        perfil.setUsuario(usuario.get());
        return Optional.of(repository.save(perfil));
    }

    public Optional<Perfil> atualizar(Long id, Perfil dados) {
        return repository.findById(id)
            .map(perfil -> {
                perfil.setTelefone(dados.getTelefone());
                perfil.setEndereco(dados.getEndereco());
                perfil.setDataNascimento(dados.getDataNascimento());
                return repository.save(perfil);
            });
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public Page<Perfil> buscarPorTelefone(String telefone, Pageable pageable) {
        return repository.findByTelefoneContainingIgnoreCase(telefone, pageable);
    }
}
