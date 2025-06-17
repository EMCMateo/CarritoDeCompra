package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private  ProductoAnadirView productoAnadirView;
    private  ProductoListaView productoListaView;
    private final ProductoDAO productoDAO;
    private  ProductoActualizarView productoActualizarView;
    private ProductoEliminarView productoEliminarView;
    private CarritoAñadirView carritoAñadirView;

    public ProductoController(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public void carritoEventos(){
        carritoAñadirView.getBtnBuscar().addActionListener(e -> {
            int codigo = Integer.parseInt(carritoAñadirView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                carritoAñadirView.getTxtNombre().setText(producto.getNombre());
                carritoAñadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                carritoAñadirView.mostrarMensaje("Producto no encontrado.");
                carritoAñadirView.limpiarCampos();
            }
        });

        carritoAñadirView.getBtnAnadir().addActionListener(e -> {
            añadirTablaCarrito();
        });
    }


    public void anadirEventos(){
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
    }

    public void listaEventos(){
        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });
    }



    public void eliminarEventos() {
        productoEliminarView.getBtnEliminar().addActionListener(e -> {

                int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
                Producto producto = productoDAO.buscarPorCodigo(codigo);


                if (producto == null) {
                    productoEliminarView.mostrarMensaje("Producto no encontrado.");
                    productoEliminarView.limpiarCampos();
                    return;
                }
                int respuesta = productoEliminarView.mostrarConfirmacion("¿Está seguro que desea eliminar el producto?");

                if (respuesta == JOptionPane.YES_OPTION) {
                    productoDAO.eliminar(codigo);
                    productoEliminarView.mostrarMensaje("Producto eliminado correctamente.");
                } else {
                    productoEliminarView.mostrarMensaje("Eliminación cancelada.");
                }

                productoEliminarView.limpiarCampos();

        });

        productoEliminarView.getBtnBuscar().addActionListener(e -> {
            int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoEliminarView.getTxtNombre().setText(producto.getNombre());
                productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                productoEliminarView.mostrarMensaje("Producto no encontrado.");
                productoEliminarView.limpiarCampos();
            }
        });
    }



    public void actualizarEventos () {


        productoActualizarView.getBtnBuscar().addActionListener(e -> {
            int codigo = Integer.parseInt(productoActualizarView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoActualizarView.getTxtNombre().setText(producto.getNombre());
                productoActualizarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                productoActualizarView.mostrarMensaje("Producto no encontrado.");
                productoActualizarView.limpiarCampos();
            }
        });

        productoActualizarView.getBtnActualizar().addActionListener(e -> {
            int codigo = Integer.parseInt(productoActualizarView.getTxtCodigo().getText());
            String nombre = productoActualizarView.getTxtNombre().getText();
            double precio = Double.parseDouble(productoActualizarView.getTxtPrecio().getText());
            Producto producto = new Producto(codigo, nombre, precio);
            productoDAO.actualizar(producto);
            productoActualizarView.mostrarMensaje("Producto actualizado.");
        });



    }

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

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
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

    private void añadirTablaCarrito(){
        int codigo = Integer.parseInt(carritoAñadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad = Integer.parseInt(carritoAñadirView.getCmBoxCantidad().getSelectedItem().toString());
        if (producto != null) {
            carritoAñadirView.cargarDatosTabla(producto, cantidad);
        }
    }
}