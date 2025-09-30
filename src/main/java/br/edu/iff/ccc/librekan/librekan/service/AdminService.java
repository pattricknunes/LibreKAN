package br.edu.iff.ccc.librekan.librekan.service;

import br.edu.iff.ccc.librekan.librekan.dto.AdminDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private final List<AdminDTO> admins = new ArrayList<>();

    public void salvarAdmin(AdminDTO admin) {
        admins.add(admin);
        System.out.println("Admin salvo: " + admin.getEmail());
    }

    public List<AdminDTO> listarAdmins() {
        return admins;
    }

    public AdminDTO buscarPorEmail(String email) {
        return admins.stream()
            .filter(a -> a.getEmail().equalsIgnoreCase(email))
            .findFirst()
            .orElse(null);
    }
}