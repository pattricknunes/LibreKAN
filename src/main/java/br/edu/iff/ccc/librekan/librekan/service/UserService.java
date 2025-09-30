package br.edu.iff.ccc.librekan.librekan.service;

import br.edu.iff.ccc.librekan.librekan.dto.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<UsuarioDTO> usuarios = new ArrayList<>();

    public void salvarUsuario(UsuarioDTO usuario) {
        usuarios.add(usuario);
        System.out.println("Usuário salvo: " + usuario.getEmail());
    }

    public List<UsuarioDTO> listarUsuarios() {
        return usuarios;
    }

    public UsuarioDTO buscarPorEmail(String email) {
        return usuarios.stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email))
            .findFirst()
            .orElse(null);
    }
}