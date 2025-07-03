package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

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

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        System.out.println("Panel principal: " + panelPrincipal);
        setContentPane(panelPrincipal);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        setTextos(mensajeInternacionalizacionHandler);

        pack();
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
    }


}