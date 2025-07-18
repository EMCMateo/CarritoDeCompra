package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implementación de {@link CarritoDAO} que guarda los carritos y sus items en archivos de texto (formato CSV).
 * <p>Se usa para persistencia ligera basada en archivos planos.
 * <ul>
 *   <li>Archivo <b>carritos.txt</b>: contiene código, fecha de creación y cédula del usuario.</li>
 *   <li>Archivo <b>items_carrito.txt</b>: contiene código del carrito, código del producto y cantidad.</li>
 * </ul>
 */
public class CarritoDAOArchivoTexto implements CarritoDAO {

    private final String ruta;
    private final List<Carrito> carritos = new ArrayList<>();
    private final UsuarioDAO usuarioDAO;
    private final ProductoDAO productoDAO;

    /**
     * Constructor con inyección de dependencias y ruta del directorio de almacenamiento.
     *
     * @param ruta Ruta del directorio donde se guardan los archivos
     * @param usuarioDAO DAO de usuarios
     * @param productoDAO DAO de productos
     */
    public CarritoDAOArchivoTexto(String ruta, UsuarioDAO usuarioDAO, ProductoDAO productoDAO) {
        this.ruta = ruta;
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;

        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        cargarCarritos();
    }

    /**
     * Carga los carritos desde el archivo carritos.txt y luego sus items desde items_carrito.txt.
     */
    private void cargarCarritos() {
        carritos.clear();
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
                        Date fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(partes[1]);
                        GregorianCalendar calendar = new GregorianCalendar();
                        calendar.setTime(fecha);
                        c.setFechaCreacion(calendar);
                    } catch (Exception ex) {
                        c.setFechaCreacion(new GregorianCalendar());
                    }

                    String cedula = partes[2];
                    Usuario usuario = usuarioDAO.buscarPorUsername(cedula);

                    if (usuario != null) {
                        c.setUsuario(usuario);
                        carritos.add(c);
                    } else {
                        System.out.println("Usuario no encontrado con cédula: " + cedula);
                    }
                }
            }
            cargarItemsCarrito();
        } catch (IOException e) {
            System.out.println("Error al cargar carritos: " + e.getMessage());
        }
    }

    /**
     * Carga los items de cada carrito desde el archivo items_carrito.txt.
     */
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

                    Carrito carrito = carritos.stream()
                            .filter(c -> c.getCodigo() == carritoId)
                            .findFirst()
                            .orElse(null);

                    Producto productoCompleto = productoDAO.buscarPorCodigo(productoCodigo);

                    if (carrito != null && productoCompleto != null) {
                        carrito.getItems().add(new ItemCarrito(productoCompleto, cantidad));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar items de carritos: " + e.getMessage());
        }
    }

    /**
     * Guarda los carritos y sus items en los archivos correspondientes.
     */
    private void guardarCarritos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta + "/carritos.txt"))) {
            for (Carrito c : carritos) {
                String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(c.getFechaCreacion().getTime());
                pw.println(String.join(",",
                        String.valueOf(c.getCodigo()),
                        fecha,
                        c.getUsuario() != null ? c.getUsuario().getCedula() : ""
                ));
            }
        } catch (IOException e) {
            System.out.println("Error al guardar carritos: " + e.getMessage());
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta + "/items_carrito.txt"))) {
            for (Carrito c : carritos) {
                for (ItemCarrito item : c.obtenerItems()) {
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
