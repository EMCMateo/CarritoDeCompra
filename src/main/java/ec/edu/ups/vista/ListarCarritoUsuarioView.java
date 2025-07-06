package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.text.NumberFormat;
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

        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setSize(1000, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);
        setTextos(mh);
        setIconos();
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel();
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
            border.setTitle(mh.get("carritousuario.listar.panel.titulo"));
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
                    sdf.format(c.getFechaCreacion().getTime()),  // ¡corregido!
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

    public void cargarDatosConFormato(List<Carrito> carritos, MensajeInternacionalizacionHandler handler) {
        modelo.setRowCount(0);
        Locale locale = handler.getLocale();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        SimpleDateFormat dateFormat = new SimpleDateFormat(handler.get("formato.fecha"));

        for (Carrito c : carritos) {
            Object[] fila = new Object[]{
                    c.getUsuario() != null ? c.getUsuario().getUsername() : handler.get("carrito.listar.desconocido"),
                    c.getCodigo(),
                    dateFormat.format(c.getFechaCreacion().getTime()), // ¡corregido!
                    currencyFormat.format(c.calcularTotal())
            };
            modelo.addRow(fila);
        }
    }

    public void actualizarMensajeHandler(MensajeInternacionalizacionHandler nuevoHandler) {
        this.mensajeHandler = nuevoHandler;
    }



    public void setIconos() {
        setIconoEscalado(btnBuscar, "/ios-search.png", 20, 20);
        setIconoEscalado(btnListar, "/md-paper.png", 20, 20);

    }
    private void setIconoEscalado(JButton boton, String ruta, int ancho, int alto) {
        URL url = getClass().getResource(ruta);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
        }
    }
}
