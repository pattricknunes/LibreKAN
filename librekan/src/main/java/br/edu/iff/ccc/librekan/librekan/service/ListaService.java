package br.edu.iff.ccc.librekan.librekan.service;

import br.edu.iff.ccc.librekan.librekan.model.Lista;
import br.edu.iff.ccc.librekan.librekan.repository.ListaRepository;
import br.edu.iff.ccc.librekan.librekan.exception.ListaNaoEncontradaException;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class ListaService {
    private final ListaRepository listaRepository;

    public ListaService(ListaRepository listaRepository) {
        this.listaRepository = listaRepository;
    }

    public List<Lista> listarTodos() {
        return listaRepository.findAll();
    }

    public Lista salvar(Lista lista) {
        return listaRepository.save(lista);
    }

    public void excluir(Long id) {
        listaRepository.deleteById(id);
    }

    public Optional<Lista> buscarPorId(Long id) {
        return Optional.ofNullable(
            listaRepository.findById(id)
                .orElseThrow(() -> new ListaNaoEncontradaException("Lista com id " + id + " não encontrada."))
        );
    }
}
