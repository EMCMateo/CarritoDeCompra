package ec.edu.ups.dao;

import ec.edu.ups.excepciones.PersistenciaException;
import ec.edu.ups.modelo.Usuario;

import java.awt.*;
import java.util.List;

public interface UsuarioDAO {

    Usuario autenticar(String username, String password);

    void crear(Usuario usuario) throws PersistenciaException;

    Usuario buscarPorUsername(String username);

    void eliminar(String username) throws PersistenciaException;

    void actualizar(Usuario usuario) throws PersistenciaException;

    List<Usuario> listarTodos();

    List<Usuario> listarAdmin();

    List<Usuario> listarClientes();


}
