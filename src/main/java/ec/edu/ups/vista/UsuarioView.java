package ec.edu.ups.vista;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Vista para gestionar la información del usuario.
 * Permite visualizar y editar los datos del usuario.
 */

public class UsuarioView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblTelefono;
    private JTextField txtTelefono;
    private JLabel lblCorreo;
    private JTextField txtCorreoUser;
    private JLabel lblFechaNac;
    private JTextField txtFechaNac;
    private JButton btnGuardar;
    private JButton btnRecuperar;
    private JTextField txtGenero;
    private JLabel lblGenero;

    private MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Constructor de la vista UsuarioView.
     * Inicializa los componentes y configura la ventana.
     *
     * @param mensajeHandler Manejador de mensajes para internacionalización.
     */
    public UsuarioView(MensajeInternacionalizacionHandler mensajeHandler) {
        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(400, 300);
        setLocation(180,0);
        setTextos(mensajeHandler);
        setIconos();



    }
/**
     * Inicializa los componentes de la vista.
     * Configura el tamaño, operación de cierre, y textos de los componentes.
     */

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setTitle(mensajeHandler.get("usuario.view.titulo"));
        lblNombre.setText(mensajeHandler.get("usuario.view.nombre"));
        lblTelefono.setText(mensajeHandler.get("usuario.view.telefono"));
        lblCorreo.setText(mensajeHandler.get("usuario.view.correo"));
        lblFechaNac.setText(mensajeHandler.get("usuario.view.fechaNac"));
        lblGenero.setText(mensajeHandler.get("usuario.genero"));
        btnGuardar.setText(mensajeHandler.get("usuario.view.guardar"));
        btnRecuperar.setText(mensajeHandler.get("usuario.view.recuperar"));
    }

    /**
     * Carga los datos del usuario en los campos del formulario.
     * Llama este método desde el controlador después de crear UsuarioView y antes de mostrarla.
     */
    public void cargarDatosUsuario(Usuario usuario) {
        if (usuario == null) return;
        txtNombre.setText(usuario.getNombreCompleto());
        txtTelefono.setText(usuario.getTelefono());
        txtCorreoUser.setText(usuario.getCorreo());
        txtFechaNac.setText(usuario.getFechaNacimiento());
        txtGenero.setText(usuario.getGenero()); // ✅ Línea añadida
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JLabel getLblTelefono() {
        return lblTelefono;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    public JTextField getTxtCorreoUser() {
        return txtCorreoUser;
    }

    public JLabel getLblFechaNac() {
        return lblFechaNac;
    }

    public JTextField getTxtFechaNac() {
        return txtFechaNac;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnRecuperar() {
        return btnRecuperar;
    }

    public JTextField getTxtGenero() {
        return txtGenero;
    }

    public void setIconos() {
        setIconoEscalado(btnRecuperar, "/ios-undo.png", 20, 20);
        setIconoEscalado(btnGuardar, "/md-done-all.png", 20, 20);

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
