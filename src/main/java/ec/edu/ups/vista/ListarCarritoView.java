package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ListarCarritoView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarrito;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnListar;
    private JScrollPane scrollPane;
    private JLabel lblCodigo;
    private JLabel lblConsejo;
    private JScrollPane ScrollPane;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public ListarCarritoView(MensajeInternacionalizacionHandler mh) {
        this.mensajeHandler = mh;
        inicializarComponentes();


        setContentPane(panelPrincipal);
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
        modelo.setColumnIdentifiers(new Object[]{"Código", "Fecha", "Total"});
    }

    public void setTextos(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("carrito.listar.titulo"));
        lblCodigo.setText(mh.get("carrito.listar.lbl.codigo"));
        lblConsejo.setText(mh.get("carrito.listar.lbl.consejo"));
        btnBuscar.setText(mh.get("carrito.listar.btn.buscar"));
        btnListar.setText(mh.get("carrito.listar.btn.listar"));

        modelo.setColumnIdentifiers(new Object[]{
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

    public JButton getBtnListar() {
        return btnListar;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public JLabel getLblConsejo() {
        return lblConsejo;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }
    /**
     * Recarga la tabla con una lista y actualiza formatos con el Locale del mensajeHandler.
     */
    public void cargarDatosConFormato(List<Carrito> lista, MensajeInternacionalizacionHandler mh) {
        modelo.setRowCount(0);
        Locale locale = mh.getLocale();
        for (Carrito c : lista) {
            String fechaFormateada = FormateadorUtils.formatearFecha(c.getFechaCreacion().getTime(), locale);
            String totalFormateado = FormateadorUtils.formatearMoneda(c.calcularTotal(), locale);
            modelo.addRow(new Object[]{c.getCodigo(), fechaFormateada, totalFormateado});
        }
    }

    /**
     * Actualiza el mensajeHandler en esta vista.
     */
    public void actualizarMensajeHandler(MensajeInternacionalizacionHandler nuevoHandler) {
        this.mensajeHandler = nuevoHandler;
    }

}
