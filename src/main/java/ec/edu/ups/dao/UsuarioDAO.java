package ec.edu.ups.dao;

import ec.edu.ups.excepciones.PersistenciaException;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz que define las operaciones CRUD y de autenticación para usuarios.
 */
public interface UsuarioDAO {

    /**
     * Autentica un usuario con sus credenciales.
     * @param username el nombre de usuario.
     * @param password la contraseña.
     * @return el usuario autenticado o null si las credenciales no son válidas.
     */
    Usuario autenticar(String username, String password);

    /**
     * Crea un nuevo usuario.
     * @param usuario el usuario a crear.
     * @throws PersistenciaException si ocurre un error al persistir el usuario.
     */
    void crear(Usuario usuario) throws PersistenciaException;

    Usuario buscarPorUsername(String username);

    void eliminar(String username) throws PersistenciaException;

    void actualizar(Usuario usuario) throws PersistenciaException;

    List<Usuario> listarTodos();

    List<Usuario> listarAdmin();

    List<Usuario> listarClientes();
}
