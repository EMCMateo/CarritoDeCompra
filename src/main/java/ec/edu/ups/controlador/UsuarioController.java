package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.ListarUsuarioView;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.Main;
import ec.edu.ups.vista.UserRegistroView;

import java.util.List;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final UserRegistroView userRegistroView;
    private final ListarUsuarioView listarUsuarioView;
    private final MensajeInternacionalizacionHandler mensajeHandler;

    private Usuario usuario;

    public UsuarioController(UsuarioDAO usuarioDAO,
                             LoginView loginView,
                             UserRegistroView userRegistroView,
                             ListarUsuarioView listarUsuarioView,
                             MensajeInternacionalizacionHandler mensajeHandler) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.userRegistroView = userRegistroView;
        this.listarUsuarioView = listarUsuarioView;
        this.mensajeHandler = mensajeHandler;

        cargarEventosLogin();
        cargarEventosRegistro();
        cargarEventosListarUsuarios();
    }

    private void cargarEventosLogin() {
        loginView.getBtnIniciarSesion().addActionListener(e -> {
            autenticar();
            userRegistroView.limpiarCampos();
            loginView.limpiarCampos();
        });

        loginView.getBtnRegistro().addActionListener(e -> {
            loginView.dispose();
            userRegistroView.setVisible(true);
        });
    }

    private void cargarEventosRegistro() {
        userRegistroView.getBtnConfirmar().addActionListener(e -> {
            String username = userRegistroView.getTxtUsername().getText().trim();
            String password = new String(userRegistroView.getPswContra().getPassword());
            String password2 = new String(userRegistroView.getPswContra2().getPassword());

            if (username.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.error.camposVacios"));
                return;
            }

            if (!password.equals(password2)) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.register.contraseñaRepetida"));
                return;
            }

            if (usuarioDAO.buscarPorUsername(username) != null) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.error.nombreUsado"));
                return;
            }

            Usuario nuevoUsuario = new Usuario(username, password, Rol.CLIENTE);
            usuarioDAO.crear(nuevoUsuario);

            userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.registrado"));
            userRegistroView.limpiarCampos();
            loginView.limpiarCampos();
            userRegistroView.dispose();
            loginView.setVisible(true);
        });

        userRegistroView.getBtnCancelar().addActionListener(e -> {
            userRegistroView.dispose();
            loginView.setVisible(true);
        });
    }

    private void cargarEventosListarUsuarios() {
        listarUsuarioView.getBtnListarTodos().addActionListener(e ->
                listarUsuarioView.cargarDatos(usuarioDAO.listarTodos())
        );

        listarUsuarioView.getBtnClientes().addActionListener(e ->
                listarUsuarioView.cargarDatos(usuarioDAO.listarClientes())
        );

        listarUsuarioView.getBtnAdmin().addActionListener(e ->
                listarUsuarioView.cargarDatos(usuarioDAO.listarAdmin())
        );

        listarUsuarioView.getBtnBuscar().addActionListener(e -> {
            String username = listarUsuarioView.getTxtUsername().getText().trim();
            Usuario u = usuarioDAO.buscarPorUsername(username);
            if (u != null) {
                listarUsuarioView.cargarDatos(List.of(u));
            } else {
                listarUsuarioView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.buscarUsername.noEncontrado"));
            }
        });
    }

    public boolean autenticar() {
        String username = loginView.getTxtUsername().getText().trim();
        String password = new String(loginView.getTxtPassword().getPassword());
        usuario = usuarioDAO.autenticar(username, password);
        if (usuario == null) {
            loginView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.login.error"));
            return false;
        } else {
            loginView.dispose();
            Main.iniciarApp(usuario, "es", "EC");
            return true;
        }
    }

    public Usuario getUsuarioAutenticado() {
        return usuario;
    }
}
