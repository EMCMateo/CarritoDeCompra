package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import ec.edu.ups.dao.ProductoDAO;

import java.io.*;
import java.util.*;

/**
 * CarritoDAOArchivoBinario
 * Implementa el guardado/lectura de carritos en archivo binario.
 * Aunque el Usuario ya viene serializado dentro del objeto Carrito,
 * se mantiene la referencia a UsuarioDAO para consistencia arquitectónica.
 */
public class CarritoDAOArchivoBinario implements CarritoDAO {

    private final String ruta;
    private final UsuarioDAO usuarioDAO; // Dependencia inyectada
    private final List<Carrito> carritos = new ArrayList<>();
    private final ProductoDAO productoDAO;

    public CarritoDAOArchivoBinario(String ruta, UsuarioDAO usuarioDAO, ProductoDAO productoDAO) {
        if (ruta == null || ruta.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta no puede ser nula o vacía.");
        }

        this.ruta = ruta;
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;

        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        File archivo = new File(ruta + "/carritos.bin");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cargarCarritos();
    }

    private void cargarCarritos() {
        carritos.clear();
        File archivo = new File(ruta + "/carritos.bin");
        if (!archivo.exists() || archivo.length() == 0) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                carritos.addAll((List<Carrito>) obj);
            }
        } catch (EOFException eof) {
            // Archivo vacío al inicio: normal
        } catch (Exception e) {
            System.out.println("Error al cargar carritos binarios: " + e.getMessage());
        }
    }

    private void guardarCarritos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + "/carritos.bin"))) {
            oos.writeObject(carritos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(Carrito carrito) {
        carritos.add(carrito);
        guardarCarritos();
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito c : carritos) {
            if (c.getCodigo() == codigo) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                guardarCarritos();
                return;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarCarritos();
    }

    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> encontrados = new ArrayList<>();
        for (Carrito c : carritos) {
            if (c.getUsuario() != null && c.getUsuario().getCedula().equals(usuario.getCedula())) {
                encontrados.add(c);
            }
        }
        return encontrados;
    }
}
