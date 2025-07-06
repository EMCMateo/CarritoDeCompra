package ec.edu.ups.vista;

import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;
import java.util.Locale;

public class ProductoActualizarView extends JInternalFrame {

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JPanel panelPrincipal;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public ProductoActualizarView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setContentPane(panelPrincipal);
        initComponents();
        configurarVentana();
        setTextos(mensajeHandler);
    }

    private void initComponents() {
        // Aquí deberías inicializar tus componentes si no lo estás haciendo en el GUI builder
    }

    private void configurarVentana() {
        setTitle(mensajeHandler.get("producto.actualizar.titulo"));
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setSize(400, 200);
        setVisible(true);
        setIconos();
    }

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("producto.actualizar.titulo"));
        lblCodigo.setText(mensajeHandler.get("producto.actualizar.lbl.codigo"));
        lblNombre.setText(mensajeHandler.get("producto.actualizar.lbl.nombre"));
        lblPrecio.setText(mensajeHandler.get("producto.actualizar.lbl.precio"));
        btnBuscar.setText(mensajeHandler.get("producto.actualizar.btn.buscar"));
        btnActualizar.setText(mensajeHandler.get("producto.actualizar.btn.actualizar"));
        if (panelPrincipal.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
            border.setTitle(mensajeHandler.get("producto.actualizar.panel.titulo"));
            panelPrincipal.repaint();
        }
    }

    public void mostrarProducto(String nombre, double precio, Locale locale) {
        txtNombre.setText(nombre);
        txtPrecio.setText(FormateadorUtils.formatearMoneda(precio, locale));
    }


    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnActualizar() { return btnActualizar; }

    public void actualizarInternacionalizacion(MensajeInternacionalizacionHandler nuevoHandler) {
        this.mensajeHandler = nuevoHandler;
        setTextos(nuevoHandler);

        // Re-formatear el campo de precio si tiene contenido
        String textoPrecio = txtPrecio.getText();
        if (!textoPrecio.isEmpty()) {
            try {
                double valor = FormateadorUtils.parsearMoneda(textoPrecio, nuevoHandler.getLocale());
                txtPrecio.setText(FormateadorUtils.formatearMoneda(valor, nuevoHandler.getLocale()));
            } catch (Exception e) {
                txtPrecio.setText("");
            }
        }
    }


    public void setIconos(){

        setIconoEscalado(btnBuscar, "/ios-search.png", 20, 20);
        setIconoEscalado(btnActualizar, "/ios-create.png", 20, 20);
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

