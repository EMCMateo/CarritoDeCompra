package ec.edu.ups.vista;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                PrincipalView principalView = new PrincipalView();
                ProductoDAO productoDAO = new ProductoDAOMemoria();


                principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ProductoAnadirView productoAnadirView = new ProductoAnadirView();

                        ProductoController productoController = new ProductoController(productoDAO);
                        productoController.setProductoAnadirView(productoAnadirView);
                        productoController.anadirEventos();
                        principalView.getjDesktopPane().add(productoAnadirView);

                    }
                });

                principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ProductoListaView productoListaView = new ProductoListaView();
                        ProductoController productoController = new ProductoController(productoDAO);
                        productoController.setProductoListaView(productoListaView);
                        productoController.listaEventos();
                        principalView.getjDesktopPane().add(productoListaView);
                    }
                });

                principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                        ProductoController productoController = new ProductoController(productoDAO);
                        productoController.setProductoEliminarView(productoEliminarView);
                        productoController.eliminarEventos();
                        principalView.getjDesktopPane().add(productoEliminarView);

                    }
                });

                principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ProductoActualizarView productoActualizarView = new ProductoActualizarView();
                        ProductoController productoController = new ProductoController(productoDAO);
                        productoController.setProductoActualizarView(productoActualizarView);
                        productoController.actualizarEventos();
                        principalView.getjDesktopPane().add(productoActualizarView);

                    }
                });

            }
        });
    }
}
