package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import java.io.*;
import java.util.*;

/**
 * Implementación de {@link ProductoDAO} que utiliza archivos de texto (formato CSV)
 * para almacenar los productos de forma persistente.
 * <p>
 * Cada producto se guarda en el archivo productos.txt con el formato:
 * {@code codigo,nombre,precio}
 */
public class ProductoDAOArchivoTexto implements ProductoDAO {

    /** Ruta base donde se almacenará el archivo productos.txt */
    private final String ruta;

    /** Lista en memoria que contiene todos los productos cargados o por guardar */
    private final List<Producto> productos = new ArrayList<>();

    /**
     * Crea una nueva instancia del DAO que almacena los productos en texto plano.
     *
     * @param ruta Carpeta donde se ubicará el archivo productos.txt
     * @throws IllegalArgumentException si la ruta es nula o vacía
     */
    public ProductoDAOArchivoTexto(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta no puede ser nula o vacía.");
        }
        this.ruta = ruta;
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        File archivo = new File(ruta + "/productos.txt");
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
     * Carga los productos existentes desde el archivo productos.txt.
     */
    private void cargarProductos() {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta + "/productos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 3) {
                    Producto p = new Producto();
                    p.setCodigo(Integer.parseInt(datos[0]));
                    p.setNombre(datos[1]);
                    p.setPrecio(Double.parseDouble(datos[2]));
                    productos.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Guarda todos los productos actuales en el archivo productos.txt.
     */
    private void guardarProductos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta + "/productos.txt"))) {
            for (Producto p : productos) {
                pw.println(String.format("%d,%s,%.2f",
                        p.getCodigo(),
                        p.getNombre(),
                        p.getPrecio()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** * Implementación del método crear de ProductoDAO.
     * Agrega un nuevo producto a la lista y lo guarda en el archivo.
     *
     * @param producto Producto a agregar
     */

    @Override
    public void crear(Producto producto) {
        productos.add(producto);
        guardarProductos();
    }

    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto a buscar
     * @return Producto encontrado o null si no existe
     */

    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto p : productos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }
    /**
     * Busca productos por su nombre, ignorando mayúsculas y minúsculas.
     *
     * @param nombre Nombre o parte del nombre del producto a buscar
     * @return Lista de productos que coinciden con el nombre
     */

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }
    /**
     * Actualiza un producto existente en la lista y lo guarda en el archivo.
     *
     * @param producto Producto con datos actualizados
     */

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
     * Elimina un producto de la lista según su código y actualiza el archivo.
     *
     * @param codigo Código del producto a eliminar
     */

    @Override
    public void eliminar(int codigo) {
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarProductos();
    }
    /**
     * Retorna una copia de la lista de todos los productos.
     *
     * @return Lista de productos
     */

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}
