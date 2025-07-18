package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.*;

/**
 * Implementación de {@link CarritoDAO} que guarda los carritos en un archivo binario usando serialización.
 * <p> Los objetos Carrito deben implementar Serializable.
 */
public class CarritoDAOArchivoBinario implements CarritoDAO {

    private final String ruta;
    private final UsuarioDAO usuarioDAO;
    private final List<Carrito> carritos = new ArrayList<>();
    private final ProductoDAO productoDAO;

    /**
     * Constructor con ruta de almacenamiento e inyección de DAOs necesarios.
     *
     * @param ruta Ruta del archivo binario
     * @param usuarioDAO DAO de usuario
     * @param productoDAO DAO de producto
     */
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

    /**
     * Carga los carritos desde el archivo binario.
     */
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
            // Archivo vacío, se ignora
        } catch (Exception e) {
            System.out.println("Error al cargar carritos binarios: " + e.getMessage());
        }
    }

    /**
     * Guarda todos los carritos en el archivo binario.
     */
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
