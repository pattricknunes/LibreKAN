package br.edu.iff.ccc.librekan.librekan.controller.api;

import br.edu.iff.ccc.librekan.librekan.dto.CartaoDTO;
import br.edu.iff.ccc.librekan.librekan.dto.CartaoUpdateDTO;
import br.edu.iff.ccc.librekan.librekan.model.Cartao;
import br.edu.iff.ccc.librekan.librekan.model.Lista;
import br.edu.iff.ccc.librekan.librekan.service.CartaoService;
import br.edu.iff.ccc.librekan.librekan.service.ListaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cartoes")
public class CartaoApiController {

    private final CartaoService cartaoService;
    private final ListaService listaService;

    public CartaoApiController(CartaoService cartaoService, ListaService listaService) {
        this.cartaoService = cartaoService;
        this.listaService = listaService;
    }

    @PostMapping
    public ResponseEntity<?> criarCartao(@Valid @ModelAttribute Cartao cartao,
                                         BindingResult result,
                                         @RequestParam("listaId") Long listaId) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("O título do cartão é obrigatório.");
        }

        Lista lista = listaService.buscarPorId(listaId)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada"));

        cartao.setLista(lista);
        Cartao cartaoSalvo = cartaoService.salvar(cartao);

        CartaoDTO dto = new CartaoDTO(cartaoSalvo.getId(), cartaoSalvo.getTitulo(), cartaoSalvo.getDescricao());
        return ResponseEntity.created(URI.create("/api/v1/cartoes/" + dto.getId())).body(dto);
    }

    @PatchMapping("/{cartaoId}/mover")
    public ResponseEntity<?> moverCartao(@PathVariable Long cartaoId, @RequestParam("novaListaId") Long novaListaId) {
        try {
            Cartao cartaoMovido = cartaoService.moverCartao(cartaoId, novaListaId);
            CartaoDTO dto = new CartaoDTO(cartaoMovido.getId(), cartaoMovido.getTitulo(), cartaoMovido.getDescricao());
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCartao(@PathVariable Long id) {
        try {
            cartaoService.excluir(id);
            return ResponseEntity.noContent().build(); 
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCartao(@PathVariable Long id, @Valid @RequestBody CartaoUpdateDTO dto, BindingResult result) {
    if (result.hasErrors()) {
        return ResponseEntity.badRequest().body("O título do cartão é obrigatório.");
    }
    try {
        Cartao cartaoAtualizado = cartaoService.atualizar(id, dto.getTitulo(), dto.getDescricao());
        CartaoDTO responseDto = new CartaoDTO(cartaoAtualizado.getId(), cartaoAtualizado.getTitulo(), cartaoAtualizado.getDescricao());
        return ResponseEntity.ok(responseDto);
    } catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
}

    @GetMapping("/{id}")
    public ResponseEntity<CartaoDTO> buscarCartaoPorId(@PathVariable Long id) {
        return cartaoService.buscarPorId(id)
                .map(cartao -> new CartaoDTO(cartao.getId(), cartao.getTitulo(), cartao.getDescricao()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CartaoDTO>> listarCartoesPorLista(@RequestParam("listaId") Long listaId) {
        List<Cartao> cartoes = cartaoService.listarPorListaId(listaId);
        List<CartaoDTO> dtos = cartoes.stream()
                .map(c -> new CartaoDTO(c.getId(), c.getTitulo(), c.getDescricao()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}