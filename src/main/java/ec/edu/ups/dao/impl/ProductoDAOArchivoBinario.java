package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import java.io.*;
import java.util.*;

/**
 * Implementación de {@link ProductoDAO} que utiliza archivos binarios
 * para almacenar los productos de forma persistente.
 */
public class ProductoDAOArchivoBinario implements ProductoDAO {

    /** Ruta base donde se almacenará el archivo productos.bin */
    private final String ruta;

    /** Lista de productos en memoria que se serializa/deserializa */
    private final List<Producto> productos = new ArrayList<>();

    /**
     * Crea una instancia del DAO binario con la ruta especificada.
     *
     * @param ruta Carpeta donde se ubicará el archivo productos.bin
     */
    public ProductoDAOArchivoBinario(String ruta) {
        this.ruta = ruta;
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        File archivo = new File(ruta + "/productos.bin");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cargarProductos();
    }

    /**
     * Carga los productos desde el archivo binario.
     */
    private void cargarProductos() {
        File archivo = new File(ruta + "/productos.bin");
        if (!archivo.exists() || archivo.length() == 0) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                productos.clear();
                productos.addAll((List<Producto>) obj);
            }
        } catch (EOFException eof) {
            // Archivo vacío, es normal al inicio
        } catch (Exception e) {
            System.out.println("Error al cargar productos binarios: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista actual de productos en archivo binario.
     */
    private void guardarProductos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + "/productos.bin"))) {
            oos.writeObject(productos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Crea un nuevo producto y lo guarda en el archivo.
     *
     * @param producto Producto a crear.
     */

    @Override
    public void crear(Producto producto) {
        productos.add(producto);
        guardarProductos();
    }
    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto a buscar.
     * @return Producto encontrado o null si no existe.
     */

    @Override
    public Producto buscarPorCodigo(int codigo) {
        cargarProductos();
        for (Producto p : productos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                guardarProductos();
                return;
            }
        }
    }
    /**
     * Elimina un producto por su código.
     *
     * @param codigo Código del producto a eliminar.
     */

    @Override
    public void eliminar(int codigo) {
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarProductos();
    }
    /**
     * Lista todos los productos almacenados.
     *
     * @return Lista de productos.
     */

    @Override
    public List<Producto> listarTodos() {
        cargarProductos();
        return new ArrayList<>(productos);
    }
    /**
     * Busca productos por nombre, ignorando mayúsculas/minúsculas.
     *
     * @param nombre Nombre o parte del nombre a buscar.
     * @return Lista de productos que coinciden con el nombre.
     */

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        cargarProductos();
        List<Producto> encontrados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getNombre() != null && p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }
}
