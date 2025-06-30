package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class ProductoActualizarView extends JInternalFrame {
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

    private MensajeInternacionalizacionHandler mensajeHandler;

    public ProductoActualizarView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;


        setContentPane(panelPrincipal);
        setTitle(mensajeHandler.get("producto.actualizar.titulo"));
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setClosable(true);
        setIconifiable(true);
        setVisible(true);
        pack();

        setTextos(mensajeHandler);
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

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("producto.actualizar.titulo"));
        lblCodigo.setText(mensajeHandler.get("producto.actualizar.lbl.codigo"));
        lblNombre.setText(mensajeHandler.get("producto.actualizar.lbl.nombre"));
        lblPrecio.setText(mensajeHandler.get("producto.actualizar.lbl.precio"));

        btnBuscar.setText(mensajeHandler.get("producto.actualizar.btn.buscar"));
        btnActualizar.setText(mensajeHandler.get("producto.actualizar.btn.actualizar"));
    }
}
