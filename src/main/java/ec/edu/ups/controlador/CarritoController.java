package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.CarritoAñadirView;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CarritoController {

    private final ProductoDAO productoDAO;
    private final CarritoAñadirView carritoAñadirView;
    private final Carrito carrito;
    private final CarritoDAO carritoDAO;

    public CarritoController(CarritoDAO carritoDAO, CarritoAñadirView carritoAñadirView, ProductoDAO productoDAO, Carrito carrito){

        this.productoDAO = productoDAO;
        this.carritoAñadirView = carritoAñadirView;
        this.carritoDAO = carritoDAO;
        this.carrito = new Carrito();
        carritoAñadirView.setDatosCarrito(carrito);
    }

    public void carritoEventos() {
        carritoAñadirView.getBtnBuscar().addActionListener(e -> {
            int codigo = Integer.parseInt(carritoAñadirView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                carritoAñadirView.getTxtNombre().setText(producto.getNombre());
                carritoAñadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                carritoAñadirView.getBtnAnadir().setEnabled(true);
            } else {
                carritoAñadirView.mostrarMensaje("Producto no encontrado.");
                carritoAñadirView.limpiarCampos();
                carritoAñadirView.getBtnAnadir().setEnabled(false);
            }
        });

        carritoAñadirView.getBtnAnadir().addActionListener(e -> {
            añadirProductoAlCarrito();
        });

        carritoAñadirView.getBtnGuardar().addActionListener(e -> {
            try {
                guardarCarrito();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });

        carritoAñadirView.getBtnBorrar().addActionListener(e -> {
            carrito.vaciarCarrito(); // Vacía lista de items
            carritoAñadirView.cargarDatosTabla(carrito.obtenerItems()); // Limpia la tabla
            carritoAñadirView.getTxtSubtotal().setText("");
            carritoAñadirView.getTxtIVA().setText("");
            carritoAñadirView.getTxtTotal().setText("");
            carritoAñadirView.limpiarCampos(); // Limpia campos de texto
        });


    }

    private void añadirProductoAlCarrito() {

            int codigo = Integer.parseInt(carritoAñadirView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            int cantidad = Integer.parseInt(carritoAñadirView.getCmBoxCantidad().getSelectedItem().toString());

            if (producto != null) {
                carrito.agregarProducto(producto, cantidad); // evita duplicados y suma cantidades
                carritoAñadirView.cargarDatosTabla(carrito.obtenerItems()); // actualiza tabla
                mostrarTotales();
                carritoAñadirView.limpiarCampos();
                carritoAñadirView.getBtnAnadir().setEnabled(false);
            }


    }

    private void mostrarTotales() {
        carritoAñadirView.getTxtSubtotal().setText(String.format("%.2f", carrito.calcularSubTotal()));
        carritoAñadirView.getTxtIVA().setText(String.format("%.2f", carrito.calcularIVA()));
        carritoAñadirView.getTxtTotal().setText(String.format("%.2f", carrito.calcularTotal()));
    }

    private void listarCarrito() {
        List<Carrito> carritos = carritoDAO.listarTodos();
        carritoAñadirView.cargarDatos();
    }


    private void guardarCarrito() throws ParseException {
        if (carrito.estaVacio()) {
            carritoAñadirView.mostrarMensaje("No hay productos en el carrito.");
            return;
        }

        int codigo = Integer.parseInt(carritoAñadirView.getTxtCodigoCarrito().getText());
        String textoFecha = carritoAñadirView.getTxtFecha().getText();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fechaDate = sdf.parse(textoFecha);

        GregorianCalendar fecha = new GregorianCalendar();
        fecha.setTime(fechaDate);

        carrito.setCodigo(codigo);
        carrito.setFechaCreacion(fecha);

        carritoDAO.crear(carrito);

        carritoAñadirView.mostrarMensaje("Carrito guardado correctamente");

        // Limpia para siguiente uso
        carrito.vaciarCarrito();
        carritoAñadirView.cargarDatosTabla(carrito.obtenerItems());
        carritoAñadirView.getTxtSubtotal().setText("");
        carritoAñadirView.getTxtIVA().setText("");
        carritoAñadirView.getTxtTotal().setText("");
    }

}
