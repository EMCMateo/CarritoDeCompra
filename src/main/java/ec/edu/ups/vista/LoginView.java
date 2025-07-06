package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;

public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JPasswordField txtPassword;
    private JTextField txtUsername;
    private JButton btnIniciarSesion;
    private JButton btnRegistro;
    private JLabel lblPasswordLogin;
    private JLabel lblUsernameLogin;
    private JLabel lblRecuperacion;
    private JButton btnRecuperar;

    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    public LoginView(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;

        setContentPane(panelPrincipal);
        inicializarComponentes();
        setIconos();
    }

    private void inicializarComponentes() {


        setSize(500, 300);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        setTextos(mensajeInternacionalizacionHandler);

        setLocationRelativeTo(null); // Centrar después de pack()

        setVisible(true);
    }



    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public JButton getBtnRegistro() {
        return btnRegistro;
    }

    public JButton getBtnRecuperar() {
        return btnRecuperar;
    }

    public JLabel getLblRecuperacion() {
        return lblRecuperacion;
    }

    public JLabel getLblUsernameLogin() {
        return lblUsernameLogin;
    }

    public JLabel getLblPasswordLogin() {
        return lblPasswordLogin;
    }

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("login.titulo"));
        lblUsernameLogin.setText(mensajeHandler.get("login.lbl.usuario"));
        lblPasswordLogin.setText(mensajeHandler.get("login.lbl.contrasena"));
        btnIniciarSesion.setText(mensajeHandler.get("login.btn.iniciar"));
        btnRegistro.setText(mensajeHandler.get("login.btn.registrarse"));
        lblRecuperacion.setText(mensajeHandler.get("login.lbl.recuperar"));
        btnRecuperar.setText(mensajeHandler.get("login.btn.recuperar"));
        if (panelPrincipal.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
            border.setTitle(mensajeHandler.get("log.in.panel.titulo"));
            panelPrincipal.repaint();
        }
    }

    public void setIconos() {
        setIconoEscalado(btnIniciarSesion, "/ios-arrow-dropright-circle.png", 20, 20);
        setIconoEscalado(btnRegistro, "/md-person-add.png", 20, 20);
        setIconoEscalado(btnRecuperar, "/ios-repeat.png", 20, 20);
    }
    private void setIconoEscalado(JButton boton, String ruta, int ancho, int alto) {
        URL url = getClass().getResource(ruta);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
        }
    }



}