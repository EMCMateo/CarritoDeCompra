package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelSecundario;
    private JPasswordField txtPassword;
    private JTextField txtUsername;
    private JButton btnIniciarSesion;
    private JButton btnRegistro;
    private JLabel lblPasswordLogin;
    private JLabel lblUsernameLogin;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    public LoginView(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler){
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        setTextos(mensajeInternacionalizacionHandler);
        setContentPane(panelPrincipal);
        setSize(500,500);
        setTitle("Iniciar Sesion");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setVisible(true);
        setLocationRelativeTo(null);



    }

    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    public void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");
    }



    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JPanel getPanelSecundario() {
        return panelSecundario;
    }

    public void setPanelSecundario(JPanel panelSecundario) {
        this.panelSecundario = panelSecundario;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(JPasswordField txtPassword) {
        this.txtPassword = txtPassword;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public void setBtnIniciarSesion(JButton btnIniciarSesion) {
        this.btnIniciarSesion = btnIniciarSesion;
    }

    public JButton getBtnRegistro() {
        return btnRegistro;
    }

    public void setBtnRegistro(JButton btnRegistro) {
        this.btnRegistro = btnRegistro;
    }

    public void setTextos(ec.edu.ups.util.MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("login.titulo"));
        lblUsernameLogin.setText(mensajeHandler.get("login.lbl.usuario"));
        lblPasswordLogin.setText(mensajeHandler.get("login.lbl.contrasena"));
        btnIniciarSesion.setText(mensajeHandler.get("login.btn.iniciar"));
        btnRegistro.setText(mensajeHandler.get("login.btn.registrarse"));
    }

}
