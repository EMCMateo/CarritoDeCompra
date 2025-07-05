package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ListarCarritoView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarrito;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnListar;
    private JScrollPane ScrollPane;
    private JLabel lblCodigo;
    private JLabel lblConsejo;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public ListarCarritoView(MensajeInternacionalizacionHandler mh) {
        this.mensajeHandler = mh;
        setContentPane(panelPrincipal);
        inicializarComponentes();

        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setSize(1000, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);
        setTextos(mh);
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblCarrito.setModel(modelo);
        modelo.setColumnIdentifiers(new Object[]{
                "Usuario",
                "Código",
                "Fecha",
                "Total"
        });
    }

    public void setTextos(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("carrito.listar.titulo"));
        lblCodigo.setText(mh.get("carrito.listar.lbl.codigo"));
        lblConsejo.setText(mh.get("carrito.listar.lbl.consejo"));
        btnBuscar.setText(mh.get("carrito.listar.btn.buscar"));
        btnListar.setText(mh.get("carrito.listar.btn.listar"));

        if (panelPrincipal.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
            border.setTitle(mh.get("carrito.listar.panel.titulo"));
            panelPrincipal.repaint();
        }

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

    public void cargarDatosConFormato(List<Carrito> carritos, MensajeInternacionalizacionHandler handler) {
        modelo.setRowCount(0);
        Locale locale = handler.getLocale();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        SimpleDateFormat dateFormat = new SimpleDateFormat(handler.get("formato.fecha"));

        for (Carrito c : carritos) {
            Object[] fila = new Object[]{
                    c.getUsuario() != null ? c.getUsuario().getUsername() : handler.get("carrito.listar.desconocido"),
                    c.getCodigo(),
                    dateFormat.format(c.getFechaCreacion().getTime()),
                    currencyFormat.format(c.calcularTotal())
            };
            modelo.addRow(fila);
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

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public JLabel getLblConsejo() {
        return lblConsejo;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void actualizarMensajeHandler(MensajeInternacionalizacionHandler nuevoHandler) {
        this.mensajeHandler = nuevoHandler;
    }
}
