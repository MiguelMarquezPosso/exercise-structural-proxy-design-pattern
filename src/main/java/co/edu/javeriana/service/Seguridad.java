package co.edu.javeriana.service;

import co.edu.javeriana.model.Usuario;
import co.edu.javeriana.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Seguridad {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean Autorizacion(String user, String password) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsernameAndPassword(user, password);
            if (usuarioOpt.isPresent() && usuarioOpt.get().getActivo()) {
                System.out.println("Usuario " + user + " autorizado");
                return true;
            } else {
                System.out.println("Usuario " + user + " no autorizado");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error en autorización: " + e.getMessage());
            return false;
        }
    }
}