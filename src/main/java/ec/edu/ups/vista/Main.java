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
    private static MensajeInternacionalizacionHandler mensajeHandler;

    private static boolean preguntasCargadas = false;

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

            LoginView loginView = new LoginView(mensajeHandler);
            UserRegistroView userRegistroView = new UserRegistroView(mensajeHandler);
            ListarUsuarioView listarUsuarioView = new ListarUsuarioView(usuarioDAO, mensajeHandler);

            new UsuarioController(usuarioDAO, loginView, userRegistroView, listarUsuarioView, mensajeHandler, preguntaDAO);
            loginView.setVisible(true);
            userRegistroView.setVisible(false);

        });
    }

    public static void iniciarApp(Usuario usuario, String idioma, String pais) {
        lang = idioma;
        country = pais;
        mensajeHandler.setLenguaje(lang, country);

        PrincipalView principalView = new PrincipalView(mensajeHandler);
        MiJDesktopPane escritorio = principalView.getDesktop(); // importante

        // Vistas
        // ProductoAnadirView se creará cada vez que se abra la ventana
        ProductoAnadirView[] productoAnadirViewRef = new ProductoAnadirView[1];
        ProductoListaView productoListaView = new ProductoListaView(mensajeHandler);
        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensajeHandler);
        ProductoActualizarView productoActualizarView = new ProductoActualizarView(mensajeHandler);
        CarritoAñadirView carritoAnadirView = new CarritoAñadirView(mensajeHandler);
        ListarCarritoView listarCarritoView = new ListarCarritoView(mensajeHandler);
        ListarCarritoUsuarioView listarCarritoUsuarioView = new ListarCarritoUsuarioView(mensajeHandler);
        ListarUsuarioView listarUsuarioView = new ListarUsuarioView(usuarioDAO, mensajeHandler);
        LoginView loginView = new LoginView(mensajeHandler);
        UserRegistroView registroView = new UserRegistroView(mensajeHandler);

        // Controladores
        // El controlador se creará cada vez que se cree la vista

        new CarritoController(carritoDAO, carritoAnadirView, productoDAO, new Carrito(), listarCarritoView,
                usuario, mensajeHandler, listarCarritoUsuarioView, usuarioDAO).carritoEventos();

        configurarAccesoPorRol(usuario, principalView);

        // Menú de idioma
        principalView.getMenuItemES().addActionListener(e -> {
            cambiarIdioma("es", "EC");
            actualizarTextos(principalView, productoAnadirView, productoListaView, productoEliminarView, productoActualizarView,
                    carritoAnadirView, listarCarritoView, listarCarritoUsuarioView, listarUsuarioView, loginView, registroView);
        });

        principalView.getMenuItemEN().addActionListener(e -> {
            cambiarIdioma("en", "US");
            actualizarTextos(principalView, productoAnadirView, productoListaView, productoEliminarView, productoActualizarView,
                    carritoAnadirView, listarCarritoView, listarCarritoUsuarioView, listarUsuarioView, loginView, registroView);
        });

        principalView.getMenuItemIT().addActionListener(e -> {
            cambiarIdioma("it", "IT");
            actualizarTextos(principalView, productoAnadirView, productoListaView, productoEliminarView, productoActualizarView,
                    carritoAnadirView, listarCarritoView, listarCarritoUsuarioView, listarUsuarioView, loginView, registroView);
        });

        // Menús funcionales
        principalView.getMenuItemCrearProducto().addActionListener(e -> {
            productoAnadirViewRef[0] = new ProductoAnadirView(mensajeHandler);
            new ProductoController(productoDAO, productoAnadirViewRef[0], productoListaView,
                    carritoAnadirView, productoEliminarView, productoActualizarView, mensajeHandler).inicializarEventos();
            abrirVentana(escritorio, productoAnadirViewRef[0]);
        });
        principalView.getMenuItemBuscarProducto().addActionListener(e -> abrirVentana(escritorio, productoListaView));
        principalView.getMenuItemEliminarProducto().addActionListener(e -> abrirVentana(escritorio, productoEliminarView));
        principalView.getMenuItemActualizarProducto().addActionListener(e -> abrirVentana(escritorio, productoActualizarView));
        principalView.getMenuItemCrearCarrito().addActionListener(e -> abrirVentana(escritorio, carritoAnadirView));
        principalView.getMenuItemListarCarrito().addActionListener(e -> abrirVentana(escritorio, listarCarritoView));
        principalView.getMenutItemListarCarritoUsuario().addActionListener(e -> abrirVentana(escritorio, listarCarritoUsuarioView));
        principalView.getMenuItemListarUsuarios().addActionListener(e -> abrirVentana(escritorio, listarUsuarioView));

        // Cerrar sesión
        principalView.getMenuItemCerrarSesion().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(principalView,
                    mensajeHandler.get("menu.cerrar.confirmacion"),
                    mensajeHandler.get("menu.cerrar.titulo"),
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                principalView.dispose();
                main(new String[0]);
            }
        });

        // Salir
        principalView.getMenuItemSalir().addActionListener(e -> System.exit(0));

        principalView.setVisible(true);
        escritorio.repaint();

    }

    private static void abrirVentana(JDesktopPane escritorio, JInternalFrame vista) {
        try {
            System.out.println("Abriendo vista: " + vista.getClass().getSimpleName());
            if (!vista.isVisible()) {
                escritorio.add(vista);
                vista.setVisible(true);
                vista.setClosable(true);
                vista.setIconifiable(true);
                vista.setMaximizable(true);
                vista.setResizable(true);
            }
            vista.toFront();
            vista.requestFocusInWindow();
            vista.setSelected(true);
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
            UserRegistroView registroView
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
        registroView.setTextos(mensajeHandler);
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
}
