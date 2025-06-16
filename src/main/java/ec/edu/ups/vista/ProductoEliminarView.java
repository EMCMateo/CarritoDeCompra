package ec.edu.ups.vista;

import javax.swing.*;

public class ProductoEliminarView extends JInternalFrame{
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

    // Métodos públicos para acceder a los componentes
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnEliminar() { return btnEliminar; }

    public void mostrarMensajeEliminar(String message) {
        JOptionPane.showConfirmDialog(this, message);

    }
    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);

    }
    public void limpiarCampos(){
        txtCodigo.setText("");
    }
}


