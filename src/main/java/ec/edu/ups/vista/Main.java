package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.*;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class Main {

    private static final UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
    private static final ProductoDAO productoDAO = new ProductoDAOMemoria();
    private static final CarritoDAO carritoDAO = new CarritoDAOMemoria();
    private static final PreguntaDAO preguntaDAO = new PreguntaDAOMemoria();

    private static String lang = "es";
    private static String country = "EC";

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            MensajeInternacionalizacionHandler mensajeHandler = new MensajeInternacionalizacionHandler(lang, country);

            for (int i = 1; i <= 10; i++) {
                ((PreguntaDAOMemoria) preguntaDAO).agregarPregunta(new Pregunta(i, mensajeHandler.get("pregunta.seguridad." + i)));
            }

            LoginView loginView = new LoginView(mensajeHandler);
            UserRegistroView userRegistroView = new UserRegistroView(mensajeHandler);
            ListarUsuarioView listarUsuarioView = new ListarUsuarioView(usuarioDAO, mensajeHandler);

            new UsuarioController(usuarioDAO, loginView, userRegistroView, listarUsuarioView, mensajeHandler, preguntaDAO);

            loginView.setVisible(true);
            userRegistroView.setVisible(false);
        });
    }

    public static void iniciarApp(Usuario usuario, String len, String pais) {
        lang = len;
        country = pais;

        MensajeInternacionalizacionHandler mensajeHandler = new MensajeInternacionalizacionHandler(lang, country);

        // Vistas
        PrincipalView principalView = new PrincipalView(mensajeHandler);
        ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensajeHandler);
        ProductoListaView productoListaView = new ProductoListaView(mensajeHandler);
        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensajeHandler);
        ProductoActualizarView productoActualizarView = new ProductoActualizarView(mensajeHandler);
        CarritoAñadirView carritoAnadirView = new CarritoAñadirView(mensajeHandler);
        ListarCarritoView listarCarritoView = new ListarCarritoView(mensajeHandler);
        ListarCarritoUsuarioView listarCarritoUsuarioView = new ListarCarritoUsuarioView(mensajeHandler);
        ListarUsuarioView listarUsuarioView = new ListarUsuarioView(usuarioDAO, mensajeHandler);
        LoginView loginView = new LoginView(mensajeHandler);
        UserRegistroView registrarseView = new UserRegistroView(mensajeHandler);

        new ProductoController(productoDAO, productoAnadirView, productoListaView, carritoAnadirView, productoEliminarView, productoActualizarView, mensajeHandler).inicializarEventos();
        new CarritoController(carritoDAO, carritoAnadirView, productoDAO, new Carrito(), listarCarritoView, usuario, mensajeHandler, listarCarritoUsuarioView, usuarioDAO).carritoEventos();

        configurarAccesoPorRol(usuario, principalView);


        principalView.getMenuItemCerrarSesion().addActionListener(e -> {
            int confirmar = JOptionPane.showConfirmDialog(
                    principalView,
                    mensajeHandler.get("menu.cerrar.confirmacion"),
                    mensajeHandler.get("menu.cerrar.titulo"),
                    JOptionPane.YES_NO_OPTION
            );
            if (confirmar == JOptionPane.YES_OPTION) {
                principalView.dispose();
                main(new String[0]);
            }
        });

        principalView.getMenuItemSalir().addActionListener(e -> System.exit(0));

        principalView.getMenuItemCrearProducto().addActionListener(e -> abrirVentana(principalView, productoAnadirView));
        principalView.getMenuItemBuscarProducto().addActionListener(e -> abrirVentana(principalView, productoListaView));
        principalView.getMenuItemEliminarProducto().addActionListener(e -> abrirVentana(principalView, productoEliminarView));
        principalView.getMenuItemActualizarProducto().addActionListener(e -> abrirVentana(principalView, productoActualizarView));
        principalView.getMenuItemCrearCarrito().addActionListener(e -> abrirVentana(principalView, carritoAnadirView));
        principalView.getMenuItemListarCarrito().addActionListener(e -> abrirVentana(principalView, listarCarritoView));
        principalView.getMenutItemListarCarritoUsuario().addActionListener(e -> abrirVentana(principalView, listarCarritoUsuarioView));
        principalView.getMenuItemListarUsuarios().addActionListener(e -> abrirVentana(principalView, listarUsuarioView));

        // Eventos de idioma
        principalView.getMenuItemES().addActionListener(e -> {
            cambiarIdioma("es", "EC");
            actualizarTextosGlobales(principalView, mensajeHandler, productoAnadirView, productoListaView, productoEliminarView, productoActualizarView,
                    carritoAnadirView, listarCarritoView, listarCarritoUsuarioView, listarUsuarioView, loginView, registrarseView);
        });

        principalView.getMenuItemEN().addActionListener(e -> {
            cambiarIdioma("en", "US");
            actualizarTextosGlobales(principalView, mensajeHandler, productoAnadirView, productoListaView, productoEliminarView, productoActualizarView,
                    carritoAnadirView, listarCarritoView, listarCarritoUsuarioView, listarUsuarioView, loginView, registrarseView);
        });

        principalView.getMenuItemIT().addActionListener(e -> {
            cambiarIdioma("it", "IT");
            actualizarTextosGlobales(principalView, mensajeHandler, productoAnadirView, productoListaView, productoEliminarView, productoActualizarView,
                    carritoAnadirView, listarCarritoView, listarCarritoUsuarioView, listarUsuarioView, loginView, registrarseView);
        });

        principalView.setVisible(true);
    }

    private static void abrirVentana(PrincipalView principalView, JInternalFrame vista) {
        if (!vista.isVisible()) {
            principalView.getDesktop().add(vista);
            vista.setVisible(true);
        }
        vista.toFront();
    }

    private static void configurarAccesoPorRol(Usuario usuario, PrincipalView principalView) {
        if (usuario.getRol() == Rol.CLIENTE) {
            principalView.getMenuItemCrearProducto().setEnabled(false);
            principalView.getMenuItemBuscarProducto().setEnabled(false);
            principalView.getMenuItemEliminarProducto().setEnabled(false);
            principalView.getMenuItemActualizarProducto().setEnabled(false);
            principalView.getMenuItemListarCarrito().setEnabled(false);
            principalView.getMenuItemListarUsuarios().setEnabled(false);
        }
    }

    private static void cambiarIdioma(String idioma, String pais) {
        lang = idioma;
        country = pais;
    }

    private static void actualizarTextosGlobales(
            PrincipalView principalView,
            MensajeInternacionalizacionHandler mensajeHandler,
            ProductoAnadirView productoAnadirView,
            ProductoListaView productoListaView,
            ProductoEliminarView productoEliminarView,
            ProductoActualizarView productoActualizarView,
            CarritoAñadirView carritoAnadirView,
            ListarCarritoView listarCarritoView,
            ListarCarritoUsuarioView listarCarritoUsuarioView,
            ListarUsuarioView listarUsuarioView,
            LoginView loginView,
            UserRegistroView registrarseView
    ) {

        principalView.setTextos(mensajeHandler);
        productoAnadirView.setTextos(mensajeHandler);
        productoListaView.setTextos(mensajeHandler);
        productoEliminarView.setTextos(mensajeHandler);
        productoActualizarView.setTextos(mensajeHandler);
        carritoAnadirView.setTextos(mensajeHandler);
        listarCarritoView.setTextos(mensajeHandler);
        listarCarritoUsuarioView.setTextos(mensajeHandler);
        listarUsuarioView.setTextos();
        loginView.setTextos(mensajeHandler);
        registrarseView.setTextos(mensajeHandler);
    }
}
