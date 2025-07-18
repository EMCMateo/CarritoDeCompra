package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * Vista para añadir un producto.
 * Permite ingresar los datos del producto y limpiarlos.
 */

public class ProductoAnadirView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtPrecio;
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;

    private MensajeInternacionalizacionHandler mensajeHandler;
    /**
     * Constructor de la vista ProductoAnadirView.
     * Inicializa los componentes y configura la ventana.
     *
     * @param mensajeHandler Manejador de mensajes para internacionalización.
     */

    public ProductoAnadirView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setContentPane(panelPrincipal);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        setTextos(mensajeHandler);
        configurarVentana();
        setIconos();
    }





    private void configurarVentana() {
        setTitle("Datos del Producto");
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setSize(500, 500);
        setVisible(true);
    }

    /**
     * Establece los textos de los componentes de la vista según el manejador de mensajes.
     *
     * @param mensajeHandler Manejador de mensajes para internacionalización.
     */

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("producto.anadir.titulo"));
        lblCodigo.setText(mensajeHandler.get("producto.anadir.lbl.codigo"));
        lblNombre.setText(mensajeHandler.get("producto.anadir.lbl.nombre"));
        lblPrecio.setText(mensajeHandler.get("producto.anadir.lbl.precio"));
        btnAceptar.setText(mensajeHandler.get("producto.anadir.btn.aceptar"));
        btnLimpiar.setText(mensajeHandler.get("producto.anadir.btn.limpiar"));
        if (panelPrincipal.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
            border.setTitle(mensajeHandler.get("producto.anadir.panel.titulo"));
            panelPrincipal.repaint();
        }
    }

    /**
     * Muestra los datos del producto en los campos de texto.
     *
     * @param codigo Código del producto.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     */

    public void mostrarProducto(String codigo, String nombre, double precio) {
        txtCodigo.setText(codigo);
        txtNombre.setText(nombre);
        String precioFormateado = FormateadorUtils.formatearMoneda(precio, mensajeHandler.getLocale());
        txtPrecio.setText(precioFormateado);
    }

    /**
     * Limpia los campos de entrada del formulario.
     * Resetea los campos de código, nombre y precio.
     */

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     *
     * @param mensaje Mensaje a mostrar al usuario.
     */

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }
    /**
     * Actualiza la internacionalización de los textos de la vista.
     * Reestablece los textos y re-formatea el precio si ya hay datos en pantalla.
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
                txtPrecio.setText(FormateadorUtils.formatearMoneda(valor, nuevoHandler.getLocale()));
            } catch (Exception e) {
                txtPrecio.setText("");
            }
        }
    }
    // En ProductoAnadirView.java



    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBtnAceptar() { return btnAceptar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }



    /**
     * Establece los iconos para los botones de aceptar y limpiar.
     * Utiliza iconos escalados desde recursos.
     */
    public void setIconos(){

        setIconoEscalado(btnAceptar, "/md-done-all.png", 20, 20);
        setIconoEscalado(btnLimpiar, "/ios-trash.png", 20, 20);
    }

    /**
     * Establece un icono escalado para un botón.
     * Utiliza una ruta de recurso para cargar el icono y lo escala al tamaño especificado.
     *
     * @param boton Botón al que se le asignará el icono.
     * @param ruta Ruta del recurso del icono.
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
}
