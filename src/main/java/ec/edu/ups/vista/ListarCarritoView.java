package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;

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
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Codigo", "Fecha"};
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
            // Obtener la fecha como Date desde GregorianCalendar
            String fechaFormateada = sdf.format(carrito.getFechaCreacion().getTime());

            Object[] fila = {
                    carrito.getCodigo(),
                    fechaFormateada,
                    carrito.calcularTotal()
            };
            modelo.addRow(fila);
        }
    }
}




