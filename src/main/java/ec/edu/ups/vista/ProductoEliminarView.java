package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

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
        setTextos(mensajeHandler);

        setContentPane(panelPrincipal);
        setTitle(mensajeHandler.get("producto.eliminar.titulo"));
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setIconifiable(true);
        setClosable(true);
        setVisible(true);
        pack();

        setTextos(mensajeHandler);
    }

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnEliminar() { return btnEliminar; }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public int mostrarConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, mensajeHandler.get("producto.eliminar.confirmar"), JOptionPane.YES_NO_OPTION);
    }

    // Muestra un mensaje simple
    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("producto.eliminar.titulo"));
        btnBuscar.setText(mensajeHandler.get("producto.eliminar.btn.buscar"));
        btnEliminar.setText(mensajeHandler.get("producto.eliminar.btn.eliminar"));

        lblCodigo.setText(mensajeHandler.get("producto.eliminar.lbl.codigo"));
        lblNombre.setText(mensajeHandler.get("producto.eliminar.lbl.nombre"));
        lblPrecio.setText(mensajeHandler.get("producto.eliminar.lbl.precio"));
    }
}
