package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para objetos Carrito.
 */
public interface CarritoDAO {

    /**
     * Guarda un nuevo carrito.
     * @param carrito el carrito a crear.
     */
    void crear(Carrito carrito);

    /**
     * Busca un carrito por su código único.
     * @param codigo el código del carrito.
     * @return el carrito encontrado, o null si no existe.
     */
    Carrito buscarPorCodigo(int codigo);

    /**
     * Actualiza un carrito existente.
     * @param carrito el carrito con los datos actualizados.
     */
    void actualizar(Carrito carrito);

    /**
     * Elimina un carrito por su código.
     * @param codigo el código del carrito a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Lista todos los carritos registrados.
     * @return una lista de todos los carritos.
     */
    List<Carrito> listarTodos();

    /**
     * Busca todos los carritos de un usuario específico.
     * @param usuario el usuario dueño de los carritos.
     * @return una lista de carritos asociados al usuario.
     */
    List<Carrito> buscarPorUsuario(Usuario usuario);
}
