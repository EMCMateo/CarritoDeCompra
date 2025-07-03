package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

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

    public ProductoActualizarView(MensajeInternacionalizacionHandler mensajeHandler) {
        initComponents();
        setTextos(mensajeHandler);
        configurarVentana();
    }

    private void initComponents() {

    }

    private void configurarVentana() {
        setContentPane(panelPrincipal);
        setTitle("Actualizar Producto");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setSize(400, 200);
        setVisible(true);
    }

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("producto.actualizar.titulo"));
        lblCodigo.setText(mensajeHandler.get("producto.actualizar.lbl.codigo"));
        lblNombre.setText(mensajeHandler.get("producto.actualizar.lbl.nombre"));
        lblPrecio.setText(mensajeHandler.get("producto.actualizar.lbl.precio"));
        btnBuscar.setText(mensajeHandler.get("producto.actualizar.btn.buscar"));
        btnActualizar.setText(mensajeHandler.get("producto.actualizar.btn.actualizar"));
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
}
