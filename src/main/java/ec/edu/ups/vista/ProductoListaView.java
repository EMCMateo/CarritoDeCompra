package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class ProductoListaView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JButton btnListar;
    private JLabel lblNombre;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajes;

    public ProductoListaView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajes = mensajeHandler;
        setContentPane(panelPrincipal);
        initComponents();
        configurarVentana();
        setTextos(mensajeHandler);
    }

    private void initComponents() {
        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Código", "Nombre", "Precio"});
    }

    private void configurarVentana() {
        setTitle("Lista de Productos");
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setSize(600, 400);
        setVisible(true);
    }

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("producto.lista.titulo"));
        lblNombre.setText(mensajeHandler.get("producto.lista.lbl.nombre"));
        btnBuscar.setText(mensajeHandler.get("producto.lista.btn.buscar"));
        btnListar.setText(mensajeHandler.get("producto.lista.btn.listar"));
        if (panelPrincipal.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
            border.setTitle(mensajeHandler.get("producto.listar.panel.titulo"));
            panelPrincipal.repaint();
        }

        modelo.setColumnIdentifiers(new String[]{
                mensajeHandler.get("producto.tabla.codigo"),
                mensajeHandler.get("producto.tabla.nombre"),
                mensajeHandler.get("producto.tabla.precio")
        });
    }

    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setRowCount(0);
        Locale locale = mensajes.getLocale();

        for (Producto p : listaProductos) {
            String precioFormateado = FormateadorUtils.formatearMoneda(p.getPrecio(), locale);
            modelo.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    precioFormateado
            });
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JTextField getTxtBuscar() { return txtBuscar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnListar() { return btnListar; }
    public JTable getTblProductos() { return tblProductos; }
    public DefaultTableModel getModelo() { return modelo; }
}
