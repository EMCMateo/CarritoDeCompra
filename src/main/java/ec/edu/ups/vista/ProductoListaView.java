package ec.edu.ups.vista;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

public class ProductoListaView extends JInternalFrame {

    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JPanel panelPrincipal;
    private JButton btnListar;
    private JLabel lblNombre;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    public ProductoListaView(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;

        inicializarComponentes(); // Inicializa componentes y modelo
        this.setTitle(mensajeInternacionalizacionHandler.get("panel.producto.lista"));
        setTextos(mensajeInternacionalizacionHandler);

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);
    }

    private void inicializarComponentes() {


        // Inicializa el modelo y asigna a la tabla
        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);


        String[] columnas = {"Codigo", "Nombre", "Precio"};
        modelo.setColumnIdentifiers(columnas);
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setNumRows(0);
        for (Producto producto : listaProductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
            };
            modelo.addRow(fila);
        }
    }

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("producto.lista.titulo"));
        btnBuscar.setText(mensajeHandler.get("producto.lista.btn.buscar"));
        btnListar.setText(mensajeHandler.get("producto.lista.btn.listar"));
        lblNombre.setText(mensajeHandler.get("producto.lista.lbl.nombre"));

        String[] columnas = {
                mensajeHandler.get("producto.tabla.codigo"),
                mensajeHandler.get("producto.tabla.nombre"),
                mensajeHandler.get("producto.tabla.precio")
        };
        modelo.setColumnIdentifiers(columnas);
    }
}
