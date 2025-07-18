package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.*;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.ConfiguracionAlmacenamiento;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.io.File;

public class Main {

    // Cambiamos de final para poder reasignar según la selección
    private static UsuarioDAO usuarioDAO;
    private static ProductoDAO productoDAO;
    private static CarritoDAO carritoDAO;
    private static PreguntaDAO preguntaDAO;

    private static String lang = "es";
    private static String country = "EC";
    private static MensajeInternacionalizacionHandler mensajeHandler;

    private static boolean preguntasCargadas = false;

    private static LoginView loginView;
    private static UserRegistroView userRegistroView;
    private static ListarUsuarioView listarUsuarioView;
    private static UsuarioController usuarioController;
    private static ListarCarritoView listarCarritoView;
    private static ListarCarritoUsuarioView listarCarritoUsuarioView;
    private static CarritoController carritoController;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            mensajeHandler = new MensajeInternacionalizacionHandler(lang, country);

            // Mostrar siempre la ventana de selección al inicio
            SeleccionAlmacenamientoView seleccionView = new SeleccionAlmacenamientoView(mensajeHandler);
            seleccionView.setVisible(true);

            seleccionView.getBtnContinuar().addActionListener(e -> {
                // Se obtiene el objeto completo, no solo el texto
                SeleccionAlmacenamientoView.AlmacenamientoOpcion opcion = seleccionView.getSeleccion();
                String tipoLogico = opcion.getKey(); // "MEMORIA", "TEXTO" o "BINARIO"
                String ruta = seleccionView.getRutaSeleccionada();

                // Validar que se tenga una ruta si no es almacenamiento en memoria
                if (!tipoLogico.equals("MEMORIA") && (ruta == null || ruta.isEmpty())) {
                    JOptionPane.showMessageDialog(seleccionView,
                        mensajeHandler.get("seleccion.error.noCarpeta"),
                        mensajeHandler.get("error.titulo"),
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear la carpeta si no existe
                if (!tipoLogico.equals("MEMORIA")) {
                    File carpeta = new File(ruta);
                    if (!carpeta.exists()) {
                        if (!carpeta.mkdirs()) {
                            JOptionPane.showMessageDialog(seleccionView,
                                mensajeHandler.get("seleccion.error.crearCarpeta"),
                                mensajeHandler.get("error.titulo"),
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                // Se guarda el texto visible para el usuario, pero se usa la clave lógica para el funcionamiento
                ConfiguracionAlmacenamiento.guardarConfiguracion(opcion.toString(), (ruta != null) ? ruta : "");

                // Inicializar DAOs con la selección
                inicializarDAOs(tipoLogico, ruta);

                seleccionView.dispose();
                iniciarVentanaLogin();
            });
        });
    }

    private static void inicializarDAOs(String tipo, String ruta) {
        if (tipo.equals("MEMORIA")) {
            usuarioDAO = new UsuarioDAOMemoria();
            productoDAO = new ProductoDAOMemoria();
            carritoDAO = new CarritoDAOMemoria(usuarioDAO);
            preguntaDAO = new PreguntaDAOMemoria();
        } else if (tipo.equals("TEXTO")) {
            usuarioDAO = new UsuarioDAOArchivoTexto(ruta);
            productoDAO = new ProductoDAOArchivoTexto(ruta);
            carritoDAO = new CarritoDAOArchivoTexto(ruta, usuarioDAO, productoDAO);


            preguntaDAO = new PreguntaDAOArchivoTexto(ruta);
        } else { // Archivo binario
            usuarioDAO = new UsuarioDAOArchivoBinario(ruta);
            productoDAO = new ProductoDAOArchivoBinario(ruta);
            carritoDAO = new CarritoDAOArchivoBinario(ruta, usuarioDAO, productoDAO);

            preguntaDAO = new PreguntaDAOArchivoBinario(ruta);
        }

        // Refrescar vistas de carritos si existen y están inicializadas
        if (listarCarritoView != null) {
            listarCarritoView.cargarDatosConFormato(carritoDAO.listarTodos(), mensajeHandler);
        }
        if (listarCarritoUsuarioView != null) {
            listarCarritoUsuarioView.cargarDatosConFormato(carritoDAO.listarTodos(), mensajeHandler);
        }
        // Forzar recarga de datos en el controlador si existe
        if (carritoController != null) {
            carritoController.listarCarrito();
        }

        if (!preguntasCargadas) {
            for (int i = 1; i <= 10; i++) {
                preguntaDAO.agregarPregunta(
                    new Pregunta(i, mensajeHandler.get("pregunta.seguridad." + i))
                );
            }
            preguntasCargadas = true;
        }
    }

    private static void iniciarVentanaLogin() {
        loginView = new LoginView(mensajeHandler);
        userRegistroView = new UserRegistroView(mensajeHandler);
        listarUsuarioView = new ListarUsuarioView(usuarioDAO, mensajeHandler);
        usuarioController = new UsuarioController(usuarioDAO, loginView, userRegistroView, listarUsuarioView, mensajeHandler, preguntaDAO);
        loginView.setVisible(true);
    }

    /**
     * Inicia la aplicación con el usuario autenticado y el idioma seleccionado.
     *
     * @param usuario El usuario autenticado.
     * @param idioma  El idioma seleccionado.
     * @param pais    El país seleccionado.
     */

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
        listarCarritoView = new ListarCarritoView(mensajeHandler);
        listarCarritoUsuarioView = new ListarCarritoUsuarioView(mensajeHandler);
        listarUsuarioView = new ListarUsuarioView(usuarioDAO, mensajeHandler);
        UsuarioView usuarioView = new UsuarioView(mensajeHandler);
        CarritoEditarView carritoEditarView = new CarritoEditarView(mensajeHandler);

        // Controladores
        new ProductoController(productoDAO, productoAnadirView, productoListaView,
                carritoAnadirView, productoEliminarView, productoActualizarView, mensajeHandler);

        carritoController = new CarritoController(
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
