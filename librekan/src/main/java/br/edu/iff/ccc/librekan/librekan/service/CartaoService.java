package br.edu.iff.ccc.librekan.librekan.service;

import br.edu.iff.ccc.librekan.librekan.model.Cartao;
import br.edu.iff.ccc.librekan.librekan.repository.CartaoRepository;
import br.edu.iff.ccc.librekan.librekan.exception.CartaoNaoEncontradoException;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class CartaoService {
    private final CartaoRepository cartaoRepository;

    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public List<Cartao> listarTodos() {
        return cartaoRepository.findAll();
    }

    public Cartao salvar(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    public void excluir(Long id) {
        cartaoRepository.deleteById(id);
    }

    public Optional<Cartao> buscarPorId(Long id) {
        return Optional.ofNullable(
            cartaoRepository.findById(id)
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartão com id " + id + " não encontrado."))
        );
    }
}
