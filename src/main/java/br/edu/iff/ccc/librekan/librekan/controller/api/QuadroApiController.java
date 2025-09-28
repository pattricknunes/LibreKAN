package br.edu.iff.ccc.librekan.librekan.controller.api;

import br.edu.iff.ccc.librekan.librekan.dto.AdminDTO;
import br.edu.iff.ccc.librekan.librekan.dto.QuadroDTO;
import br.edu.iff.ccc.librekan.librekan.dto.QuadroUpdateDTO;
import br.edu.iff.ccc.librekan.librekan.dto.UsuarioDTO;
import br.edu.iff.ccc.librekan.librekan.model.Quadro;
import br.edu.iff.ccc.librekan.librekan.repository.QuadroRepository;
import br.edu.iff.ccc.librekan.librekan.service.QuadroService;
import jakarta.servlet.http.HttpSession; 
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/quadros")
public class QuadroApiController {

    private final QuadroService quadroService;
    private final QuadroRepository quadroRepository;

    public QuadroApiController(QuadroService quadroService, QuadroRepository quadroRepository) {
        this.quadroService = quadroService;
        this.quadroRepository = quadroRepository;
    }

    @GetMapping
    public List<QuadroDTO> listarQuadros(HttpSession session) { 
        Object usuarioLogado = session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            return Collections.emptyList();
        }

        String email = "";
        if (usuarioLogado instanceof UsuarioDTO u) {
            email = u.getEmail();
        } else if (usuarioLogado instanceof AdminDTO a) {
            email = a.getEmail();
        }
        return quadroService.listarPorDonoEmail(email).stream()
                .map(quadro -> new QuadroDTO(quadro.getId(), quadro.getNome()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> criarQuadro(@Valid @RequestBody QuadroUpdateDTO dto, HttpSession session) { 
        String nome = dto.getNome();

        Object usuarioLogado = session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não está logado.");
        }
        
        String email = (usuarioLogado instanceof UsuarioDTO u) ? u.getEmail() : ((AdminDTO) usuarioLogado).getEmail();
        
        if (quadroRepository.findByNomeIgnoreCase(nome).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("O nome do quadro '" + nome + "' já está em uso.");
        }

        Quadro novoQuadro = new Quadro();
        novoQuadro.setNome(nome);
        novoQuadro.setDonoEmail(email);
        
        Quadro quadroSalvo = quadroService.salvar(novoQuadro);
        QuadroDTO quadroDto = new QuadroDTO(quadroSalvo.getId(), quadroSalvo.getNome());

        return ResponseEntity.created(URI.create("/api/v1/quadros/" + quadroDto.getId())).body(quadroDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuadroDTO> atualizarQuadro(@PathVariable Long id, @Valid @RequestBody QuadroUpdateDTO dto) {
        Quadro quadroAtualizado = quadroService.atualizarNome(id, dto.getNome());
        QuadroDTO quadroDto = new QuadroDTO(quadroAtualizado.getId(), quadroAtualizado.getNome());
        return ResponseEntity.ok(quadroDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirQuadro(@PathVariable Long id) {
        quadroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}