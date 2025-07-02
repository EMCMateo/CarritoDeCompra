package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
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
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    public ProductoAnadirView(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        this.setTitle(mensajeInternacionalizacionHandler.get("panel.producto.anadir"));
        setTextos(mensajeInternacionalizacionHandler);
        setContentPane(panelPrincipal);
        setTitle("Datos del Producto");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setSize(500, 400);
        setResizable(true);
        setVisible(true);
        //pack();

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }
    public void setTextos(ec.edu.ups.util.MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("producto.anadir.titulo"));
        lblCodigo.setText(mensajeHandler.get("producto.anadir.lbl.codigo"));
        lblNombre.setText(mensajeHandler.get("producto.anadir.lbl.nombre"));
        lblPrecio.setText(mensajeHandler.get("producto.anadir.lbl.precio"));
        btnAceptar.setText(mensajeHandler.get("producto.anadir.btn.aceptar"));
        btnLimpiar.setText(mensajeHandler.get("producto.anadir.btn.limpiar"));
    }

}
