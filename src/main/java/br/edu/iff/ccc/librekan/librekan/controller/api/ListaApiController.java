package br.edu.iff.ccc.librekan.librekan.controller.api;

import br.edu.iff.ccc.librekan.librekan.dto.ListaDTO;
import br.edu.iff.ccc.librekan.librekan.model.Lista;
import br.edu.iff.ccc.librekan.librekan.model.Quadro;
import br.edu.iff.ccc.librekan.librekan.service.ListaService;
import br.edu.iff.ccc.librekan.librekan.service.QuadroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> criarLista(@Valid @ModelAttribute Lista lista,
                                        BindingResult result,
                                        @RequestParam("quadroId") Long quadroId) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("O nome da lista é obrigatório.");
        }

        Quadro quadro = quadroService.buscarPorId(quadroId)
                .orElseThrow(() -> new RuntimeException("Quadro não encontrado"));

        lista.setQuadro(quadro);
        Lista listaSalva = listaService.salvar(lista);

        ListaDTO dto = new ListaDTO(listaSalva.getId(), listaSalva.getNome());
        return ResponseEntity.created(URI.create("/api/v1/listas/" + dto.getId())).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarNomeLista(@PathVariable Long id, @RequestParam("nome") String novoNome) {
        if (novoNome == null || novoNome.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("O nome não pode ser vazio.");
        }
        try {
            Lista listaAtualizada = listaService.atualizarNome(id, novoNome);
            ListaDTO dto = new ListaDTO(listaAtualizada.getId(), listaAtualizada.getNome());
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deletarLista(@PathVariable Long id) {
        try {
            listaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}