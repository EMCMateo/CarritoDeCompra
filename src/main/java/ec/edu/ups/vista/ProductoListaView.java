package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductoListaView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JButton btnListar;
    private JLabel lblNombre;
    private DefaultTableModel modelo;

    public ProductoListaView(MensajeInternacionalizacionHandler mensajeHandler) {
        setContentPane(panelPrincipal);
        initComponents();
        configurarVentana();
        setTextos(mensajeHandler);

    }

    private void initComponents() {

        tblProductos = new JTable();
        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        JScrollPane scrollPane = new JScrollPane(tblProductos);


        modelo.setColumnIdentifiers(new String[]{"Código", "Nombre", "Precio"});
    }

    private void configurarVentana() {
        setTitle("Lista de Productos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
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

        modelo.setColumnIdentifiers(new String[]{
                mensajeHandler.get("producto.tabla.codigo"),
                mensajeHandler.get("producto.tabla.nombre"),
                mensajeHandler.get("producto.tabla.precio")
        });
    }

    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setRowCount(0);
        for (Producto p : listaProductos) {
            modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()});
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
