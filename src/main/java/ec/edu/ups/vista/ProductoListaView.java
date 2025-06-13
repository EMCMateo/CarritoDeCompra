package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductoListaView extends JFrame {
    private JButton btnBuscar;
    private JTable tblProductos;
    private JTextField txtBuscar;
    private JPanel panelPrincipal;
    private JLabel lblBuscar;

    public ProductoListaView (){
        setContentPane(panelPrincipal);
        setTitle("Listado de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        //pack();


    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setButton1(JButton button1) {
        this.btnBuscar = button1;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTextField1(JTextField textField1) {
        this.txtBuscar = textField1;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }
    public void mostrarProductos(List<Producto> productoList){
        DefaultTableModel modelo = new DefaultTableModel();
        Object [] columnas = {"id","Nombre","Precio"};
        modelo.setColumnIdentifiers(columnas);
        for (Producto producto : productoList){
            Object [] datosProducto = {producto.getCodigo(), producto.getNombre(), producto.getPrecio()};
            modelo.addRow(datosProducto);
        }
        tblProductos.setModel(modelo);

    }
}
