package ec.edu.ups.vista;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class ProductoListaView extends JInternalFrame {

    private JPanel panelPrincipal;
    private ProductoDAO productoDAO;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JButton btnListar;
    private JLabel lblNombre;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajes;

    public ProductoListaView(MensajeInternacionalizacionHandler mensajeHandler, ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
        this.mensajes = mensajeHandler;
        setContentPane(panelPrincipal);
        initComponents();
        configurarVentana();
        setTextos(mensajeHandler);
        setIconos();
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

    public void cargarDatos(List<Producto> listaProductos, Locale locale) {
        modelo.setRowCount(0);
        for (Producto p : listaProductos) {
            modelo.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    FormateadorUtils.formatearMonedaConSimbolo(p.getPrecio(), locale)
            });
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void actualizarDatos(MensajeInternacionalizacionHandler mensajeHandler) {
        List<Producto> productos = productoDAO.listarTodos();
        cargarDatos(productos, mensajeHandler.getLocale());
    }

    public JTextField getTxtBuscar() { return txtBuscar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnListar() { return btnListar; }
    public JTable getTblProductos() { return tblProductos; }
    public DefaultTableModel getModelo() { return modelo; }



    public void setIconos(){

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
