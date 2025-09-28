package br.edu.iff.ccc.librekan.librekan.service;

import br.edu.iff.ccc.librekan.librekan.model.Lista;
import br.edu.iff.ccc.librekan.librekan.repository.ListaRepository; // Importe o novo repository
import org.springframework.stereotype.Service;

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
        listaRepository.deleteById(id);
    }   

    public Lista atualizarNome(Long id, String novoNome) {
    Lista lista = listaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Lista não encontrada com o id: " + id));

    lista.setNome(novoNome);
    return listaRepository.save(lista);
    }

}
