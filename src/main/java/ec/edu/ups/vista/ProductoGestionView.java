package ec.edu.ups.vista;

import javax.swing.*;

public class ProductoGestionView extends JFrame {
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JPanel panelPrincipal;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;

    public ProductoGestionView() {
        setContentPane(panelPrincipal);
        setTitle("Gestion del Producto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        //pack();
    }

    // Métodos públicos para acceder a los componentes
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnEliminar() { return btnEliminar; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}
