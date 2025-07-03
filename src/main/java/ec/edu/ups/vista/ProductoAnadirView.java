package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public ProductoAnadirView(MensajeInternacionalizacionHandler mensajeHandler) {
        initComponents(); // Inicializar componentes primero
        setTextos(mensajeHandler); // Después ya puedes cambiar sus textos
        configurarVentana();
    }

    private void initComponents() {

        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void configurarVentana() {
        setContentPane(panelPrincipal);
        setTitle("Datos del Producto");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
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

    // Getters para el controlador
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBtnAceptar() { return btnAceptar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
}
