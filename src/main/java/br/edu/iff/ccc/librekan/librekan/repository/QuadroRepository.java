package br.edu.iff.ccc.librekan.librekan.repository;
import br.edu.iff.ccc.librekan.librekan.model.Quadro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface QuadroRepository extends JpaRepository<Quadro, Long> {
        List<Quadro> findByDonoEmail(String donoEmail);
        Optional<Quadro> findByNomeIgnoreCaseAndIdNot(String novoNome, Long id);
        Optional<Quadro> findByNomeIgnoreCase(String nome);
}
