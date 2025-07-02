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
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class Main {

    private static final UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
    private static final ProductoDAO productoDAO = new ProductoDAOMemoria();
    private static final CarritoDAO carritoDAO = new CarritoDAOMemoria();

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MensajeInternacionalizacionHandler mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
                LoginView loginView = new LoginView(mensajeHandler);
                UserRegistroView userRegistroView = new UserRegistroView(mensajeHandler);
                ListarUsuarioView listarUsuarioView = new ListarUsuarioView(usuarioDAO, mensajeHandler);

                new UsuarioController(
                        usuarioDAO,
                        loginView,
                        userRegistroView,
                        listarUsuarioView,
                        mensajeHandler
                );

                loginView.setVisible(true);
            }
        });
    }

    public static void iniciarApp(Usuario usuario, String len, String pais) {
        MensajeInternacionalizacionHandler mensajeHandler = new MensajeInternacionalizacionHandler(len, pais);

        // Ventana principal
        PrincipalView principalView = new PrincipalView(mensajeHandler);

        // Vistas
        ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensajeHandler);
        ProductoListaView productoListaView = new ProductoListaView(mensajeHandler);
        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensajeHandler);
        ProductoActualizarView productoActualizarView = new ProductoActualizarView(mensajeHandler);
        CarritoAñadirView carritoAnadirView = new CarritoAñadirView(mensajeHandler);
        ListarCarritoView listarCarritoView = new ListarCarritoView(mensajeHandler);
        ListarUsuarioView listarUsuarioView = new ListarUsuarioView(usuarioDAO, mensajeHandler);
        ListarCarritoUsuario listarCarritoUsuario = new ListarCarritoUsuario(mensajeHandler);
        

        // Controladores
        ProductoController productoController = new ProductoController(
                productoDAO,
                productoAnadirView,
                productoListaView,
                carritoAnadirView,
                productoEliminarView,
                productoActualizarView,
                mensajeHandler
        );

        CarritoController carritoController = new CarritoController(
                carritoDAO,
                carritoAnadirView,
                productoDAO,
                new Carrito(),
                listarCarritoView,
                usuario, 
                mensajeHandler,
                listarCarritoUsuario,
                usuarioDAO
        );
        carritoController.carritoEventos();

        configurarAccesoPorRol(usuario, principalView);

        principalView.getMenuItemES().addActionListener(e -> {
            mensajeHandler.setLenguaje("es", "EC");
            principalView.setTextos(mensajeHandler);
            productoAnadirView.setTextos(mensajeHandler);
            productoListaView.setTextos(mensajeHandler);
            productoEliminarView.setTextos(mensajeHandler);
            productoActualizarView.setTextos(mensajeHandler);
            carritoAnadirView.setTextos(mensajeHandler);
            listarCarritoView.setTextos(mensajeHandler);
            listarUsuarioView.setTextos(mensajeHandler);
        });

        principalView.getMenuItemEN().addActionListener(e -> {
            mensajeHandler.setLenguaje("en", "US");
            principalView.setTextos(mensajeHandler);
            productoAnadirView.setTextos(mensajeHandler);
            productoListaView.setTextos(mensajeHandler);
            productoEliminarView.setTextos(mensajeHandler);
            productoActualizarView.setTextos(mensajeHandler);
            carritoAnadirView.setTextos(mensajeHandler);
            listarCarritoView.setTextos(mensajeHandler);
            listarUsuarioView.setTextos(mensajeHandler);
        });

        principalView.getMenuItemIT().addActionListener(e -> {
            mensajeHandler.setLenguaje("it", "IT");
            principalView.setTextos(mensajeHandler);
            productoAnadirView.setTextos(mensajeHandler);
            productoListaView.setTextos(mensajeHandler);
            productoEliminarView.setTextos(mensajeHandler);
            productoActualizarView.setTextos(mensajeHandler);
            carritoAnadirView.setTextos(mensajeHandler);
            listarCarritoView.setTextos(mensajeHandler);
            listarUsuarioView.setTextos(mensajeHandler);
        });

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



        principalView.getMenuItemCerrarSesion().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    principalView,
                    "¿Está seguro que desea cerrar sesión?",
                    "Cerrar Sesión",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                principalView.dispose();

                LoginView loginView = new LoginView(mensajeHandler);
                UserRegistroView userRegistroView = new UserRegistroView(mensajeHandler);
                new UsuarioController(usuarioDAO, loginView, userRegistroView, listarUsuarioView, mensajeHandler);

                loginView.setVisible(true);
            }
        });

        principalView.getMenuItemSalir().addActionListener(e -> System.exit(0));
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
