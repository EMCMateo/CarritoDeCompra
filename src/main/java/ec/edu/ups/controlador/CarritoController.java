package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.CarritoAñadirView;
import ec.edu.ups.vista.ListarCarritoView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CarritoController {

    private final ProductoDAO productoDAO;
    private final CarritoAñadirView carritoAñadirView;
    private final ListarCarritoView listarCarritoView;
    private Carrito carrito;
    private final CarritoDAO carritoDAO;
    private boolean eventosRegistrados = false;
    private Usuario usuarioActual;
    private boolean visualizarCarrito = false;


    public CarritoController(CarritoDAO carritoDAO, CarritoAñadirView carritoAñadirView, ProductoDAO productoDAO, Carrito carrito, ListarCarritoView listarCarritoView, Usuario usuarioActual) {
        this.productoDAO = productoDAO;
        this.carritoAñadirView = carritoAñadirView;
        this.carritoDAO = carritoDAO;
        this.listarCarritoView = listarCarritoView;
        this.carrito = null; // ← IMPORTANTE: el carrito se inicializa solo cuando se guarda o se añade algo
        this.usuarioActual = usuarioActual;
    }

    public void carritoEventos() {
        if (eventosRegistrados) return;
        eventosRegistrados = true;

        carritoAñadirView.getBtnBuscar().addActionListener(e -> {
            if (visualizarCarrito) return; // ← DESACTIVADO EN MODO VISUALIZACIÓN

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
            if (visualizarCarrito) return; // ← DESACTIVADO EN MODO VISUALIZACIÓN
            añadirProductoAlCarrito();
        });

        carritoAñadirView.getBtnGuardar().addActionListener(e -> {
            if (visualizarCarrito) return; // ← DESACTIVADO EN MODO VISUALIZACIÓN
            guardarCarrito();
        });

        carritoAñadirView.getBtnBorrar().addActionListener(e -> {
            if (visualizarCarrito) return;
            carrito.vaciarCarrito();
            carritoAñadirView.cargarDatosTabla(carrito.obtenerItems());
            carritoAñadirView.getTxtSubtotal().setText("");
            carritoAñadirView.getTxtIVA().setText("");
            carritoAñadirView.getTxtTotal().setText("");
            carritoAñadirView.limpiarCampos();
        });

        carritoAñadirView.getTblCarrito().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 1 || visualizarCarrito) return;

                JTable tabla = carritoAñadirView.getTblCarrito();
                int fila = tabla.rowAtPoint(e.getPoint());

                if (fila >= 0) {
                    int codigoProducto = (int) tabla.getValueAt(fila, 0);
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

        listarCarritoView.getBtnListar().addActionListener(e -> listarCarrito());

        listarCarritoView.getBtnBuscar().addActionListener(e -> buscarCarrito());

        listarCarritoView.getTblCarrito().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 1) return;

                JTable tabla = listarCarritoView.getTblCarrito();
                int fila = tabla.rowAtPoint(e.getPoint());

                if (fila >= 0) {
                    int codigoCarrito = (int) tabla.getValueAt(fila, 1);
                    Object[] opciones = {"Editar", "Eliminar", "Visualizar", "Cancelar"};
                    int respuesta = JOptionPane.showOptionDialog(
                            listarCarritoView,
                            "¿Qué desea hacer con el carrito?",
                            "Opciones",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opciones,
                            opciones[3]
                    );

                    if (respuesta == 0 || respuesta == 2) {
                        visualizarCarrito = (respuesta == 2);
                        Carrito carritoExistente = carritoDAO.buscarPorCodigo(codigoCarrito);
                        if (carritoExistente != null) {
                            carrito = carritoExistente;

                            carritoAñadirView.setDatosCarrito(carrito);
                            carritoAñadirView.cargarDatosTabla(carrito.obtenerItems());
                            carritoAñadirView.getTxtSubtotal().setText(String.format("%.2f", carrito.calcularSubTotal()));
                            carritoAñadirView.getTxtIVA().setText(String.format("%.2f", carrito.calcularIVA()));
                            carritoAñadirView.getTxtTotal().setText(String.format("%.2f", carrito.calcularTotal()));

                            // Desactivar edición
                            carritoAñadirView.getTxtCodigoCarrito().setEditable(false);
                            carritoAñadirView.getTxtFecha().setEditable(false);
                            carritoAñadirView.getCmBoxCantidad().setEnabled(!visualizarCarrito);
                            carritoAñadirView.getTxtCodigo().setEditable(!visualizarCarrito);
                            carritoAñadirView.getBtnBuscar().setEnabled(!visualizarCarrito);
                            carritoAñadirView.getBtnGuardar().setEnabled(!visualizarCarrito);
                            carritoAñadirView.getBtnBorrar().setEnabled(!visualizarCarrito);
                            carritoAñadirView.getBtnAnadir().setEnabled(false);

                            if (!carritoAñadirView.isShowing()) {
                                listarCarritoView.getParent().add(carritoAñadirView);
                            }

                            carritoAñadirView.setVisible(true);
                            carritoAñadirView.toFront();
                        }
                    } else if (respuesta == 1) {
                        int confirmar = JOptionPane.showConfirmDialog(
                                listarCarritoView,
                                "¿Está seguro que desea eliminar este carrito?",
                                "Confirmación",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirmar == JOptionPane.YES_OPTION) {
                            eliminarCarrito(codigoCarrito);
                        }
                    }
                }
            }
        });
    }

    private void añadirProductoAlCarrito() {
        if (carrito == null) {
            carrito = new Carrito(); // ← Código se genera solo aquí
            carrito.setUsuario(usuarioActual);
            carritoAñadirView.setDatosCarrito(carrito); // Mostrar el código recién ahora
        }

        int codigo = Integer.parseInt(carritoAñadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad = Integer.parseInt(carritoAñadirView.getCmBoxCantidad().getSelectedItem().toString());

        if (producto != null) {
            carrito.agregarProducto(producto, cantidad);
            carritoAñadirView.cargarDatosTabla(carrito.obtenerItems());
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
            listarCarritoView.cargarDatos(List.of(carrito));
        } else {
            listarCarritoView.mostrarMensaje("Carrito no encontrado.");
            listarCarritoView.cargarDatos(List.of());
        }
    }

    private void guardarCarrito() {
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setUsuario(usuarioActual);
        }

        if (carrito.estaVacio()) {
            carritoAñadirView.mostrarMensaje("No hay productos en el carrito.");
            return;
        }

        Carrito existente = carritoDAO.buscarPorCodigo(carrito.getCodigo());
        if (existente != null) {
            carritoDAO.actualizar(carrito);
            carritoAñadirView.mostrarMensaje("Carrito actualizado correctamente.");
        } else {
            carritoDAO.crear(carrito);
            carritoAñadirView.mostrarMensaje("Carrito guardado correctamente.");
        }

        carritoAñadirView.setDatosCarrito(carrito);
        carrito = null;
        carritoAñadirView.cargarDatosTabla(List.of());
        carritoAñadirView.limpiarCampos();
        carritoAñadirView.getTxtSubtotal().setText("");
        carritoAñadirView.getTxtIVA().setText("");
        carritoAñadirView.getTxtTotal().setText("");
    }

    private void eliminarCarrito(int codigoCarrito) {
        carritoDAO.eliminar(codigoCarrito);
        listarCarrito();
        listarCarritoView.mostrarMensaje("Carrito eliminado correctamente.");
    }
}
