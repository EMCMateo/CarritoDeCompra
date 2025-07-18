package ec.edu.ups.vista;

import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;
/**
 * Vista para eliminar un producto.
 * Permite buscar un producto por código y eliminarlo del sistema.
 */

public class ProductoEliminarView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel lblCodigo;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Constructor de la vista ProductoEliminarView.
     * Inicializa los componentes y configura la ventana.
     *
     * @param mensajeHandler Manejador de mensajes para internacionalización.
     */
    public ProductoEliminarView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setContentPane(panelPrincipal);
        configurarVentana();
        setTextos(mensajeHandler);
        setIconos();
    }

    /**
     * Configura las propiedades de la ventana interna.
     * Establece título, tamaño, comportamiento de cierre y visibilidad.
     */
    private void configurarVentana() {
        setTitle(mensajeHandler.get("producto.eliminar.titulo"));
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setSize(400, 200);
        setVisible(true);
    }

    /**
     * Establece los textos de los componentes de la vista
     * usando el manejador de mensajes para internacionalización.
     *
     * @param mensajeHandler Manejador de mensajes para obtener los textos.
     */

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("producto.eliminar.titulo"));
        lblCodigo.setText(mensajeHandler.get("producto.eliminar.lbl.codigo"));
        lblNombre.setText(mensajeHandler.get("producto.eliminar.lbl.nombre"));
        lblPrecio.setText(mensajeHandler.get("producto.eliminar.lbl.precio"));
        btnBuscar.setText(mensajeHandler.get("producto.eliminar.btn.buscar"));
        btnEliminar.setText(mensajeHandler.get("producto.eliminar.btn.eliminar"));
        if (panelPrincipal.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
            border.setTitle(mensajeHandler.get("producto.eliminar.panel.titulo"));
            panelPrincipal.repaint();
        }
    }

    /**
     * Muestra los detalles del producto encontrado.
     * Actualiza los campos de nombre y precio con la información del producto.
     *
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     */

    public void mostrarProducto(String nombre, double precio) {
        txtNombre.setText(nombre);
        String precioFormateado = FormateadorUtils.formatearMoneda(precio, mensajeHandler.getLocale());
        txtPrecio.setText(precioFormateado);
    }

    /**
     * Establece los iconos de los botones de búsqueda y eliminación.
     * Los iconos se escalan a un tamaño específico.
     */

    public void setIconos(){

        setIconoEscalado(btnBuscar, "/ios-search.png", 20, 20);
        setIconoEscalado(btnEliminar, "/ios-trash.png", 20, 20);
    }

    /**
     * Establece un icono escalado para un botón.
     * Carga el icono desde la ruta especificada y lo escala al tamaño dado.
     *
     * @param boton Botón al que se le asignará el icono.
     * @param ruta Ruta del icono a cargar.
     * @param ancho Ancho deseado del icono.
     * @param alto Alto deseado del icono.
     */

    private void setIconoEscalado(JButton boton, String ruta, int ancho, int alto) {
        URL url = getClass().getResource(ruta);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
        }
    }

    /**
     * Actualiza la internacionalización de la vista.
     * Cambia el manejador de mensajes y actualiza los textos de los componentes.
     * Si ya hay un precio en pantalla, lo re-formatea según el nuevo locale.
     *
     * @param nuevoHandler Nuevo manejador de mensajes para internacionalización.
     */


    public void actualizarInternacionalizacion(MensajeInternacionalizacionHandler nuevoHandler) {
        this.mensajeHandler = nuevoHandler;
        setTextos(nuevoHandler);

        // Si ya hay datos en pantalla, re-formatear el precio
        String textoPrecio = txtPrecio.getText();
        if (!textoPrecio.isEmpty()) {
            try {
                double valor = FormateadorUtils.parsearMoneda(textoPrecio, nuevoHandler.getLocale());
                txtPrecio.setText(FormateadorUtils.formatearMonedaConSimbolo(valor, nuevoHandler.getLocale()));
            } catch (Exception e) {
                txtPrecio.setText("");
            }
        }
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * Utiliza el manejador de mensajes para mostrar el texto adecuado.
     *
     * @param mensaje Mensaje a mostrar en el cuadro de diálogo.
     */


    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }


    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnEliminar() { return btnEliminar; }


}
