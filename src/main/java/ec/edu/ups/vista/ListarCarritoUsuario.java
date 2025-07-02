package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ListarCarritoUsuario extends JInternalFrame{
    private JScrollPane ScrollPane;
    private JTable tblCarrito;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JLabel lblCodigo;
    private JButton btnListar;
    private JLabel lblConsejo;
    private JPanel panelPrincipal;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private DefaultTableModel modelo;

    public ListarCarritoUsuario(MensajeInternacionalizacionHandler mensajeHandler){
        this.mensajeHandler = mensajeHandler;
        inicializarComponentes(); // primero inicializa el modelo y tabla
        this.setTitle(mensajeHandler.get("panel.carrito.listar"));
        setTextos(mensajeHandler); // luego cambia textos internacionales

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 400);
        setLocation(320, 0);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel();
        tblCarrito.setModel(modelo);

        // Puedes inicializar cabeceras básicas aquí si quieres
        modelo.setColumnIdentifiers(new Object[]{
                "Usuario",
                "Código",
                "Fecha",
                "Total"
        });
    }

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("carrito.listar.titulo"));
        lblCodigo.setText(mensajeHandler.get("carrito.listar.lbl.codigo"));
        lblConsejo.setText(mensajeHandler.get("carrito.listar.lbl.consejo"));
        btnBuscar.setText(mensajeHandler.get("carrito.listar.btn.buscar"));
        btnListar.setText(mensajeHandler.get("carrito.listar.btn.listar"));

        // Ahora modelo nunca es null aquí
        modelo.setColumnIdentifiers(new Object[]{
                mensajeHandler.get("carrito.listar.col.usuario"),
                mensajeHandler.get("carrito.listar.col.codigo"),
                mensajeHandler.get("carrito.listar.col.fecha"),
                mensajeHandler.get("carrito.listar.col.total")
        });
    }

    public void cargarDatos(List<Carrito> listaCarritos) {
        modelo.setNumRows(0);
        Locale locale = mensajeHandler.getLocale();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (Carrito carrito : listaCarritos) {
            String fechaFormateada = sdf.format(carrito.getFechaCreacion().getTime());
            String nombreUsuario = carrito.getUsuario() != null ? carrito.getUsuario().toString() :
                    mensajeHandler.get("carrito.listar.desconocido");
            String totalFormateado = FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale);

            modelo.addRow(new Object[]{
                    nombreUsuario,
                    carrito.getCodigo(),
                    fechaFormateada,
                    totalFormateado
            });
        }
    }

    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Getters
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
        return ScrollPane;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public JLabel getLblConsejo() {
        return lblConsejo;
    }
}




