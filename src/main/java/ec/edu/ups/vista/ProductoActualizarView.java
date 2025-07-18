package ec.edu.ups.vista;

import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;
import java.util.Locale;

/**
 * Vista para actualizar un producto.
 * Permite buscar un producto por código, mostrar sus detalles y actualizar su información.
 */
public class ProductoActualizarView extends JInternalFrame {

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JPanel panelPrincipal;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private MensajeInternacionalizacionHandler mensajeHandler;
    /**
     * Constructor de la vista ProductoActualizarView.
     * Inicializa los componentes y configura la ventana.
     *
     * @param mensajeHandler Manejador de mensajes para internacionalización.
     */

    public ProductoActualizarView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setContentPane(panelPrincipal);
        configurarVentana();
        setTextos(mensajeHandler);
    }

    /**
     * Configura la ventana con título, tamaño y comportamiento.
     * También establece los iconos de los botones.
     */

    private void configurarVentana() {
        setTitle(mensajeHandler.get("producto.actualizar.titulo"));
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setSize(400, 200);
        setVisible(true);
        setIconos();
    }

    /**
     * Establece los textos de los componentes de la vista utilizando el manejador de mensajes.
     * @param mensajeHandler Manejador de mensajes para obtener los textos internacionalizados.
     */

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("producto.actualizar.titulo"));
        lblCodigo.setText(mensajeHandler.get("producto.actualizar.lbl.codigo"));
        lblNombre.setText(mensajeHandler.get("producto.actualizar.lbl.nombre"));
        lblPrecio.setText(mensajeHandler.get("producto.actualizar.lbl.precio"));
        btnBuscar.setText(mensajeHandler.get("producto.actualizar.btn.buscar"));
        btnActualizar.setText(mensajeHandler.get("producto.actualizar.btn.actualizar"));
        if (panelPrincipal.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
            border.setTitle(mensajeHandler.get("producto.actualizar.panel.titulo"));
            panelPrincipal.repaint();
        }
    }
    /**
     * Muestra los detalles del producto en los campos de texto.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     * @param locale Locale para formatear el precio.
     */

    public void mostrarProducto(String nombre, double precio, Locale locale) {
        txtNombre.setText(nombre);
        txtPrecio.setText(FormateadorUtils.formatearMoneda(precio, locale));
    }

    /**
     * Muestra un mensaje al usuario en un cuadro de diálogo.
     * @param mensaje El mensaje a mostrar.
     */

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Limpia los campos de entrada del formulario.
     * Resetea los campos de código, nombre y precio.
     */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnActualizar() { return btnActualizar; }

    /**
     * Actualiza la internacionalización de los textos y formatea el campo de precio.
     * @param nuevoHandler Nuevo manejador de mensajes para internacionalización.
     */
    public void actualizarInternacionalizacion(MensajeInternacionalizacionHandler nuevoHandler) {
        this.mensajeHandler = nuevoHandler;
        setTextos(nuevoHandler);

        // Re-formatear el campo de precio si tiene contenido
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

    /**
     * Establece los iconos de los botones con imágenes escaladas.
     * Utiliza recursos locales para cargar las imágenes.
     */


    public void setIconos(){

        setIconoEscalado(btnBuscar, "/ios-search.png", 20, 20);
        setIconoEscalado(btnActualizar, "/ios-create.png", 20, 20);
    }

    /**
     * Establece un icono escalado para un botón.
     * Carga la imagen desde los recursos y la escala al tamaño especificado.
     *
     * @param boton El botón al que se le asignará el icono.
     * @param ruta La ruta del recurso de la imagen.
     * @param ancho El ancho deseado para el icono.
     * @param alto El alto deseado para el icono.
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

