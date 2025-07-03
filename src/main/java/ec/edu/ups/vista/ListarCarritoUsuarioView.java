package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ListarCarritoUsuarioView extends JInternalFrame {
    private JScrollPane scrollPane;
    private JTable tblCarrito;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JLabel lblCodigo;
    private JButton btnListar;
    private JLabel lblConsejo;
    private JPanel panelPrincipal;
    private DefaultTableModel modelo;
    private JScrollPane ScrollPane;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public ListarCarritoUsuarioView(MensajeInternacionalizacionHandler mh) {
        this.mensajeHandler = mh;
        setContentPane(panelPrincipal);
        inicializarComponentes();


        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);
        setTextos(mh);



    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel();
        tblCarrito.setModel(modelo);
        modelo.setColumnIdentifiers(new Object[]{"Usuario", "Código", "Fecha", "Total"});
    }

    public void setTextos(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("carrito.listar.titulo"));
        lblCodigo.setText(mh.get("carrito.listar.lbl.codigo"));
        lblConsejo.setText(mh.get("carrito.listar.lbl.consejo"));
        btnBuscar.setText(mh.get("carrito.listar.btn.buscar"));
        btnListar.setText(mh.get("carrito.listar.btn.listar"));

        modelo.setColumnIdentifiers(new Object[]{
                mh.get("carrito.listar.col.usuario"),
                mh.get("carrito.listar.col.codigo"),
                mh.get("carrito.listar.col.fecha"),
                mh.get("carrito.listar.col.total")
        });
    }

    public void cargarDatos(List<Carrito> lista) {
        modelo.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Locale locale = mensajeHandler.getLocale();
        for (Carrito c : lista) {
            modelo.addRow(new Object[]{
                    c.getUsuario() != null ? c.getUsuario().getUsername() : mensajeHandler.get("carrito.listar.desconocido"),
                    c.getCodigo(),
                    sdf.format(c.getFechaCreacion().getTime()),
                    FormateadorUtils.formatearMoneda(c.calcularTotal(), locale)
            });
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JTable getTblCarrito() {
        return tblCarrito;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JLabel getLblConsejo() {
        return lblConsejo;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void cargarDatosConFormato(List<Carrito> lista, MensajeInternacionalizacionHandler mh) {
        modelo.setRowCount(0);
        Locale locale = mh.getLocale();
        for (Carrito c : lista) {
            String usuario = c.getUsuario() != null ? c.getUsuario().getUsername() : mh.get("carrito.listar.desconocido");
            String fechaFormateada = FormateadorUtils.formatearFecha(c.getFechaCreacion().getTime(), locale);
            String totalFormateado = FormateadorUtils.formatearMoneda(c.calcularTotal(), locale);
            modelo.addRow(new Object[]{usuario, c.getCodigo(), fechaFormateada, totalFormateado});
        }
    }

    /**
     * Actualiza el mensajeHandler en esta vista.
     */
    public void actualizarMensajeHandler(MensajeInternacionalizacionHandler nuevoHandler) {
        this.mensajeHandler = nuevoHandler;
    }
}
