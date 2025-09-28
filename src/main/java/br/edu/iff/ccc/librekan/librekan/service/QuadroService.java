package br.edu.iff.ccc.librekan.librekan.service;

import br.edu.iff.ccc.librekan.librekan.exceptions.RecursoNaoEncontradoException;
import br.edu.iff.ccc.librekan.librekan.exceptions.RegraDeNegocioException;
import br.edu.iff.ccc.librekan.librekan.model.Quadro;
import br.edu.iff.ccc.librekan.librekan.repository.QuadroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuadroService {

    private final QuadroRepository quadroRepository;

    public QuadroService(QuadroRepository quadroRepository) {
        this.quadroRepository = quadroRepository;
    }

    public List<Quadro> listarTodos() {
        return quadroRepository.findAll();
    }

    public List<Quadro> listarPorDonoEmail(String donoEmail) {
        return quadroRepository.findByDonoEmail(donoEmail);
    }

    public Quadro salvar(Quadro quadro) {
        return quadroRepository.save(quadro);
    }

    public Optional<Quadro> buscarPorId(Long id) {
        return quadroRepository.findById(id);
    }

    public void excluir(Long id) {
        if (quadroRepository.findById(id).isEmpty()) {
            throw new RecursoNaoEncontradoException("Quadro não encontrado com o ID: " + id);
        }
        quadroRepository.deleteById(id);
    }
    
     @Transactional
    public Quadro atualizarNome(Long id, String novoNome) {
        if (quadroRepository.findByNomeIgnoreCaseAndIdNot(novoNome, id).isPresent()) {
            throw new RegraDeNegocioException("O nome do quadro '" + novoNome + "' já está em uso.");
        }
        Quadro quadro = quadroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Quadro não encontrado com o id: " + id));
        
        quadro.setNome(novoNome);
        return quadroRepository.save(quadro);
    }
}