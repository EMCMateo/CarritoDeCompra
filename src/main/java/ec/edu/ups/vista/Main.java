package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.*;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.excepciones.PersistenciaException;
import ec.edu.ups.excepciones.ValidacionException;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.ConfiguracionAlmacenamiento;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.io.File;

/**
 * Clase principal que actúa como punto de entrada y orquestador de la aplicación.
 * Se encarga de:
 * 1. Iniciar la selección del método de almacenamiento (memoria, texto, binario).
 * 2. Inicializar las capas DAO, Controladores y Vistas.
 * 3. Gestionar el ciclo de vida de la aplicación, desde el login hasta el cierre de sesión.
 * 4. Manejar la internacionalización y la configuración de acceso por roles.
 */
public class Main {

    // --- DAOs (Data Access Objects) ---
    /** DAO para la gestión de usuarios. Su implementación se decide en tiempo de ejecución. */
    private static UsuarioDAO usuarioDAO;
    /** DAO para la gestión de productos. Su implementación se decide en tiempo de ejecución. */
    private static ProductoDAO productoDAO;
    /** DAO para la gestión de carritos. Su implementación se decide en tiempo de ejecución. */
    private static CarritoDAO carritoDAO;
    /** DAO para la gestión de preguntas de seguridad. Su implementación se decide en tiempo de ejecución. */
    private static PreguntaDAO preguntaDAO;

    // --- Internacionalización ---
    /** Código del idioma actual (ej. "es", "en"). */
    private static String lang = "es";
    /** Código del país actual (ej. "EC", "US"). */
    private static String country = "EC";
    /** Manejador central para obtener los textos internacionalizados. */
    private static MensajeInternacionalizacionHandler mensajeHandler;

    /** Flag para asegurar que las preguntas de seguridad se carguen una sola vez. */
    private static boolean preguntasCargadas = false;

    // --- Vistas y Controladores Principales ---
    /** Vista de inicio de sesión. */
    private static LoginView loginView;
    /** Vista de registro de nuevos usuarios. */
    private static UserRegistroView userRegistroView;
    /** Vista para listar todos los usuarios (accesible por el administrador). */
    private static ListarUsuarioView listarUsuarioView;
    /** Controlador principal para la lógica de usuarios. */
    private static UsuarioController usuarioController;
    /** Vista para listar todos los carritos (accesible por el administrador). */
    private static ListarCarritoView listarCarritoView;
    /** Vista para listar los carritos de un usuario específico. */
    private static ListarCarritoUsuarioView listarCarritoUsuarioView;
    /** Controlador principal para la lógica de carritos. */
    private static CarritoController carritoController;

    /**
     * Punto de entrada principal de la aplicación.
     * Inicia la interfaz gráfica en el hilo de despacho de eventos de Swing (EDT).
     * Muestra la ventana de selección de almacenamiento antes de continuar.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
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
                try {
                    inicializarDAOs(tipoLogico, ruta);
                } catch (ValidacionException | PersistenciaException ex) {
                    throw new RuntimeException(ex);
                }

                seleccionView.dispose();
                iniciarVentanaLogin();
            });
        });
    }

    /**
     * Inicializa las implementaciones de los DAO según la selección del usuario.
     * Este método actúa como una fábrica para la capa de persistencia.
     * También carga las preguntas de seguridad si no han sido cargadas previamente.
     *
     * @param tipo El tipo de almacenamiento ("MEMORIA", "TEXTO", "BINARIO").
     * @param ruta La ruta base para el almacenamiento en archivos (puede ser nula para memoria).
     * @throws ValidacionException Si ocurre un error de validación durante la inicialización.
     * @throws PersistenciaException Si ocurre un error de I/O durante la inicialización.
     */
    private static void inicializarDAOs(String tipo, String ruta) throws ValidacionException, PersistenciaException {
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

    /**
     * Prepara e inicia la ventana de Login, junto con las vistas y el controlador necesarios
     * para el proceso de autenticación y registro.
     */
    private static void iniciarVentanaLogin() {
        loginView = new LoginView(mensajeHandler);
        userRegistroView = new UserRegistroView(mensajeHandler);
        listarUsuarioView = new ListarUsuarioView(usuarioDAO, mensajeHandler);
        usuarioController = new UsuarioController(usuarioDAO, loginView, userRegistroView, listarUsuarioView, mensajeHandler, preguntaDAO);
        loginView.setVisible(true);
    }

    /**
     * Inicia la aplicación principal después de una autenticación exitosa.
     * Configura la ventana principal (MDI), inicializa todas las vistas y controladores
     * necesarios para la sesión del usuario, y establece los menús según el rol.
     *
     * @param usuario El usuario que ha iniciado sesión.
     * @param idioma  El código del idioma para la sesión.
     * @param pais    El código del país para la sesión.
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

    /**
     * Método utilitario para abrir y gestionar ventanas internas (JInternalFrame)
     * dentro del escritorio principal (JDesktopPane).
     * Evita que se abran múltiples instancias de la misma ventana.
     *
     * @param escritorio El JDesktopPane contenedor.
     * @param vista      La ventana JInternalFrame que se va a abrir.
     */
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

    /**
     * Cambia el idioma y país actual en el manejador de internacionalización.
     *
     * @param idioma El nuevo código de idioma.
     * @param pais   El nuevo código de país.
     */
    private static void cambiarIdioma(String idioma, String pais) {
        lang = idioma;
        country = pais;
        mensajeHandler.setLenguaje(lang, country);
    }

    /**
     * Actualiza los textos de todas las vistas activas de la aplicación
     * después de un cambio de idioma.
     *
     * @param principalView La ventana principal.
     * @param productoAnadirView Vista para añadir productos.
     * @param productoListaView Vista para listar productos.
     * @param productoEliminarView Vista para eliminar productos.
     * @param productoActualizarView Vista para actualizar productos.
     * @param carritoAnadirView Vista para añadir carritos.
     * @param listarCarritoView Vista para listar todos los carritos.
     * @param listarCarritoUsuarioView Vista para listar carritos de un usuario.
     * @param listarUsuarioView Vista para listar usuarios.
     * @param loginView Vista de login (puede ser nula).
     * @param userRegistroView Vista de registro (puede ser nula).
     * @param usuarioView Vista de perfil de usuario.
     * @param carritoEditarView Vista para editar carritos.
     */
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

    /**
     * Configura la visibilidad y el estado de los menús en la ventana principal
     * basándose en el rol del usuario que ha iniciado sesión.
     *
     * @param usuario       El usuario autenticado.
     * @param principalView La ventana principal cuyos menús se configurarán.
     */
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