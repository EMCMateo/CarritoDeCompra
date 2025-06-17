package ec.edu.ups.vista;

import javax.swing.*;

public class ProductoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JLabel lblCodigo;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnEliminar;

    public ProductoEliminarView() {
        setContentPane(panelPrincipal);
        setTitle("Eliminar Producto");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setIconifiable(true);
        setClosable(true);
        setVisible(true);
        pack();
    }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnEliminar() { return btnEliminar; }

    public int mostrarConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    }

    // Muestra un mensaje simple
    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
    }
}
