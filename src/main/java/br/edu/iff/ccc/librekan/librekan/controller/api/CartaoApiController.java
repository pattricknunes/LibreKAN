package br.edu.iff.ccc.librekan.librekan.controller.api;

import br.edu.iff.ccc.librekan.librekan.dto.CartaoDTO;
import br.edu.iff.ccc.librekan.librekan.dto.CartaoUpdateDTO;
import br.edu.iff.ccc.librekan.librekan.exceptions.RecursoNaoEncontradoException;
import br.edu.iff.ccc.librekan.librekan.exceptions.RegraDeNegocioException;
import br.edu.iff.ccc.librekan.librekan.model.Cartao;
import br.edu.iff.ccc.librekan.librekan.model.Lista;
import br.edu.iff.ccc.librekan.librekan.service.CartaoService;
import br.edu.iff.ccc.librekan.librekan.service.ListaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cartoes")
@Tag(name = "Cartões", description = "Endpoints para gerenciamento de cartões")
public class CartaoApiController {

    private final CartaoService cartaoService;
    private final ListaService listaService;

    public CartaoApiController(CartaoService cartaoService, ListaService listaService) {
        this.cartaoService = cartaoService;
        this.listaService = listaService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo cartão em uma lista específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Lista pai não encontrada")
    })
    public ResponseEntity<?> criarCartao(@Valid @RequestBody CartaoUpdateDTO dto,
            BindingResult result,
            @RequestParam("listaId") Long listaId) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("O título do cartão é obrigatório.");
        }

        Lista lista = listaService.buscarPorId(listaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Lista não encontrada"));

        Cartao novoCartao = new Cartao();
        novoCartao.setTitulo(dto.getTitulo());
        novoCartao.setDescricao(dto.getDescricao());
        novoCartao.setLista(lista);

        Cartao cartaoSalvo = cartaoService.salvar(novoCartao);

        CartaoDTO responseDto = new CartaoDTO(cartaoSalvo.getId(), cartaoSalvo.getTitulo(), cartaoSalvo.getDescricao());
        return ResponseEntity.created(URI.create("/api/v1/cartoes/" + responseDto.getId())).body(responseDto);
    }

    @PatchMapping("/{cartaoId}/mover")
    @Operation(summary = "Move um cartão para uma nova lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartão movido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cartão ou lista de destino não encontrados")
    })
    public ResponseEntity<CartaoDTO> moverCartao(@PathVariable Long cartaoId,
            @RequestParam("novaListaId") Long novaListaId) {
        Cartao cartaoMovido = cartaoService.moverCartao(cartaoId, novaListaId);
        CartaoDTO dto = new CartaoDTO(cartaoMovido.getId(), cartaoMovido.getTitulo(), cartaoMovido.getDescricao());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um cartão existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cartão excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cartão não encontrado")
    })
    public ResponseEntity<Void> deletarCartao(@PathVariable Long id) {
        cartaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza o título e a descrição de um cartão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartão atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Cartão não encontrado")
    })
    public ResponseEntity<CartaoDTO> atualizarCartao(@PathVariable Long id, @Valid @RequestBody CartaoUpdateDTO dto,
            BindingResult result) {
        if (result.hasErrors()) {
            throw new RegraDeNegocioException("O título do cartão é obrigatório.");
        }
        Cartao cartaoAtualizado = cartaoService.atualizar(id, dto.getTitulo(), dto.getDescricao());
        CartaoDTO responseDto = new CartaoDTO(cartaoAtualizado.getId(), cartaoAtualizado.getTitulo(),
                cartaoAtualizado.getDescricao());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um cartão específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartão encontrado"),
            @ApiResponse(responseCode = "404", description = "Cartão não encontrado")
    })
    public ResponseEntity<CartaoDTO> buscarCartaoPorId(@PathVariable Long id) {
        return cartaoService.buscarPorId(id)
                .map(cartao -> new CartaoDTO(cartao.getId(), cartao.getTitulo(), cartao.getDescricao()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Lista todos os cartões de uma lista específica")
    @ApiResponse(responseCode = "200", description = "Cartões listados com sucesso")
    public ResponseEntity<List<CartaoDTO>> listarCartoesPorLista(@RequestParam("listaId") Long listaId) {
        List<Cartao> cartoes = cartaoService.listarPorListaId(listaId);
        List<CartaoDTO> dtos = cartoes.stream()
                .map(c -> new CartaoDTO(c.getId(), c.getTitulo(), c.getDescricao()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}