package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CarritoAñadirView extends JInternalFrame {
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JButton btnAnadir;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JTable tblCarrito;
    private JTextField txtCantidad;
    private JLabel lblCantidad;
    private JLabel lblSubt;
    private JLabel lblIva;
    private JLabel lblTotal;
    private JPanel panelPrincipal;
    private JComboBox cmBoxCantidad;
    private DefaultTableModel modelo;

    public CarritoAñadirView(){
        super("Datos del Carrito", true , true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setVisible(true);
        cargarDatos();
        //pack();

        modelo = new DefaultTableModel();
        Object[] columnas = {"Codigo", "Nombre", "Precio", "Cantidad"};
        modelo.setColumnIdentifiers(columnas);
        tblCarrito.setModel(modelo);


    }
    public void cargarDatos(){
        cmBoxCantidad.removeAllItems();
        for(int i = 0; i<20;i++){
            cmBoxCantidad.addItem(String.valueOf(i+1));
        }
    }

    public void cargarDatosTabla(Producto producto, int cantidad) {
        modelo.setNumRows(0);

            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    cantidad
            };
            modelo.addRow(fila);


    }

    public void limpiarCampos(){
        txtNombre.setText("");
        txtCodigo.setText("");
        txtPrecio.setText("");
    }
    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


    public JComboBox getCmBoxCantidad() {
        return cmBoxCantidad;
    }

    public void setCmBoxCantidad(JComboBox cmBoxCantidad) {
        this.cmBoxCantidad = cmBoxCantidad;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
    }

    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    public void setBtnAnadir(JButton añadirButton) {
        this.btnAnadir = añadirButton;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    public JTextField getTxtIVA() {
        return txtIVA;
    }

    public void setTxtIVA(JTextField txtIVA) {
        this.txtIVA = txtIVA;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    public JTable getTblCarrito() {
        return tblCarrito;
    }

    public void setTblCarrito(JTable tblCarrito) {
        this.tblCarrito = tblCarrito;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public void setTxtCantidad(JTextField txtCantidad) {
        this.txtCantidad = txtCantidad;
    }

    public JLabel getLblCantidad() {
        return lblCantidad;
    }

    public void setLblCantidad(JLabel lblCantidad) {
        this.lblCantidad = lblCantidad;
    }

    public JLabel getLblSubt() {
        return lblSubt;
    }

    public void setLblSubt(JLabel lblSubt) {
        this.lblSubt = lblSubt;
    }

    public JLabel getLblIva() {
        return lblIva;
    }

    public void setLblIva(JLabel lblIva) {
        this.lblIva = lblIva;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }


    public static void main(String [] args){
        new CarritoAñadirView();
    }
}
