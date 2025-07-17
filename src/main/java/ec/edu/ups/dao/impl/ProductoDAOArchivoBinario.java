package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import java.io.*;
import java.util.*;

public class ProductoDAOArchivoBinario implements ProductoDAO {
    private final String ruta;
    private final List<Producto> productos = new ArrayList<>();

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

    private void guardarProductos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + "/productos.bin"))) {
            oos.writeObject(productos);
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

    @Override
    public void eliminar(int codigo) {
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarProductos();
    }

    @Override
    public List<Producto> listarTodos() {
        cargarProductos();
        return new ArrayList<>(productos);
    }

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
