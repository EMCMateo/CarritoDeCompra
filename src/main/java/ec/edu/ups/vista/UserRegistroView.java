package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Vista para el registro de usuarios.
 * Permite ingresar datos personales y de acceso del usuario.
 */
public class UserRegistroView extends JFrame {
    private JPanel panelPrincipal;
    private JLabel lblNombreCompleto;
    private JTextField txtNombreCompleto;
    private JLabel lblCorreo;
    private JTextField txtCorreo;
    private JLabel lblTelefono;
    private JTextField txtTelefono;
    private JLabel lblFechaNacimiento;
    private JTextField txtFechaNacimiento;
    private JLabel lblCedula; // Cambiado: era lblUsername
    private JTextField txtCedula; // Cambiado: era txtUsername
    private JLabel lblPassword;
    private JPasswordField pswContra;
    private JLabel lblPasswordConfirm;
    private JPasswordField pswContra2;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JComboBox cmbGenero;
    private JLabel lblGenero;

    private MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Constructor de la vista UserRegistroView.
     * Inicializa los componentes y configura la ventana.
     *
     * @param mensajeHandler Manejador de mensajes para internacionalización.
     */

    public UserRegistroView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setContentPane(panelPrincipal);
        setResizable(true);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setTitle(mensajeHandler.get("usuario.registro.panel.titulo"));
        setTextos();
        setIconos();
        cargarGeneros();
    }

    /**
     * Configura los textos de los componentes de la vista.
     * Utiliza el manejador de mensajes para obtener los textos internacionalizados.
     */


    public void setTextos() {
        setTitle(mensajeHandler.get("usuario.registro.panel.titulo"));

        lblNombreCompleto.setText(mensajeHandler.get("usuario.view.nombre"));
        lblCorreo.setText(mensajeHandler.get("usuario.view.correo"));
        lblTelefono.setText(mensajeHandler.get("usuario.view.telefono"));
        lblFechaNacimiento.setText(mensajeHandler.get("usuario.view.fechaNac"));
        lblCedula.setText("Cédula"); // Cambiado: era lblUsername, ahora lblCedula

        lblPassword.setText(mensajeHandler.get("login.view.password"));
        lblPasswordConfirm.setText(mensajeHandler.get("login.view.repetir.password"));

        btnConfirmar.setText(mensajeHandler.get("usuario.view.guardar"));
        btnCancelar.setText(mensajeHandler.get("boton.cancelar"));
    }

    /**
     * Carga los géneros disponibles en el JComboBox cmbGenero.
     * Utiliza el manejador de mensajes para obtener los nombres de los géneros.
     */

    private void cargarGeneros() {
        cmbGenero.removeAllItems();
        cmbGenero.addItem(mensajeHandler.get("genero.masculino"));
        cmbGenero.addItem(mensajeHandler.get("genero.femenino"));
        cmbGenero.addItem(mensajeHandler.get("genero.nobinario"));
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JTextField getTxtNombreCompleto() {
        return txtNombreCompleto;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtFechaNacimiento() {
        return txtFechaNacimiento;
    }

    public JTextField getTxtCedula() { // Cambiado: era getTxtUsername
        return txtCedula;
    }

    public JPasswordField getPswContra() {
        return pswContra;
    }

    public JPasswordField getPswContra2() {
        return pswContra2;
    }

    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JLabel getLblNombreCompleto() {
        return lblNombreCompleto;
    }

    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    public JLabel getLblTelefono() {
        return lblTelefono;
    }

    public JLabel getLblFechaNacimiento() {
        return lblFechaNacimiento;
    }

    public JLabel getLblCedula() {
        return lblCedula;
    }

    public JLabel getLblPassword() {
        return lblPassword;
    }

    public JLabel getLblPasswordConfirm() {
        return lblPasswordConfirm;
    }

    public JComboBox getCmbGenero() {
        return cmbGenero;
    }

    public void limpiarTodo(){
        txtCedula.setText(""); // Cambiado: era txtUsername
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");
        txtNombreCompleto.setText("");
        pswContra.setText("");
        pswContra2.setText("");
    }

    public void setIconos(){

        setIconoEscalado(btnCancelar, "/ios-undo.png", 20, 20);
        setIconoEscalado(btnConfirmar, "/md-done-all.png", 20, 20);
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
