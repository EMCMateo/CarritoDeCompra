package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.ProductoAnadirView;
import ec.edu.ups.vista.ProductoGestionView;
import ec.edu.ups.vista.ProductoListaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoDAO productoDAO;
    private final ProductoGestionView productoGestionView;


    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              ProductoGestionView productoGestionView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoGestionView = productoGestionView;
        configurarEventos();
    }


    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

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

        productoGestionView.getBtnBuscar().addActionListener(e -> {
            int codigo = Integer.parseInt(productoGestionView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoGestionView.getTxtNombre().setText(producto.getNombre());
                productoGestionView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                productoGestionView.mostrarMensaje("Producto no encontrado.");
                productoGestionView.limpiarCampos();
            }
        });

        productoGestionView.getBtnActualizar().addActionListener(e -> {
            int codigo = Integer.parseInt(productoGestionView.getTxtCodigo().getText());
            String nombre = productoGestionView.getTxtNombre().getText();
            double precio = Double.parseDouble(productoGestionView.getTxtPrecio().getText());
            Producto producto = new Producto(codigo, nombre, precio);
            productoDAO.actualizar(producto);
            productoGestionView.mostrarMensaje("Producto actualizado.");
        });

        productoGestionView.getBtnEliminar().addActionListener(e -> {
            int codigo = Integer.parseInt(productoGestionView.getTxtCodigo().getText());
            productoDAO.eliminar(codigo);
            productoGestionView.mostrarMensaje("Producto eliminado.");
            productoGestionView.limpiarCampos();
        });

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
}