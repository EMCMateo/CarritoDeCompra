package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;

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
    private JComboBox<String> cmBoxCantidad;
    private JTextField txtCodigoCarrito;
    private JTextField txtFecha;
    private JButton btnBorrar;
    private JButton btnGuardar;
    private JLabel lblCodigoCarrito;
    private JLabel lblFecha;
    private JLabel lblConsejo;
    private JLabel lblCodigo;
    private JButton btnEliminarProductoC;
    private JButton btnActualizarProductoC;
    private DefaultTableModel modelo;

    private MensajeInternacionalizacionHandler mensajeHandler;

    public CarritoAñadirView(MensajeInternacionalizacionHandler mensajeHandler){
        super("Datos del Carrito", true , true, false, true);
        this.mensajeHandler = mensajeHandler;


        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocation(200 , 125);
        setVisible(true);
        cargarDatos();

        if (txtNombre.getText().isEmpty() && txtPrecio.getText().isEmpty()){
            btnAnadir.setEnabled(false);
        }
        txtCodigoCarrito.setEditable(false);
        txtFecha.setEditable(false);

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] columnas = {
                mensajeHandler.get("carrito.anadir.tbl.codigo"),
                mensajeHandler.get("carrito.anadir.tbl.nombre"),
                mensajeHandler.get("carrito.anadir.tbl.precio"),
                mensajeHandler.get("carrito.anadir.tbl.cantidad"),
                mensajeHandler.get("carrito.anadir.tbl.subtotal")
        };
        modelo.setColumnIdentifiers(columnas);
        tblCarrito.setModel(modelo);

        btnEliminarProductoC = new JButton(mensajeHandler.get("carrito.anadir.btn.eliminar"));
        btnActualizarProductoC = new JButton(mensajeHandler.get("carrito.anadir.btn.actualizar"));

        setTextos(mensajeHandler);
    }

    public void cargarDatos(){
        cmBoxCantidad.removeAllItems();
        for(int i = 1; i <= 20; i++){
            cmBoxCantidad.addItem(String.valueOf(i));
        }
    }

    public void cargarDatosTabla(List<ItemCarrito> items) {
        modelo.setRowCount(0); // Borra la tabla
        Locale locale = mensajeHandler.getLocale();

        for (ItemCarrito item : items) {
            Object[] fila = {
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio() * item.getCantidad(), locale)
            };
            modelo.addRow(fila);
        }
    }

    public void setDatosCarrito(Carrito carrito) {
        txtCodigoCarrito.setText(String.valueOf(carrito.getCodigo()));
        txtFecha.setText(FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), mensajeHandler.getLocale()));
    }

    public void limpiarCampos(){
        txtNombre.setText("");
        txtCodigo.setText("");
        txtPrecio.setText("");
    }

    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Getters y setters (omito para brevedad, copia los tuyos)

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("carrito.anadir.titulo"));

        lblCodigo.setText(mensajeHandler.get("carrito.anadir.lbl.codigo"));
        lblNombre.setText(mensajeHandler.get("carrito.anadir.lbl.nombre"));
        lblPrecio.setText(mensajeHandler.get("carrito.anadir.lbl.precio"));
        lblCantidad.setText(mensajeHandler.get("carrito.anadir.lbl.cantidad"));
        lblSubt.setText(mensajeHandler.get("carrito.anadir.lbl.subtotal"));
        lblIva.setText(mensajeHandler.get("carrito.anadir.lbl.iva"));
        lblTotal.setText(mensajeHandler.get("carrito.anadir.lbl.total"));
        lblCodigoCarrito.setText(mensajeHandler.get("carrito.anadir.lbl.codigocarrito"));
        lblFecha.setText(mensajeHandler.get("carrito.anadir.lbl.fecha"));
        lblConsejo.setText(mensajeHandler.get("carrito.anadir.lbl.consejo"));

        btnBuscar.setText(mensajeHandler.get("carrito.anadir.btn.buscar"));
        btnAnadir.setText(mensajeHandler.get("carrito.anadir.btn.anadir"));
        btnBorrar.setText(mensajeHandler.get("carrito.anadir.btn.borrar"));
        btnGuardar.setText(mensajeHandler.get("carrito.anadir.btn.guardar"));
        btnEliminarProductoC.setText(mensajeHandler.get("carrito.anadir.btn.eliminar"));
        btnActualizarProductoC.setText(mensajeHandler.get("carrito.anadir.btn.actualizar"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajeHandler.get("carrito.anadir.tbl.codigo"),
                mensajeHandler.get("carrito.anadir.tbl.nombre"),
                mensajeHandler.get("carrito.anadir.tbl.precio"),
                mensajeHandler.get("carrito.anadir.tbl.cantidad"),
                mensajeHandler.get("carrito.anadir.tbl.subtotal")
        });
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

    public void setBtnAnadir(JButton btnAnadir) {
        this.btnAnadir = btnAnadir;
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

    public JComboBox<String> getCmBoxCantidad() {
        return cmBoxCantidad;
    }

    public void setCmBoxCantidad(JComboBox<String> cmBoxCantidad) {
        this.cmBoxCantidad = cmBoxCantidad;
    }

    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }

    public void setTxtCodigoCarrito(JTextField txtCodigoCarrito) {
        this.txtCodigoCarrito = txtCodigoCarrito;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public void setTxtFecha(JTextField txtFecha) {
        this.txtFecha = txtFecha;
    }

    public JButton getBtnBorrar() {
        return btnBorrar;
    }

    public void setBtnBorrar(JButton btnBorrar) {
        this.btnBorrar = btnBorrar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JLabel getLblCodigoCarrito() {
        return lblCodigoCarrito;
    }

    public void setLblCodigoCarrito(JLabel lblCodigoCarrito) {
        this.lblCodigoCarrito = lblCodigoCarrito;
    }

    public JLabel getLblFecha() {
        return lblFecha;
    }

    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
    }

    public JLabel getLblConsejo() {
        return lblConsejo;
    }

    public void setLblConsejo(JLabel lblConsejo) {
        this.lblConsejo = lblConsejo;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
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

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }

    public void setMensajeHandler(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
    }
}
