package br.edu.iff.ccc.librekan.librekan.service;

import br.edu.iff.ccc.librekan.librekan.model.Quadro;
import br.edu.iff.ccc.librekan.librekan.repository.QuadroRepository;
import br.edu.iff.ccc.librekan.librekan.exception.QuadroNaoEncontradoException;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class QuadroService {
    private final QuadroRepository quadroRepository;

    public QuadroService(QuadroRepository quadroRepository) {
        this.quadroRepository = quadroRepository;
    }

    public List<Quadro> listarTodos() {
        return quadroRepository.findAll();
    }

    public Quadro salvar(Quadro quadro) {
        return quadroRepository.save(quadro);
    }

    public void excluir(Long id) {
        quadroRepository.deleteById(id);
    }

    public Optional<Quadro> buscarPorId(Long id) {
        return Optional.ofNullable(
            quadroRepository.findById(id)
                .orElseThrow(() -> new QuadroNaoEncontradoException("Quadro com id " + id + " não encontrado."))
        );
    }
}
