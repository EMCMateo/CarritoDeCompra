package ec.edu.ups.vista;

import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;

public class ProductoEliminarView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel lblCodigo;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public ProductoEliminarView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setContentPane(panelPrincipal);
        initComponents();
        configurarVentana();
        setTextos(mensajeHandler);
        setIconos();
    }

    private void initComponents() {
        // Aquí deberías inicializar tus componentes si no usas GUI builder
    }

    private void configurarVentana() {
        setTitle(mensajeHandler.get("producto.eliminar.titulo"));
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setSize(400, 200);
        setVisible(true);
    }

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("producto.eliminar.titulo"));
        lblCodigo.setText(mensajeHandler.get("producto.eliminar.lbl.codigo"));
        lblNombre.setText(mensajeHandler.get("producto.eliminar.lbl.nombre"));
        lblPrecio.setText(mensajeHandler.get("producto.eliminar.lbl.precio"));
        btnBuscar.setText(mensajeHandler.get("producto.eliminar.btn.buscar"));
        btnEliminar.setText(mensajeHandler.get("producto.eliminar.btn.eliminar"));
        if (panelPrincipal.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
            border.setTitle(mensajeHandler.get("producto.eliminar.panel.titulo"));
            panelPrincipal.repaint();
        }
    }

    public void mostrarProducto(String nombre, double precio) {
        txtNombre.setText(nombre);
        String precioFormateado = FormateadorUtils.formatearMoneda(precio, mensajeHandler.getLocale());
        txtPrecio.setText(precioFormateado);
    }


    public void setIconos(){

        setIconoEscalado(btnBuscar, "/ios-search.png", 20, 20);
        setIconoEscalado(btnEliminar, "/ios-trash.png", 20, 20);
    }

    private void setIconoEscalado(JButton boton, String ruta, int ancho, int alto) {
        URL url = getClass().getResource(ruta);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
        }
    }


    public void actualizarInternacionalizacion(MensajeInternacionalizacionHandler nuevoHandler) {
        this.mensajeHandler = nuevoHandler;
        setTextos(nuevoHandler);

        // Si ya hay datos en pantalla, re-formatear el precio
        String textoPrecio = txtPrecio.getText();
        if (!textoPrecio.isEmpty()) {
            try {
                double valor = FormateadorUtils.parsearMoneda(textoPrecio, nuevoHandler.getLocale());
                txtPrecio.setText(FormateadorUtils.formatearMonedaConSimbolo(valor, nuevoHandler.getLocale()));
            } catch (Exception e) {
                txtPrecio.setText("");
            }
        }
    }


    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }


    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnEliminar() { return btnEliminar; }


}
