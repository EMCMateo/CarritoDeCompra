package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;

/**
 * Vista para el inicio de sesión del usuario.
 * Permite ingresar la cédula y contraseña para iniciar sesión.
 */

public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JPasswordField txtPassword;
    private JTextField txtCedula; // Cambiado: era txtUsername, ahora es txtCedula
    private JButton btnIniciarSesion;
    private JButton btnRegistro;
    private JLabel lblPasswordLogin;
    private JLabel lblCedulaLogin; // Cambiado: era lblUsernameLogin, ahora es lblCedulaLogin
    private JLabel lblRecuperacion;
    private JButton btnRecuperar;

    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    /**
     * Constructor de la vista LoginView.
     * Inicializa los componentes y configura la ventana.
     *
     * @param mensajeInternacionalizacionHandler Manejador de mensajes para internacionalización.
     */

    public LoginView(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;

        setContentPane(panelPrincipal);
        inicializarComponentes();
        setIconos();
    }

    /**
     * Inicializa los componentes de la vista.
     * Configura el tamaño, operación de cierre, y textos de los componentes.
     */

    private void inicializarComponentes() {


        setSize(500, 300);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        setTextos(mensajeInternacionalizacionHandler);

        setLocationRelativeTo(null); // Centrar después de pack()

        setVisible(true);
    }

    /**
     * Muestra un mensaje de diálogo con el texto proporcionado.
     *
     * @param message El mensaje a mostrar.
     */
    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void limpiarCampos() {
        txtCedula.setText("");
        txtPassword.setText("");
    }

    public JTextField getTxtCedula() { // Cambiado: era getTxtUsername
        return txtCedula;
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

    public JLabel getLblCedulaLogin() { // Cambiado: era getLblUsernameLogin
        return lblCedulaLogin;
    }

    public JLabel getLblPasswordLogin() {
        return lblPasswordLogin;
    }

    /**
     * Establece los textos de los componentes de la vista según el manejador de mensajes.
     * Utiliza claves para obtener los textos internacionalizados.
     *
     * @param mensajeHandler Manejador de mensajes para obtener los textos.
     */

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("login.titulo"));
        lblCedulaLogin.setText(mensajeHandler.get("login.lbl.usuario")); // Cambiado: era "Usuario", ahora "Cédula"
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
    /**
     * Establece los iconos de los botones de la vista.
     * Los iconos se escalan a un tamaño específico.
     */

    public void setIconos() {
        setIconoEscalado(btnIniciarSesion, "/ios-arrow-dropright-circle.png", 20, 20);
        setIconoEscalado(btnRegistro, "/md-person-add.png", 20, 20);
        setIconoEscalado(btnRecuperar, "/ios-repeat.png", 20, 20);
    }
    /**
     * Establece un icono escalado para un botón.
     *
     * @param boton El botón al que se le asignará el icono.
     * @param ruta  La ruta del icono a cargar.
     * @param ancho El ancho al que se escalará el icono.
     * @param alto  El alto al que se escalará el icono.
     */
    private void setIconoEscalado(JButton boton, String ruta, int ancho, int alto) {
        URL url = getClass().getResource(ruta);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
        }
    }



}