package br.edu.iff.ccc.librekan.librekan.repository;

import br.edu.iff.ccc.librekan.librekan.model.Lista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaRepository extends JpaRepository<Lista, Long> {
    Lista findByNome(String nome);
}
