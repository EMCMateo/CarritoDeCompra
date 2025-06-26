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
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    private static final UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
    private static final ProductoDAO productoDAO = new ProductoDAOMemoria();
    private static final CarritoDAO carritoDAO = new CarritoDAOMemoria();

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                //Iniciar Sesion
                LoginView loginView = new LoginView();
                UserRegistroView userRegistroView = new UserRegistroView();

                // Configurar controlador
                UsuarioController usuarioController = new UsuarioController(
                        usuarioDAO,
                        loginView,
                        userRegistroView
                );


                loginView.setVisible(true);


            }
            });

    }
    public static void configurarAccesoPorRol(Usuario usuario, PrincipalView principalView) {
        if (usuario.getRol() == Rol.CLIENTE) {
            // Cliente: desactivar todas las opciones excepto Crear Carrito
            principalView.getMenuItemCrearProducto().setEnabled(false);
            principalView.getMenuItemBuscarProducto().setEnabled(false);
            principalView.getMenuItemEliminarProducto().setEnabled(false);
            principalView.getMenuItemActualizarProducto().setEnabled(false);
            principalView.getMenuItemListarCarrito().setEnabled(false);
        }
    }

    public static void iniciarApp(Usuario usuario){

        PrincipalView principalView = new PrincipalView();
        Main.configurarAccesoPorRol(usuario, principalView);

        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
        ProductoListaView productoListaView = new ProductoListaView();
        CarritoAñadirView carritoAnadirView = new CarritoAñadirView();
        ProductoEliminarView productoEliminarView = new ProductoEliminarView();
        ProductoActualizarView productoActualizarView = new ProductoActualizarView();
        ListarCarritoView listarCarritoView = new ListarCarritoView();
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
                carrito,
                listarCarritoView

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
                carritoController.carritoEventos();
                principalView.getjDesktopPane().add(carritoAnadirView);
                carritoAnadirView.setVisible(true);
            }
        });

        principalView.getMenuItemListarCarrito().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carritoController.carritoEventos();
                principalView.getjDesktopPane().add(listarCarritoView);
                listarCarritoView.setVisible(true);
            }
        });
        principalView.getMenuItemSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        principalView.getMenuItemCerrarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        principalView,
                        "¿Está seguro que desea cerrar sesión?",
                        "Cerrar Sesión",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    principalView.dispose();

                    LoginView loginView = new LoginView();
                    UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
                    UserRegistroView userRegistroView = new UserRegistroView();
                    UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView, userRegistroView);

                    loginView.setVisible(true);
                }
            }
        });
        principalView.setVisible(true);
    }

    }

