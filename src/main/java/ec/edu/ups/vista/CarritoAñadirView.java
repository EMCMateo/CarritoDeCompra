package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

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
    private JTextField txtCodigoCarrito;
    private JTextField txtFecha;
    private JButton btnBorrar;
    private JButton btnGuardar;
    private JButton btnEliminarProductoC;
    private JButton btnActualizarProductoC;
    private DefaultTableModel modelo;

    public CarritoAñadirView(){
        super("Datos del Carrito", true , true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocation(200 , 125);
        setVisible(true);
        cargarDatos();
        if (txtNombre.getText() == "" && txtPrecio.getText() == ""){
            btnAnadir.setEnabled(false);
        }
        txtCodigoCarrito.setEditable(false);
        txtFecha.setEditable(false);

        //pack();

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] columnas = {"Codigo", "Nombre", "Precio", "Cantidad", "SubTotal"};
        modelo.setColumnIdentifiers(columnas);
        tblCarrito.setModel(modelo);

        /*
        cargarProductos();
        */

        btnEliminarProductoC = new JButton("Eliminar");
        btnActualizarProductoC = new JButton("Editar");





    }
    public void cargarDatos(){
        cmBoxCantidad.removeAllItems();
        for(int i = 0; i<20;i++){
            cmBoxCantidad.addItem(String.valueOf(i+1));
        }
    }

    public void cargarProductos(){
        cmBoxCantidad.removeAllItems();
        for(int i = 0; i<20;i++){
            cmBoxCantidad.addItem(String.valueOf(i+1));
        }
    }


    public void cargarDatosTabla(List<ItemCarrito> items) {
        modelo.setRowCount(0); // Borra la tabla
        for (ItemCarrito item : items) {
            Object[] fila = {
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    String.format("%.2f", item.getProducto().getPrecio() * item.getCantidad()),

            };
            modelo.addRow(fila);
        }


    }

    public void setDatosCarrito(Carrito carrito) {
        txtCodigoCarrito.setText(String.valueOf(carrito.getCodigo()));

        // También podrías poner la fecha si lo deseas:
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        txtFecha.setText(sdf.format(carrito.getFechaCreacion().getTime()));
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

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public void setTxtFecha(JTextField txtFecha) {
        this.txtFecha = txtFecha;
    }

    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JButton getBtnBorrar() {
        return btnBorrar;
    }

    public void setBtnBorrar(JButton btnBorrar) {
        this.btnBorrar = btnBorrar;
    }

    public void setTxtCodigoCarrito(JTextField txtCodigoCarrito) {
        this.txtCodigoCarrito = txtCodigoCarrito;
    }

    public JButton getBtnEliminarProductoC() {
        return btnEliminarProductoC;
    }

    public void setBtnEliminarProductoC(JButton btnEliminarProductoC) {
        this.btnEliminarProductoC = btnEliminarProductoC;
    }

    public JButton getBtnActualizarProductoC() {
        return btnActualizarProductoC;
    }

    public void setBtnActualizarProductoC(JButton btnActualizarProductoC) {
        this.btnActualizarProductoC = btnActualizarProductoC;
    }
}


