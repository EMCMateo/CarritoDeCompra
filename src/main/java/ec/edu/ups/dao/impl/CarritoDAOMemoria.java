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
 * CarritoDAOMemoria
 * DAO de carritos basado en almacenamiento en memoria.
 * Ideal para pruebas o modo sin persistencia real.
 */
public class CarritoDAOMemoria implements CarritoDAO {

    private final List<Carrito> carritos;
    private final UsuarioDAO usuarioDAO; // Dependencia inyectada
    private int secuencia = 1;

    public CarritoDAOMemoria(UsuarioDAO usuarioDAO) {
        this.carritos = new ArrayList<>();
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(secuencia++);
        carritos.add(carrito);
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                return;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        return carritos.stream()
                .filter(c -> c.getUsuario() != null && c.getUsuario().getCedula().equals(usuario.getCedula()))
                .collect(Collectors.toList());
    }
}
