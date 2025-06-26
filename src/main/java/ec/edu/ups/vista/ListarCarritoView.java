package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListarCarritoView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarrito;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnListar;
    private JScrollPane ScrollPane;
    private DefaultTableModel modelo;

    public ListarCarritoView() {

        setContentPane(panelPrincipal);
        setTitle("Listado de Carritos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 400);
        setLocation(320 , 0);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Codigo", "Fecha",  "Total", "Productos"};
        modelo.setColumnIdentifiers(columnas);
        tblCarrito.setModel(modelo);
        modelo.setNumRows(0);




    }

    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTable getTblCarrito() {
        return tblCarrito;
    }

    public void setTblCarrito(JTable tblCarrito) {
        this.tblCarrito = tblCarrito;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public void cargarDatos(List<Carrito> listaCarritos) {
        modelo.setNumRows(0);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (Carrito carrito : listaCarritos) {
            String fechaFormateada = sdf.format(carrito.getFechaCreacion().getTime());

            StringBuilder productosTexto = new StringBuilder();
            for (ItemCarrito item : carrito.obtenerItems()) {
                productosTexto.append(item.getProducto().getNombre())
                        .append(" x").append(item.getCantidad())
                        .append(", ");
            }

            if (productosTexto.length() > 0) {
                productosTexto.setLength(productosTexto.length() - 2); // quitar la última coma
            }

            Object[] fila = {
                    carrito.getCodigo(),
                    fechaFormateada,
                    String.format("%.2f", carrito.calcularTotal()),
                    productosTexto.toString()
            };

            modelo.addRow(fila);
        }
    }

}




