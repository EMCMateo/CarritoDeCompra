package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.ListarUsuarioView;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.Main;
import ec.edu.ups.vista.UserRegistroView;

import javax.swing.*;
import java.util.List;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final UserRegistroView userRegistroView;
    private Usuario usuario;
    private ListarUsuarioView listarUsuarioView;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, UserRegistroView userRegistroView, ListarUsuarioView listarUsuarioView){
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.userRegistroView = userRegistroView;
        this.listarUsuarioView = listarUsuarioView;
        cargarEventos();
        eventosRegistro();
        eventosListar(); // ACTIVAR BOTONES DE LISTADO
    }

    private void cargarEventos(){
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

    private void eventosRegistro() {
        userRegistroView.getBtnConfirmar().addActionListener(e -> {
            String username = userRegistroView.getTxtUsername().getText().trim();
            String password = new String(userRegistroView.getPswContra().getPassword());
            String password2 = new String(userRegistroView.getPswContra2().getPassword());

            if (username.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                userRegistroView.mostrarMensaje("Por favor, complete todos los campos.");
                return;
            }

            if (!password.equals(password2)) {
                userRegistroView.mostrarMensaje("Las contraseñas no coinciden.");
                return;
            }

            if (usuarioDAO.buscarPorUsername(username) != null) {
                userRegistroView.mostrarMensaje("El nombre de usuario ya está en uso.");
                return;
            }

            Usuario nuevoUsuario = new Usuario(username, password, Rol.CLIENTE);
            usuarioDAO.crear(nuevoUsuario);

            userRegistroView.mostrarMensaje("Registro exitoso. Inicie sesión con sus credenciales.");
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

    private void eventosListar() {
        listarUsuarioView.getBtnListarTodos().addActionListener(e -> {
            listarUsuarioView.cargarDatos(usuarioDAO.listarTodos());
        });

        listarUsuarioView.getBtnClientes().addActionListener(e -> {
            listarUsuarioView.cargarDatos(usuarioDAO.listarClientes());
        });

        listarUsuarioView.getBtnAdmin().addActionListener(e -> {
            listarUsuarioView.cargarDatos(usuarioDAO.listarAdmin());
        });

        listarUsuarioView.getBtnBuscar().addActionListener(e -> {
            String username = listarUsuarioView.getTxtUsername().getText().trim();
            Usuario u = usuarioDAO.buscarPorUsername(username);
            if (u != null) {
                listarUsuarioView.cargarDatos(List.of(u));
            } else {
                listarUsuarioView.mostrarMensaje("Usuario no encontrado.");
            }
        });
    }

    public boolean autenticar() {
        String username = loginView.getTxtUsername().getText();
        String password = loginView.getTxtPassword().getText();

        usuario = usuarioDAO.autenticar(username, password);

        if (usuario == null) {
            loginView.mostrarMensaje("Usuario no encontrado!");
            return false;
        } else {
            loginView.dispose();
            Main.iniciarApp(usuario);
            return true;
        }
    }

    public Usuario getUsuarioAuteticado(){
        return usuario;
    }
}
