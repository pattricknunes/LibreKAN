package br.edu.iff.ccc.librekan.librekan.controller.api;

import br.edu.iff.ccc.librekan.librekan.dto.ListaDTO;
import br.edu.iff.ccc.librekan.librekan.dto.ListaUpdateDTO; // Importe o novo DTO
import br.edu.iff.ccc.librekan.librekan.model.Lista;
import br.edu.iff.ccc.librekan.librekan.model.Quadro;
import br.edu.iff.ccc.librekan.librekan.service.ListaService;
import br.edu.iff.ccc.librekan.librekan.service.QuadroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/listas")
public class ListaApiController {

    private final ListaService listaService;
    private final QuadroService quadroService;

    public ListaApiController(ListaService listaService, QuadroService quadroService) {
        this.listaService = listaService;
        this.quadroService = quadroService;
    }

    @PostMapping
    public ResponseEntity<?> criarLista(@Valid @RequestBody ListaUpdateDTO dto,
                                        BindingResult result,
                                        @RequestParam("quadroId") Long quadroId) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("O nome da lista é obrigatório.");
        }
        Quadro quadro = quadroService.buscarPorId(quadroId)
                .orElseThrow(() -> new RuntimeException("Quadro não encontrado"));

        Lista novaLista = new Lista();
        novaLista.setNome(dto.getNome()); 
        novaLista.setQuadro(quadro);
        Lista listaSalva = listaService.salvar(novaLista);

        ListaDTO responseDto = new ListaDTO(listaSalva.getId(), listaSalva.getNome());
        return ResponseEntity.created(URI.create("/api/v1/listas/" + responseDto.getId())).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaDTO> atualizarNomeLista(@PathVariable Long id, @Valid @RequestBody ListaUpdateDTO dto) {
        Lista listaAtualizada = listaService.atualizarNome(id, dto.getNome());
        ListaDTO responseDto = new ListaDTO(listaAtualizada.getId(), listaAtualizada.getNome());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLista(@PathVariable Long id) {
        listaService.excluir(id);
        return ResponseEntity.noContent().build();
    }


}