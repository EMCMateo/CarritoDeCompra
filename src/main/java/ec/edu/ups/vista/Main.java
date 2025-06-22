package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                //Iniciar Sesion
                UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
                LoginView loginView = new LoginView();
                UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

                loginView.setVisible(true);
            }
            });
    }
    public static void iniciarApp(){
        PrincipalView principalView = new PrincipalView();
        ProductoDAO productoDAO = new ProductoDAOMemoria();
        CarritoDAO carritoDAO = new CarritoDAOMemoria();

        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
        ProductoListaView productoListaView = new ProductoListaView();
        CarritoAñadirView carritoAnadirView = new CarritoAñadirView();
        ProductoEliminarView productoEliminarView = new ProductoEliminarView();
        ProductoActualizarView productoActualizarView = new ProductoActualizarView();

        // Instanciamos Controladores
        ProductoController productoController = new ProductoController(
                productoDAO,
                productoAnadirView,
                productoListaView,
                carritoAnadirView,
                productoEliminarView,
                productoActualizarView
        );

        // Crear el carrito
        Carrito carrito = new Carrito();

        // Instanciar el controlador del carrito correctamente
        CarritoController carritoController = new CarritoController(
                carritoDAO,
                carritoAnadirView,
                productoDAO,
                carrito
        );

        principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoController.setProductoAnadirView(productoAnadirView);
                productoController.anadirEventos();
                principalView.getjDesktopPane().add(productoAnadirView);
                productoAnadirView.setVisible(true);
            }
        });

        principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoController.setProductoListaView(productoListaView);
                productoController.listaEventos();
                principalView.getjDesktopPane().add(productoListaView);
                productoListaView.setVisible(true);
            }
        });

        principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoController.setProductoEliminarView(productoEliminarView);
                productoController.eliminarEventos();
                principalView.getjDesktopPane().add(productoEliminarView);
                productoEliminarView.setVisible(true);
            }
        });

        principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoController.setProductoActualizarView(productoActualizarView);
                productoController.actualizarEventos();
                principalView.getjDesktopPane().add(productoActualizarView);
                productoActualizarView.setVisible(true);
            }
        });

        principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carritoController.carritoEventos(); // Corregido el nombre
                principalView.getjDesktopPane().add(carritoAnadirView);
                carritoAnadirView.setVisible(true);
            }
        });

        principalView.setVisible(true);
    }

    }

