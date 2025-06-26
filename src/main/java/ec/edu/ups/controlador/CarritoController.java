package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.CarritoAñadirView;
import ec.edu.ups.vista.ListarCarritoView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CarritoController {

    private final ProductoDAO productoDAO;
    private final CarritoAñadirView carritoAñadirView;
    private final ListarCarritoView listarCarritoView;
    private Carrito carrito;
    private final CarritoDAO carritoDAO;
    private boolean eventosRegistrados = false;


    public CarritoController(CarritoDAO carritoDAO, CarritoAñadirView carritoAñadirView, ProductoDAO productoDAO, Carrito carrito, ListarCarritoView listarCarritoView){

        this.productoDAO = productoDAO;
        this.carritoAñadirView = carritoAñadirView;
        this.carritoDAO = carritoDAO;
        this.listarCarritoView = listarCarritoView;
        this.carrito = new Carrito();
        carritoAñadirView.setDatosCarrito(carrito);
    }

    public void carritoEventos() {
        if (eventosRegistrados) return;
        eventosRegistrados = true;
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

        carritoAñadirView.getTblCarrito().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 1) return;
                JTable tabla = carritoAñadirView.getTblCarrito();
                int fila = tabla.rowAtPoint(e.getPoint());

                if (fila >= 0) {
                    int codigoProducto = (int) tabla.getValueAt(fila, 0);

                    // Mostrar el diálogo
                    Object[] opciones = {"Editar", "Eliminar", "Cancelar"};
                    int respuesta = JOptionPane.showOptionDialog(
                            null,
                            "¿Qué desea hacer con el producto?",
                            "Opciones",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opciones,
                            opciones[2]
                    );

                    if (respuesta == JOptionPane.YES_OPTION) {
                        // Editar
                        int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la nueva cantidad:"));

                        if (nuevaCantidad > 0) {
                            for (ItemCarrito item : carrito.obtenerItems()) {
                                if (item.getProducto().getCodigo() == codigoProducto) {
                                    item.setCantidad(nuevaCantidad);
                                    break;
                                }
                            }
                            carritoAñadirView.cargarDatosTabla(carrito.obtenerItems());
                            mostrarTotales();
                        }

                    } else if (respuesta == JOptionPane.NO_OPTION) {
                        // Eliminar (con confirmación)
                        int confirmar = JOptionPane.showConfirmDialog(null,
                                "¿Está seguro que desea eliminar el producto?",
                                "Confirmar eliminación",
                                JOptionPane.YES_NO_OPTION);

                        if (confirmar == JOptionPane.YES_OPTION) {
                            carrito.eliminarProducto(codigoProducto);
                            carritoAñadirView.cargarDatosTabla(carrito.obtenerItems());
                            mostrarTotales();
                        }
                    }
                }
            }
        });

    listarCarritoView.getBtnListar().addActionListener(e -> {
        listarCarrito();
    });

    listarCarritoView.getBtnBuscar().addActionListener(e -> {
        buscarCarrito();
    });

        listarCarritoView.getTblCarrito().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 1) return;

                JTable tabla = listarCarritoView.getTblCarrito();
                int fila = tabla.rowAtPoint(e.getPoint());

                if (fila >= 0) {
                    int codigoCarrito = (int) tabla.getValueAt(fila, 0); // ✅ Este es el código del carrito

                    Object[] opciones = {"Editar", "Eliminar", "Cancelar"};
                    int respuesta = JOptionPane.showOptionDialog(
                            listarCarritoView, // ✅ Usamos la vista correcta
                            "¿Qué desea hacer con el carrito?",
                            "Opciones",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opciones,
                            opciones[2]
                    );

                    switch (respuesta) {
                        case 0: // Editar
                            Carrito carritoExistente = carritoDAO.buscarPorCodigo(codigoCarrito);
                            if (carritoExistente != null) {
                                carrito = carritoExistente;

                                carritoAñadirView.setDatosCarrito(carrito);
                                carritoAñadirView.cargarDatosTabla(carrito.obtenerItems());
                                carritoAñadirView.getTxtSubtotal().setText(String.format("%.2f", carrito.calcularSubTotal()));
                                carritoAñadirView.getTxtIVA().setText(String.format("%.2f", carrito.calcularIVA()));
                                carritoAñadirView.getTxtTotal().setText(String.format("%.2f", carrito.calcularTotal()));

                                carritoAñadirView.getTxtCodigoCarrito().setEditable(false);
                                carritoAñadirView.getTxtFecha().setEditable(false);

                                if (!carritoAñadirView.isShowing()) {
                                    listarCarritoView.getParent().add(carritoAñadirView);
                                }

                                carritoAñadirView.setVisible(true);
                                carritoAñadirView.toFront();
                            } else {
                                listarCarritoView.mostrarMensaje("No se encontró el carrito.");
                            }
                            break;

                        case 1: // Eliminar carrito
                            int confirmar = JOptionPane.showConfirmDialog(
                                    listarCarritoView,
                                    "¿Está seguro que desea eliminar este carrito?",
                                    "Confirmación",
                                    JOptionPane.YES_NO_OPTION
                            );
                            if (confirmar == JOptionPane.YES_OPTION) {
                                eliminarCarrito(codigoCarrito); // ✅ Método correcto
                            }
                            break;

                        default:
                            break;
                    }
                }
            }
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
            listarCarritoView.cargarDatos(carritos);
        }
        private void buscarCarrito() {
            int codigo = Integer.parseInt(listarCarritoView.getTxtCodigo().getText());

            Carrito carrito = carritoDAO.buscarPorCodigo(codigo);

            if (carrito != null) {
                listarCarritoView.cargarDatos(List.of(carrito)); // Java 9+, convierte a lista
            } else {
                listarCarritoView.mostrarMensaje("Carrito no encontrado.");
                listarCarritoView.cargarDatos(List.of());
            }
        }



        private void guardarCarrito() throws ParseException {
            if (carrito.estaVacio()) {
                carritoAñadirView.mostrarMensaje("No hay productos en el carrito.");
                return;
            }


            String textoFecha = carritoAñadirView.getTxtFecha().getText();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date fechaDate = sdf.parse(textoFecha);

            GregorianCalendar fecha = new GregorianCalendar();
            fecha.setTime(fechaDate);

            carrito.setFechaCreacion(fecha);

            // Verificamos si ya existe → actualizamos, si no → creamos
            Carrito existente = carritoDAO.buscarPorCodigo(carrito.getCodigo());
            if (existente != null) {
                carritoDAO.actualizar(carrito);
                carritoAñadirView.mostrarMensaje("Carrito actualizado correctamente.");
            } else {
                carritoDAO.crear(carrito);
                carritoAñadirView.mostrarMensaje("Carrito guardado correctamente.");
            }
            carritoAñadirView.setDatosCarrito(new Carrito()); // nuevo carrito vacío
            carritoAñadirView.cargarDatosTabla(new ArrayList<>());


            this.carrito = new Carrito();
            carritoAñadirView.setDatosCarrito(this.carrito);
            carritoAñadirView.cargarDatosTabla(this.carrito.obtenerItems());
            carritoAñadirView.getTxtSubtotal().setText("");
            carritoAñadirView.getTxtIVA().setText("");
            carritoAñadirView.getTxtTotal().setText("");
            carritoAñadirView.limpiarCampos();
        }



    private void eliminarCarrito(int codigoCarrito) {
        carritoDAO.eliminar(codigoCarrito);
        listarCarrito();
        listarCarritoView.mostrarMensaje("Carrito eliminado correctamente.");
    }







}
