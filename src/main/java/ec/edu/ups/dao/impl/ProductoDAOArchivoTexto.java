package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import java.io.*;
import java.util.*;

/**
 * ProductoDAOArchivoTexto
 * Implementa el guardado/lectura de productos en archivo de texto (CSV)
 * usando los métodos exactos de la interfaz ProductoDAO.
 * Formato: codigo,nombre,precio
 */
public class ProductoDAOArchivoTexto implements ProductoDAO {
    private final String ruta;
    private final List<Producto> productos = new ArrayList<>();

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

    @Override
    public void crear(Producto producto) {
        productos.add(producto);
        guardarProductos();
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto p : productos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

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

    @Override
    public void eliminar(int codigo) {
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarProductos();
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}
