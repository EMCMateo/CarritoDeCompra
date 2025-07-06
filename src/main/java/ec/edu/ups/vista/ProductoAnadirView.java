package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class ProductoAnadirView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtPrecio;
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;

    private MensajeInternacionalizacionHandler mensajeHandler;

    public ProductoAnadirView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setContentPane(panelPrincipal);
        initComponents();
        btnLimpiar.addActionListener(e -> limpiarCampos());
        setTextos(mensajeHandler);
        configurarVentana();
        setIconos();
    }

    private void initComponents() {
        // Aquí deberías inicializar tus componentes si no usas GUI builder
    }

    private void configurarVentana() {
        setTitle("Datos del Producto");
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setSize(500, 500);
        setVisible(true);
    }

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("producto.anadir.titulo"));
        lblCodigo.setText(mensajeHandler.get("producto.anadir.lbl.codigo"));
        lblNombre.setText(mensajeHandler.get("producto.anadir.lbl.nombre"));
        lblPrecio.setText(mensajeHandler.get("producto.anadir.lbl.precio"));
        btnAceptar.setText(mensajeHandler.get("producto.anadir.btn.aceptar"));
        btnLimpiar.setText(mensajeHandler.get("producto.anadir.btn.limpiar"));
        if (panelPrincipal.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
            border.setTitle(mensajeHandler.get("producto.anadir.panel.titulo"));
            panelPrincipal.repaint();
        }
    }

    public void mostrarProducto(String codigo, String nombre, double precio) {
        txtCodigo.setText(codigo);
        txtNombre.setText(nombre);
        String precioFormateado = FormateadorUtils.formatearMoneda(precio, mensajeHandler.getLocale());
        txtPrecio.setText(precioFormateado);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
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
                txtPrecio.setText(FormateadorUtils.formatearMoneda(valor, nuevoHandler.getLocale()));
            } catch (Exception e) {
                txtPrecio.setText("");
            }
        }
    }
    // En ProductoAnadirView.java



    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBtnAceptar() { return btnAceptar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }



    public void setIconos(){

        setIconoEscalado(btnAceptar, "/md-done-all.png", 20, 20);
        setIconoEscalado(btnLimpiar, "/ios-trash.png", 20, 20);
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
