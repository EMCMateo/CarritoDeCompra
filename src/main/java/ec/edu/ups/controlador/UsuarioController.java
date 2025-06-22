package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.Main;    // importa tu Main

import javax.swing.*;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private Usuario usuario;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView){
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        cargarEventos();
    }

    private void cargarEventos(){
        loginView.getBtnIniciarSesion().addActionListener(e -> autenticar());
    }

    public boolean autenticar() {
        String username = loginView.getTxtUsername().getText();
        String password = loginView.getTxtPassword().getText();

        usuario = usuarioDAO.autenticar(username, password);

        if (usuario == null) {
            loginView.mostrarMensaje("Usuario no encontrado!");
            return false;
        } else {
            loginView.dispose(); // Cierra login

            // Abre la app principal
            Main.iniciarApp();  // Asegúrate de que Main.iniciarApp() sea public

            return true;
        }
    }




    public Usuario getUsuarioAuteticado(){
        return usuario;
    }
}
