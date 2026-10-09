package com.senac.biblioteca.service;

import com.senac.biblioteca.dto.EmprestimoRequest;
import com.senac.biblioteca.entity.Emprestimo;
import com.senac.biblioteca.entity.Livro;
import com.senac.biblioteca.entity.Usuario;
import com.senac.biblioteca.enums.StatusEmprestimo;
import com.senac.biblioteca.repository.EmprestimoRepository;
import com.senac.biblioteca.repository.LivroRepository;
import com.senac.biblioteca.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Camada de serviço de Emprestimo.
 * Valida a existência do usuário e do livro antes de criar um empréstimo.
 */
@Service
public class EmprestimoService {

    private final EmprestimoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(
        EmprestimoRepository repository,
        UsuarioRepository usuarioRepository,
        LivroRepository livroRepository
    ) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    // Lista empréstimos com paginação.
    public Page<Emprestimo> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // Busca um empréstimo pelo identificador.
    public Optional<Emprestimo> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Só cria o empréstimo quando usuário e livro informados existem.
    public Optional<Emprestimo> criar(EmprestimoRequest dados) {
        Optional<Usuario> usuario = usuarioRepository.findById(dados.getUsuarioId());
        Optional<Livro> livro = livroRepository.findById(dados.getLivroId());

        if (usuario.isEmpty() || livro.isEmpty()) {
            return Optional.empty();
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataEmprestimo(dados.getDataEmprestimo());
        emprestimo.setDataPrevistaDevolucao(dados.getDataPrevistaDevolucao());
        emprestimo.setStatus(dados.getStatus());
        emprestimo.setUsuario(usuario.get());
        emprestimo.setLivro(livro.get());

        return Optional.of(repository.save(emprestimo));
    }

    // Atualiza datas e status quando o empréstimo existe.
    public Optional<Emprestimo> atualizar(Long id, EmprestimoRequest dados) {
        Optional<Usuario> usuario = usuarioRepository.findById(dados.getUsuarioId());
        Optional<Livro> livro = livroRepository.findById(dados.getLivroId());

        if (usuario.isEmpty() || livro.isEmpty()) {
            return Optional.empty();
        }

        return repository.findById(id)
            .map(emprestimo -> {
                emprestimo.setDataEmprestimo(dados.getDataEmprestimo());
                emprestimo.setDataPrevistaDevolucao(dados.getDataPrevistaDevolucao());
                emprestimo.setStatus(dados.getStatus());
                emprestimo.setUsuario(usuario.get());
                emprestimo.setLivro(livro.get());
                return repository.save(emprestimo);
            });
    }

    // Exclui pelo id quando encontrado.
    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    // Consulta personalizada pelo enum StatusEmprestimo, também paginada.
    public Page<Emprestimo> buscarPorStatus(StatusEmprestimo status, Pageable pageable) {
        return repository.findByStatus(status, pageable);
    }
}
