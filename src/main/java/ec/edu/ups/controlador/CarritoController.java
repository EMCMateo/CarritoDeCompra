
package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.CarritoView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CarritoController {
    /*

    private final Carrito carrito;
    private final CarritoView carritoView;
    private final ProductoDAO productoDAO;

    public CarritoController(Carrito carrito, CarritoView carritoView, ProductoDAO productoDAO) {
        this.carrito = carrito;
        this.carritoView = carritoView;
        this.productoDAO = productoDAO;

        configurarEventos();
        actualizarVista();
    }

    private void configurarEventos() {
        carritoView.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarItem();
            }
        });

        carritoView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarItemSeleccionado();
            }
        });

        carritoView.getBtnVaciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carrito.getListaItems().clear();
                actualizarVista();
            }
        });

        carritoView.getBtnFinalizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarCompra();
            }
        });
    }

    private void agregarItem() {
        try {
            int codigo = Integer.parseInt(carritoView.getTxtCodigo().getText());
            int cantidad = Integer.parseInt(carritoView.getTxtCantidad().getText());

            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto == null) {
                carritoView.mostrarMensaje("Producto no encontrado.");
                return;
            }

            carrito.agregarItem(new ItemCarrito(producto, cantidad));
            actualizarVista();
            carritoView.limpiarCampos();
        } catch (NumberFormatException e) {
            carritoView.mostrarMensaje("Código y cantidad deben ser números.");
        }
    }

    private void eliminarItemSeleccionado() {
        int filaSeleccionada = carritoView.getTablaCarrito().getSelectedRow();
        if (filaSeleccionada >= 0) {
            carrito.getListaItems().remove(filaSeleccionada);
            actualizarVista();
        } else {
            carritoView.mostrarMensaje("Seleccione un ítem para eliminar.");
        }
    }

    private void finalizarCompra() {
        if (carrito.getListaItems().isEmpty()) {
            carritoView.mostrarMensaje("El carrito está vacío.");
        } else {
            double total = carrito.calcularCosto();
            carritoView.mostrarMensaje("Compra finalizada. Total a pagar: $" + String.format("%.2f", total));
            carrito.getListaItems().clear();
            actualizarVista();
        }
    }

    private void actualizarVista() {
        carritoView.mostrarCarrito(carrito.getListaItems());
        carritoView.mostrarTotal(carrito.calcularCosto());
    }*/
}
