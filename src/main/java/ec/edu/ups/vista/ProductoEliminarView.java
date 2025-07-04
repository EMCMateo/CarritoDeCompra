package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

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

    }

    private void initComponents() {

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

    public int mostrarConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION);
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
