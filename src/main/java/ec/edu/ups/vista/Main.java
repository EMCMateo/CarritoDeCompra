package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.*;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

public class Main {

    private static final UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
    private static final ProductoDAO productoDAO = new ProductoDAOMemoria();
    private static final CarritoDAO carritoDAO = new CarritoDAOMemoria();
    private static final PreguntaDAO preguntaDAO = new PreguntaDAOMemoria();

    private static String lang = "es";
    private static String country = "EC";
    private static MensajeInternacionalizacionHandler mensajeHandler;

    private static boolean preguntasCargadas = false;

    private static LoginView loginView;
    private static UserRegistroView userRegistroView;
    private static ListarUsuarioView listarUsuarioView;
    private static UsuarioController usuarioController;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            mensajeHandler = new MensajeInternacionalizacionHandler(lang, country);

            if (!preguntasCargadas) {
                for (int i = 1; i <= 10; i++) {
                    ((PreguntaDAOMemoria) preguntaDAO).agregarPregunta(
                            new Pregunta(i, mensajeHandler.get("pregunta.seguridad." + i))
                    );
                }
                preguntasCargadas = true;
            }

            loginView = new LoginView(mensajeHandler);
            userRegistroView = new UserRegistroView(mensajeHandler);
            listarUsuarioView = new ListarUsuarioView(usuarioDAO, mensajeHandler);

            usuarioController = new UsuarioController(usuarioDAO, loginView, userRegistroView, listarUsuarioView, mensajeHandler, preguntaDAO);

