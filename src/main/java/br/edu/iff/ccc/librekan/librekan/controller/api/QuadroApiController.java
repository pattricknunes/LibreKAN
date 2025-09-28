package br.edu.iff.ccc.librekan.librekan.controller.api;

import br.edu.iff.ccc.librekan.librekan.dto.AdminDTO;
import br.edu.iff.ccc.librekan.librekan.dto.QuadroDTO;
import br.edu.iff.ccc.librekan.librekan.dto.QuadroUpdateDTO;
import br.edu.iff.ccc.librekan.librekan.dto.UsuarioDTO;
import br.edu.iff.ccc.librekan.librekan.model.Quadro;
import br.edu.iff.ccc.librekan.librekan.repository.QuadroRepository;
import br.edu.iff.ccc.librekan.librekan.service.QuadroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Quadros", description = "Endpoints para gerenciamento de quadros")
public class QuadroApiController {

    private final QuadroService quadroService;
    private final QuadroRepository quadroRepository;

    public QuadroApiController(QuadroService quadroService, QuadroRepository quadroRepository) {
        this.quadroService = quadroService;
        this.quadroRepository = quadroRepository;
    }

    @GetMapping
    @Operation(summary = "Lista todos os quadros do usuário logado") // <-- DESCRIÇÃO DA OPERAÇÃO
    @ApiResponse(responseCode = "200", description = "Lista de quadros retornada com sucesso")
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
    @Operation(summary = "Cria um novo quadro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quadro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos (ex: nome vazio)"),
            @ApiResponse(responseCode = "409", description = "Já existe um quadro com este nome")
    })
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
    @Operation(summary = "Atualiza o nome de um quadro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quadro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Quadro não encontrado"),
            @ApiResponse(responseCode = "409", description = "O novo nome já está em uso por outro quadro")
    })
    public ResponseEntity<QuadroDTO> atualizarQuadro(@PathVariable Long id, @Valid @RequestBody QuadroUpdateDTO dto) {
        Quadro quadroAtualizado = quadroService.atualizarNome(id, dto.getNome());
        QuadroDTO quadroDto = new QuadroDTO(quadroAtualizado.getId(), quadroAtualizado.getNome());
        return ResponseEntity.ok(quadroDto);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um quadro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Quadro excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Quadro não encontrado")
    })
    public ResponseEntity<Void> excluirQuadro(@PathVariable Long id) {
        quadroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}