package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import java.io.*;
import java.util.*;

/**
 * CarritoDAOArchivoTexto
 * Implementa el guardado/lectura de carritos en archivo de texto (CSV)
 * Formato del archivo carritos.txt:
 * codigo,fechaCreacion,usuarioCedula
 * Y para cada carrito, sus items en items_carrito.txt:
 * carritoId,productoCodigo,cantidad
 */
public class CarritoDAOArchivoTexto implements CarritoDAO {
    private final String ruta;
    private final List<Carrito> carritos = new ArrayList<>();

    public CarritoDAOArchivoTexto(String ruta) {
        this.ruta = ruta;
        // Solo verificamos que exista la carpeta
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        // Cargamos los carritos existentes
        cargarCarritos();
    }

    private void cargarCarritos() {
        carritos.clear(); // Limpiar antes de cargar para evitar duplicados
        File archivo = new File(ruta + "/carritos.txt");
        if (!archivo.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    Carrito c = new Carrito();
                    c.setCodigo(Integer.parseInt(partes[0]));
                    try {
                        c.setFechaCreacion(new java.util.GregorianCalendar());
                        c.getFechaCreacion().setTime(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(partes[1]));
                    } catch (Exception ex) {
                        c.setFechaCreacion(new java.util.GregorianCalendar());
                    }
                    String cedula = partes[2];
                    // Permitir carritos aunque la cédula sea inválida o vacía
                    Usuario usuario = new Usuario();
                    try {
                        usuario.setCedula(cedula);
                    } catch (Exception ex) {
                        usuario.setCedula("0000000000"); // Cédula dummy si es inválida
                    }
                    c.setUsuario(usuario);
                    carritos.add(c);
                }
            }
            cargarItemsCarrito();
        } catch (IOException e) {
            System.out.println("Error al cargar carritos: " + e.getMessage());
        }
    }

    private void cargarItemsCarrito() {
        File archivo = new File(ruta + "/items_carrito.txt");
        if (!archivo.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    int carritoId = Integer.parseInt(partes[0]);
                    int productoCodigo = Integer.parseInt(partes[1]);
                    int cantidad = Integer.parseInt(partes[2]);
                    Carrito carrito = carritos.stream().filter(c -> c.getCodigo() == carritoId).findFirst().orElse(null);
                    if (carrito != null) {
                        ItemCarrito item = new ItemCarrito();
                        Producto producto = new Producto();
                        producto.setCodigo(productoCodigo);
                        item.setProducto(producto);
                        item.setCantidad(cantidad);
                        carrito.getItems().add(item);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar items de carritos: " + e.getMessage());
        }
    }

    private void guardarCarritos() {
        // Guardar carritos
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta + "/carritos.txt"))) {
            for (Carrito c : carritos) {
                pw.println(String.join(",",
                    String.valueOf(c.getCodigo()),
                    c.getFechaCreacion().toString(),
                    c.getUsuario() != null ? c.getUsuario().getCedula() : ""
                ));
            }
        } catch (IOException e) {
            System.out.println("Error al guardar carritos: " + e.getMessage());
        }

        // Guardar items
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta + "/items_carrito.txt"))) {
            for (Carrito c : carritos) {
                for (ItemCarrito item : c.obtenerItems()) { // Cambiado: getItems() -> obtenerItems()
                    pw.println(String.join(",",
                        String.valueOf(c.getCodigo()),
                        String.valueOf(item.getProducto().getCodigo()),
                        String.valueOf(item.getCantidad())
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar items de carritos: " + e.getMessage());
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
