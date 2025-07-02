package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarritoDAOMemoria implements CarritoDAO {
    private List<Carrito> carritos;
    private int secuencia = 1;
    public CarritoDAOMemoria( ){
        carritos = new ArrayList<Carrito>();
    }

    @Override
    public void crear(Carrito carrito) {
        carritos.add(carrito);
        carrito.setCodigo(secuencia++);
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
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<Carrito> listarTodos() {
        return carritos;
    }

    
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        return carritos.stream()
                .filter(c -> c.getUsuario() != null && c.getUsuario().equals(usuario))
                .toList();
    }
}