            loginView.setVisible(true);
            userRegistroView.setVisible(false);
        });
    }

    public static void iniciarApp(Usuario usuario, String idioma, String pais) {
        lang = idioma;
        country = pais;
        mensajeHandler.setLenguaje(lang, country);

        if (loginView != null) {
            loginView.dispose();
            loginView = null;
        }
        if (userRegistroView != null) {
            userRegistroView.dispose();
            userRegistroView = null;
        }

        PrincipalView principalView = new PrincipalView(mensajeHandler);
        MiJDesktopPane escritorio = principalView.getDesktop();

        // Vistas
        ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensajeHandler);
        ProductoListaView productoListaView = new ProductoListaView(mensajeHandler, productoDAO);
        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensajeHandler);
        ProductoActualizarView productoActualizarView = new ProductoActualizarView(mensajeHandler);
        CarritoAñadirView carritoAnadirView = new CarritoAñadirView(mensajeHandler);
        ListarCarritoView listarCarritoView = new ListarCarritoView(mensajeHandler);
        ListarCarritoUsuarioView listarCarritoUsuarioView = new ListarCarritoUsuarioView(mensajeHandler);
        listarUsuarioView = new ListarUsuarioView(usuarioDAO, mensajeHandler);
        UsuarioView usuarioView = new UsuarioView(mensajeHandler);
        CarritoEditarView carritoEditarView = new CarritoEditarView(mensajeHandler);

        // Controladores
        new ProductoController(productoDAO, productoAnadirView, productoListaView,
                carritoAnadirView, productoEliminarView, productoActualizarView, mensajeHandler)
                .inicializarEventos();

        CarritoController carritoController = new CarritoController(
                carritoDAO,
                carritoAnadirView,
                productoDAO,
                new Carrito(),
                listarCarritoView,
                usuario,
                mensajeHandler,
                listarCarritoUsuarioView,
                usuarioDAO,
                carritoEditarView,
                escritorio
        );

        carritoController.carritoEventos();

        configurarAccesoPorRol(usuario, principalView);

        // Idioma
        principalView.getMenuItemES().addActionListener(e -> {
            cambiarIdioma("es", "EC");
            actualizarTextos(principalView, productoAnadirView, productoListaView, productoEliminarView, productoActualizarView,
                    carritoAnadirView, listarCarritoView, listarCarritoUsuarioView, listarUsuarioView, null, null, usuarioView, carritoEditarView);
        });

        principalView.getMenuItemEN().addActionListener(e -> {
            cambiarIdioma("en", "US");
            actualizarTextos(principalView, productoAnadirView, productoListaView, productoEliminarView, productoActualizarView,
                    carritoAnadirView, listarCarritoView, listarCarritoUsuarioView, listarUsuarioView, null, null, usuarioView, carritoEditarView);
        });

        principalView.getMenuItemIT().addActionListener(e -> {
            cambiarIdioma("it", "IT");
            actualizarTextos(principalView, productoAnadirView, productoListaView, productoEliminarView, productoActualizarView,
                    carritoAnadirView, listarCarritoView, listarCarritoUsuarioView, listarUsuarioView, null, null, usuarioView, carritoEditarView);
        });

        // Menús funcionales
        principalView.getMenuItemCrearProducto().addActionListener(e ->
                abrirVentana(escritorio, productoAnadirView)


        );


        principalView.getMenuItemBuscarProducto().addActionListener(e -> abrirVentana(escritorio, productoListaView));
        principalView.getMenuItemEliminarProducto().addActionListener(e -> abrirVentana(escritorio, productoEliminarView));
        principalView.getMenuItemActualizarProducto().addActionListener(e -> abrirVentana(escritorio, productoActualizarView));
        principalView.getMenuItemCrearCarrito().addActionListener(e -> abrirVentana(escritorio, carritoAnadirView));
        principalView.getMenuItemListarCarrito().addActionListener(e -> abrirVentana(escritorio, listarCarritoView));
        principalView.getMenutItemListarCarritoUsuario().addActionListener(e -> abrirVentana(escritorio, listarCarritoUsuarioView));
        principalView.getMenuItemListarUsuarios().addActionListener(e -> abrirVentana(escritorio, listarUsuarioView));
        principalView.getMenuItemYo().addActionListener(e -> {

            usuarioController.setUsuarioView(usuarioView);
            usuarioController.cargarDatosUsuarioEnVista();
            abrirVentana(escritorio, usuarioView);

        });

        principalView.getMenuItemEditarCarrito().addActionListener(e -> {
            carritoController.mostrarVentanaEditarDesdeMenu();
            abrirVentana(escritorio, carritoEditarView);
        });

        // Cerrar sesión
        principalView.getMenuItemCerrarSesion().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(principalView,
                    mensajeHandler.get("menu.cerrar.confirmacion"),
                    mensajeHandler.get("menu.cerrar.titulo"),
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                principalView.dispose();
                loginView = new LoginView(mensajeHandler);
                userRegistroView = new UserRegistroView(mensajeHandler);
                userRegistroView.setVisible(false);
                listarUsuarioView = new ListarUsuarioView(usuarioDAO, mensajeHandler);
                usuarioController = new UsuarioController(usuarioDAO, loginView, userRegistroView, listarUsuarioView, mensajeHandler, preguntaDAO);
                loginView.setVisible(true);
            }
        });

        principalView.getMenuItemSalir().addActionListener(e -> System.exit(0));

        principalView.setVisible(true);
        escritorio.repaint();
    }

    private static void abrirVentana(JDesktopPane escritorio, JInternalFrame vista) {
        try {
            if (vista.getParent() == null) {
                escritorio.add(vista);
            }

            if (!vista.isVisible()) {
                vista.setVisible(true);
            }

            vista.toFront();
            vista.setSelected(true);
            vista.requestFocusInWindow();

            escritorio.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void cambiarIdioma(String idioma, String pais) {
        lang = idioma;
        country = pais;
        mensajeHandler.setLenguaje(lang, country);
    }

    private static void actualizarTextos(
            PrincipalView principalView,
            ProductoAnadirView productoAnadirView,
            ProductoListaView productoListaView,
            ProductoEliminarView productoEliminarView,
            ProductoActualizarView productoActualizarView,
            CarritoAñadirView carritoAnadirView,
            ListarCarritoView listarCarritoView,
            ListarCarritoUsuarioView listarCarritoUsuarioView,
            ListarUsuarioView listarUsuarioView,
            LoginView loginView,
            UserRegistroView userRegistroView,
            UsuarioView usuarioView,
            CarritoEditarView carritoEditarView
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
        if (usuarioView != null) usuarioView.setTextos(mensajeHandler);
        if (loginView != null) loginView.setTextos(mensajeHandler);
        if (userRegistroView != null) userRegistroView.setTextos();
        carritoEditarView.setTextos(mensajeHandler);

        productoListaView.actualizarDatos(mensajeHandler);
        productoEliminarView.actualizarInternacionalizacion(mensajeHandler);
        productoActualizarView.actualizarInternacionalizacion(mensajeHandler);
        productoAnadirView.actualizarInternacionalizacion(mensajeHandler);
        listarCarritoView.actualizarMensajeHandler(mensajeHandler);
    }

    private static void configurarAccesoPorRol(Usuario usuario, PrincipalView principalView) {
        if (usuario.getRol() == Rol.CLIENTE) {
            principalView.getMenuItemCrearProducto().setEnabled(false);
            principalView.getMenuItemBuscarProducto().setEnabled(false);
            principalView.getMenuItemEliminarProducto().setEnabled(false);
            principalView.getMenuItemActualizarProducto().setEnabled(false);
            principalView.getMenuItemListarCarrito().setEnabled(false);
            principalView.getMenuItemListarUsuarios().setEnabled(false);
            principalView.getMenuItemBuscarProducto().setEnabled(true);
            principalView.getMenuItemEditarCarrito().setEnabled(false);
        }
    }
}
