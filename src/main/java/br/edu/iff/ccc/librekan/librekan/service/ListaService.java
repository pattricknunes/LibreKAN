package br.edu.iff.ccc.librekan.librekan.service;

import br.edu.iff.ccc.librekan.librekan.exceptions.RecursoNaoEncontradoException;
import br.edu.iff.ccc.librekan.librekan.model.Lista;
import br.edu.iff.ccc.librekan.librekan.repository.ListaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ListaService {

    private final ListaRepository listaRepository;

    public ListaService(ListaRepository listaRepository) {
        this.listaRepository = listaRepository;
    }

    public Lista salvar(Lista lista) {
        return listaRepository.save(lista);
    }

    public Optional<Lista> buscarPorId(Long id) {
        return listaRepository.findById(id);
    }

    public void excluir(Long id) {
        if (listaRepository.findById(id).isEmpty()) {
            throw new RecursoNaoEncontradoException("Lista não encontrada com o ID: " + id);
        }
        listaRepository.deleteById(id);
    }     

    @Transactional
    public Lista atualizarNome(Long id, String novoNome) {
        Lista lista = listaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Lista não encontrada com o id: " + id));
        lista.setNome(novoNome);
        return listaRepository.save(lista);
    }

}
