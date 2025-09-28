package br.edu.iff.ccc.librekan.librekan.service;

import br.edu.iff.ccc.librekan.librekan.exceptions.RecursoNaoEncontradoException;
import br.edu.iff.ccc.librekan.librekan.model.Cartao;
import br.edu.iff.ccc.librekan.librekan.model.Lista;
import br.edu.iff.ccc.librekan.librekan.repository.CartaoRepository;
import br.edu.iff.ccc.librekan.librekan.repository.ListaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;
    private final ListaRepository listaRepository;

    public CartaoService(CartaoRepository cartaoRepository, ListaRepository listaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.listaRepository = listaRepository;
    }

    public Cartao salvar(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    @Transactional
    public Cartao moverCartao(Long cartaoId, Long novaListaId) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão não encontrado com o ID: " + cartaoId));

        Lista novaLista = listaRepository.findById(novaListaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Lista de destino não encontrada com o ID: " + novaListaId));

        cartao.setLista(novaLista);
        return cartaoRepository.save(cartao);
    }

    @Transactional
    public Cartao atualizar(Long id, String novoTitulo, String novaDescricao) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão não encontrado com o id: " + id));
        cartao.setTitulo(novoTitulo);
        cartao.setDescricao(novaDescricao);
        return cartaoRepository.save(cartao);
    }

    public void excluir(Long id) {
        if (!cartaoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Cartão não encontrado com o id: " + id);
        }
        cartaoRepository.deleteById(id);
    }

    public Optional<Cartao> buscarPorId(Long id) {
        return cartaoRepository.findById(id);
    }

    public List<Cartao> listarPorListaId(Long listaId) {
        return cartaoRepository.findByListaId(listaId);
    }
}