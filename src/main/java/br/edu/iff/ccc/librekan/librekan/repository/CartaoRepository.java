package br.edu.iff.ccc.librekan.librekan.repository;

import br.edu.iff.ccc.librekan.librekan.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    List<Cartao> findByListaId(Long listaId);
}
