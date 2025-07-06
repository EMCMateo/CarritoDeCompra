package ec.edu.ups.vista;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private MensajeInternacionalizacionHandler mensajeHandler;

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

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setTitle(mensajeHandler.get("usuario.view.titulo"));
        lblNombre.setText(mensajeHandler.get("usuario.view.nombre"));
        lblTelefono.setText(mensajeHandler.get("usuario.view.telefono"));
        lblCorreo.setText(mensajeHandler.get("usuario.view.correo"));
        lblFechaNac.setText(mensajeHandler.get("usuario.view.fechaNac"));
        btnGuardar.setText(mensajeHandler.get("usuario.view.guardar"));
        btnRecuperar.setText(mensajeHandler.get("usuario.view.recuperar"));
    }

    public void cargarDatosUsuario(Usuario usuario) {
        txtNombre.setText(usuario.getNombreCompleto());
        txtTelefono.setText(usuario.getTelefono());
        txtCorreoUser.setText(usuario.getCorreo());

        String fechaFormateada = usuario.getFechaNacimiento();

        if (fechaFormateada != null && !fechaFormateada.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = sdf.parse(usuario.getFechaNacimiento());
                fechaFormateada = FormateadorUtils.formatearFecha(fecha, mensajeHandler.getLocale());
            } catch (ParseException e) {
            }
        }

        txtFechaNac.setText(fechaFormateada);
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
