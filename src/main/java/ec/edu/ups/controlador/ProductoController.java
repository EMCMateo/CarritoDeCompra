package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private ProductoAnadirView productoAnadirView;
    private ProductoListaView productoListaView;
    private ProductoActualizarView productoActualizarView;
    private ProductoEliminarView productoEliminarView;
    private CarritoAñadirView carritoAñadirView;

    private final ProductoDAO productoDAO;
    private final MensajeInternacionalizacionHandler mensajeHandler;

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              CarritoAñadirView carritoAñadirView,
                              ProductoEliminarView productoEliminarView,
                              ProductoActualizarView productoActualizarView,
                              MensajeInternacionalizacionHandler mensajeHandler) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.carritoAñadirView = carritoAñadirView;
        this.productoEliminarView = productoEliminarView;
        this.productoActualizarView = productoActualizarView;
        this.mensajeHandler = mensajeHandler;
    }

    // Actualiza los textos de las vistas con el mensajeHandler para cambiar idioma
    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        if (productoAnadirView != null) productoAnadirView.setTextos(mensajeHandler);
        if (productoListaView != null) productoListaView.setTextos(mensajeHandler);
        if (productoActualizarView != null) productoActualizarView.setTextos(mensajeHandler);
        if (productoEliminarView != null) productoEliminarView.setTextos(mensajeHandler);
        if (carritoAñadirView != null) carritoAñadirView.setTextos(mensajeHandler);
    }

    // Eventos para agregar producto
    public void anadirEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());
    }

    // Eventos para listar y buscar productos
    public void listaEventos() {
        productoListaView.getBtnBuscar().addActionListener(e -> buscarProducto());
        productoListaView.getBtnListar().addActionListener(e -> listarProductos());
    }

    // Eventos para eliminar producto
    public void eliminarEventos() {
        productoEliminarView.getBtnEliminar().addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
                Producto producto = productoDAO.buscarPorCodigo(codigo);

                if (producto == null) {
                    productoEliminarView.mostrarMensaje(mensajeHandler.get("producto.no_encontrado"));
                    productoEliminarView.limpiarCampos();
                    return;
                }
                int respuesta = productoEliminarView.mostrarConfirmacion(mensajeHandler.get("producto.eliminar.confirmar"));

                if (respuesta == JOptionPane.YES_OPTION) {
                    productoDAO.eliminar(codigo);
                    productoEliminarView.mostrarMensaje(mensajeHandler.get("producto.eliminado"));
                } else {
                    productoEliminarView.mostrarMensaje(mensajeHandler.get("producto.eliminacion.cancelada"));
                }

                productoEliminarView.limpiarCampos();
            } catch (NumberFormatException ex) {
                productoEliminarView.mostrarMensaje(mensajeHandler.get("producto.codigo.invalido"));
            }
        });

        productoEliminarView.getBtnBuscar().addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    productoEliminarView.getTxtNombre().setText(producto.getNombre());
                    productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                } else {
                    productoEliminarView.mostrarMensaje(mensajeHandler.get("producto.no.encontrado"));
                    productoEliminarView.limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                productoEliminarView.mostrarMensaje(mensajeHandler.get("producto.codigo.invalido"));
            }
        });
    }

    // Eventos para actualizar producto
    public void actualizarEventos() {
        productoActualizarView.getBtnBuscar().addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(productoActualizarView.getTxtCodigo().getText());
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    productoActualizarView.getTxtNombre().setText(producto.getNombre());
                    productoActualizarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                } else {
                    productoActualizarView.mostrarMensaje(mensajeHandler.get("producto.no.encontrado"));
                    productoActualizarView.limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                productoActualizarView.mostrarMensaje(mensajeHandler.get("producto.codigo.invalido"));
            }
        });

        productoActualizarView.getBtnActualizar().addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(productoActualizarView.getTxtCodigo().getText());
                String nombre = productoActualizarView.getTxtNombre().getText();
                double precio = Double.parseDouble(productoActualizarView.getTxtPrecio().getText());
                Producto producto = new Producto(codigo, nombre, precio);
                productoDAO.actualizar(producto);
                productoActualizarView.limpiarCampos();
                productoActualizarView.mostrarMensaje(mensajeHandler.get("producto.actualizado.ok"));

            } catch (NumberFormatException ex) {
                productoActualizarView.mostrarMensaje(mensajeHandler.get("producto.datos.invalidos"));
            }
        });
    }

    private void guardarProducto() {
        try {
            int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
            String nombre = productoAnadirView.getTxtNombre().getText();
            double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());


            Producto productoExistente = productoDAO.buscarPorCodigo(codigo);
            if (productoExistente != null) {
                productoAnadirView.mostrarMensaje(mensajeHandler.get("producto.codigo.invalido")); // Puedes cambiar por otro mensaje si quieres más específico
                return;
            }

            productoDAO.crear(new Producto(codigo, nombre, precio));
            productoAnadirView.mostrarMensaje(mensajeHandler.get("producto.guardado"));
            productoAnadirView.limpiarCampos();
            productoAnadirView.mostrarProductos(productoDAO.listarTodos());

        } catch (NumberFormatException ex) {
            productoAnadirView.mostrarMensaje(mensajeHandler.get("producto.datos.invalidos"));
        }
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();

        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    // Getters y setters si los necesitas
    public ProductoAnadirView getProductoAnadirView() {
        return productoAnadirView;
    }

    public void setProductoAnadirView(ProductoAnadirView productoAnadirView) {
        this.productoAnadirView = productoAnadirView;
    }

    public ProductoListaView getProductoListaView() {
        return productoListaView;
    }

    public void setProductoListaView(ProductoListaView productoListaView) {
        this.productoListaView = productoListaView;
    }

    public ProductoActualizarView getProductoActualizarView() {
        return productoActualizarView;
    }

    public void setProductoActualizarView(ProductoActualizarView productoActualizarView) {
        this.productoActualizarView = productoActualizarView;
    }

    public ProductoEliminarView getProductoEliminarView() {
        return productoEliminarView;
    }

    public void setProductoEliminarView(ProductoEliminarView productoEliminarView) {
        this.productoEliminarView = productoEliminarView;
    }

    public CarritoAñadirView getCarritoAñadirView() {
        return carritoAñadirView;
    }

    public void setCarritoAñadirView(CarritoAñadirView carritoAñadirView) {
        this.carritoAñadirView = carritoAñadirView;
    }
}
