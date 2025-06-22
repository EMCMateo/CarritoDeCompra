package ec.edu.ups.dao;

import ec.edu.ups.modelo.Usuario;

import java.awt.*;
import java.util.List;

public interface UsuarioDAO {

    Usuario autenticar(String username, String password);

    void crear(Usuario usuario);

    Usuario buscarPorUsername(String username);

    void eliminar (String username);

    void actualizar(Usuario usuario);

    List<Usuario> listarTodos();

    List<Usuario> listarAdmin();

    List<Usuario> listarClientes();


}
