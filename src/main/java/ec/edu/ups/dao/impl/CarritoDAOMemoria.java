package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de {@link CarritoDAO} que almacena los carritos en memoria.
 * <p> Ideal para pruebas o ejecución sin persistencia en disco.
 */
public class CarritoDAOMemoria implements CarritoDAO {

    private final List<Carrito> carritos;
    private final UsuarioDAO usuarioDAO;
    private int secuencia = 1;

    /**
     * Constructor que inyecta el {@link UsuarioDAO} necesario para asociar usuarios a carritos.
     *
     * @param usuarioDAO DAO de usuarios usado para la referencia cruzada
     */
    public CarritoDAOMemoria(UsuarioDAO usuarioDAO) {
        this.carritos = new ArrayList<>();
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Crea un nuevo carrito y lo agrega a la lista, asignando un código incremental.
     *
     * @param carrito Carrito a crear
     */
    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(secuencia++);
        carritos.add(carrito);
    }

    /**
     * Busca un carrito por su código.
     *
     * @param codigo Código único del carrito
     * @return Carrito encontrado o null si no existe
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Actualiza un carrito ya existente en la lista.
     *
     * @param carrito Carrito con datos actualizados
     */
    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                return;
            }
        }
    }

    /**
     * Elimina un carrito de la lista por su código.
     *
     * @param codigo Código del carrito a eliminar
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    /**
     * Lista todos los carritos almacenados en memoria.
     *
     * @return Lista de carritos
     */
    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

    /**
     * Busca todos los carritos asociados a un usuario específico.
     *
     * @param usuario Usuario al que pertenecen los carritos
     * @return Lista de carritos del usuario
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        return carritos.stream()
                .filter(c -> c.getUsuario() != null && c.getUsuario().getCedula().equals(usuario.getCedula()))
                .collect(Collectors.toList());
    }
}
