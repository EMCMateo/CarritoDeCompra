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

public class Main {

    // DAO únicos para compartir información entre vistas
    private static final UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
    private static final ProductoDAO productoDAO = new ProductoDAOMemoria();
    private static final CarritoDAO carritoDAO = new CarritoDAOMemoria();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Vistas iniciales
            LoginView loginView = new LoginView();
            UserRegistroView userRegistroView = new UserRegistroView();
            ListarUsuarioView listarUsuarioView = new ListarUsuarioView(usuarioDAO);

            UsuarioController usuarioController = new UsuarioController(
                    usuarioDAO,
                    loginView,
                    userRegistroView,
                    listarUsuarioView
            );

            loginView.setVisible(true);
        });
    }

    public static void iniciarApp(Usuario usuario) {
        // Ventana principal
        PrincipalView principalView = new PrincipalView();

        // Vistas
        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
        ProductoListaView productoListaView = new ProductoListaView();
        ProductoEliminarView productoEliminarView = new ProductoEliminarView();
        ProductoActualizarView productoActualizarView = new ProductoActualizarView();
        CarritoAñadirView carritoAnadirView = new CarritoAñadirView();
        ListarCarritoView listarCarritoView = new ListarCarritoView();
        ListarUsuarioView listarUsuarioView = new ListarUsuarioView(usuarioDAO);

        // Controladores
        ProductoController productoController = new ProductoController(
                productoDAO,
                productoAnadirView,
                productoListaView,
                carritoAnadirView,
                productoEliminarView,
                productoActualizarView
        );

        CarritoController carritoController = new CarritoController(
                carritoDAO,
                carritoAnadirView,
                productoDAO,
                new Carrito(),
                listarCarritoView,
                usuario
        );
        carritoController.carritoEventos();

        configurarAccesoPorRol(usuario, principalView);


        principalView.getMenuItemCrearProducto().addActionListener(e -> {
            productoController.setProductoAnadirView(productoAnadirView);
            productoController.anadirEventos();
            principalView.getjDesktopPane().add(productoAnadirView);
            productoAnadirView.setVisible(true);
        });

        principalView.getMenuItemBuscarProducto().addActionListener(e -> {
            productoController.setProductoListaView(productoListaView);
            productoController.listaEventos();
            principalView.getjDesktopPane().add(productoListaView);
            productoListaView.setVisible(true);
        });

        principalView.getMenuItemEliminarProducto().addActionListener(e -> {
            productoController.setProductoEliminarView(productoEliminarView);
            productoController.eliminarEventos();
            principalView.getjDesktopPane().add(productoEliminarView);
            productoEliminarView.setVisible(true);
        });

        principalView.getMenuItemActualizarProducto().addActionListener(e -> {
            productoController.setProductoActualizarView(productoActualizarView);
            productoController.actualizarEventos();
            principalView.getjDesktopPane().add(productoActualizarView);
            productoActualizarView.setVisible(true);
        });

        principalView.getMenuItemCrearCarrito().addActionListener(e -> {
            principalView.getjDesktopPane().add(carritoAnadirView);
            carritoAnadirView.setVisible(true);
        });

        principalView.getMenuItemListarCarrito().addActionListener(e -> {
            principalView.getjDesktopPane().add(listarCarritoView);
            listarCarritoView.setVisible(true);
        });

        principalView.getMenuItemListarUsuarios().addActionListener(e -> {
            principalView.getjDesktopPane().add(listarUsuarioView);
            listarUsuarioView.setVisible(true);
        });

        // Cerrar sesión
        principalView.getMenuItemCerrarSesion().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    principalView,
                    "¿Está seguro que desea cerrar sesión?",
                    "Cerrar Sesión",
                    JOptionPane.YES_NO_OPTION
            );


            if (confirm == JOptionPane.YES_OPTION) {
                principalView.dispose();

                // Volver al login con los mismos DAOs
                LoginView loginView = new LoginView();
                UserRegistroView userRegistroView = new UserRegistroView();
                new UsuarioController(usuarioDAO, loginView, userRegistroView, listarUsuarioView);

                loginView.setVisible(true);
            }
        });

        // Salir del sistema
        principalView.getMenuItemSalir().addActionListener(e -> System.exit(0));

        // Mostrar ventana principal
        principalView.setVisible(true);
    }

    public static void configurarAccesoPorRol(Usuario usuario, PrincipalView principalView) {
        if (usuario.getRol() == Rol.CLIENTE) {

            principalView.getMenuItemCrearProducto().setEnabled(false);
            principalView.getMenuItemBuscarProducto().setEnabled(false);
            principalView.getMenuItemEliminarProducto().setEnabled(false);
            principalView.getMenuItemActualizarProducto().setEnabled(false);
            principalView.getMenuItemListarCarrito().setEnabled(false);
            principalView.getMenuItemListarUsuarios().setEnabled(false);
        }
    }
}
