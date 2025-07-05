package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;

public class CarritoEditarView extends JInternalFrame {
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
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public CarritoEditarView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        inicializarComponentes(); // si usas GUI Builder
        setClosable(true);
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setSize(600, 400); // si no usas pack()
        setTextos(mensajeHandler); // si tienes texto internacionalizable


    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblCarrito.setModel(modelo);
        cargarDatos();
        txtCodigoCarrito.setEditable(false);
        txtFecha.setEditable(false);
        btnAnadir.setEnabled(false);
    }

    public void cargarDatos() {
        cmBoxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cmBoxCantidad.addItem(String.valueOf(i));
        }
    }

    public void cargarDatosTabla(List<ItemCarrito> items) {
        modelo.setRowCount(0);
        if (items == null) return;

        Locale locale = mensajeHandler.getLocale();
        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio() * item.getCantidad(), locale)
            });
        }
    }


    public void setDatosCarrito(Carrito carrito) {
        txtCodigoCarrito.setText(String.valueOf(carrito.getCodigo()));
        txtFecha.setText(FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), mensajeHandler.getLocale()));
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void setTextos(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("carrito.editar.titulo"));
        lblCodigo.setText(mh.get("carrito.anadir.lbl.codigo"));
        lblNombre.setText(mh.get("carrito.anadir.lbl.nombre"));
        lblPrecio.setText(mh.get("carrito.anadir.lbl.precio"));
        lblCantidad.setText(mh.get("carrito.anadir.lbl.cantidad"));
        lblSubt.setText(mh.get("carrito.anadir.lbl.subtotal"));
        lblIva.setText(mh.get("carrito.anadir.lbl.iva"));
        lblTotal.setText(mh.get("carrito.anadir.lbl.total"));
        lblCodigoCarrito.setText(mh.get("carrito.anadir.lbl.codigocarrito"));
        lblFecha.setText(mh.get("carrito.anadir.lbl.fecha"));
        lblConsejo.setText(mh.get("carrito.anadir.lbl.consejo"));

        btnBuscar.setText(mh.get("carrito.anadir.btn.buscar"));
        btnAnadir.setText(mh.get("carrito.anadir.btn.anadir"));
        btnBorrar.setText(mh.get("carrito.anadir.btn.borrar"));
        btnGuardar.setText(mh.get("carrito.anadir.btn.guardar"));

        if (panelPrincipal.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
            border.setTitle(mensajeHandler.get("carrito.editar.titulo"));
            panelPrincipal.repaint();
        }

        modelo.setColumnIdentifiers(new Object[]{
                mh.get("carrito.anadir.tbl.codigo"),
                mh.get("carrito.anadir.tbl.nombre"),
                mh.get("carrito.anadir.tbl.precio"),
                mh.get("carrito.anadir.tbl.cantidad"),
                mh.get("carrito.anadir.tbl.subtotal")
        });
    }


    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public JTextField getTxtIVA() {
        return txtIVA;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public JTable getTblCarrito() {
        return tblCarrito;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JLabel getLblCantidad() {
        return lblCantidad;
    }

    public JLabel getLblSubt() {
        return lblSubt;
    }

    public JLabel getLblIva() {
        return lblIva;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JComboBox<String> getCmBoxCantidad() {
        return cmBoxCantidad;
    }

    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public JButton getBtnBorrar() {
        return btnBorrar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JLabel getLblCodigoCarrito() {
        return lblCodigoCarrito;
    }

    public JLabel getLblFecha() {
        return lblFecha;
    }

    public JLabel getLblConsejo() {
        return lblConsejo;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }


}
