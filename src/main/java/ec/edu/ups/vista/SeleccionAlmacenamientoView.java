package ec.edu.ups.vista;

import javax.swing.*;

public class SeleccionAlmacenamientoView extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox<String> cmbTipoAlmacenamiento;
    private JLabel lblChooseRoot;
    private JButton btnContinuar;
    private JFileChooser fileChooser;
    private String rutaSeleccionada;

    public SeleccionAlmacenamientoView() {
        setContentPane(panelPrincipal);
        setTitle("Configuración de Almacenamiento");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 750);
        setLocationRelativeTo(null);
        setResizable(false);

        // 1. Configurar el JFileChooser que ya está en el formulario
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Seleccionar carpeta para almacenamiento");
        // Ocultamos los botones "Abrir" y "Cancelar" del JFileChooser, ya que usaremos nuestro propio botón "Confirmar"
        fileChooser.setControlButtonsAreShown(false);
        // Lo ocultamos por defecto, solo se mostrará si se elige una opción de archivo.
        fileChooser.setVisible(false);

        // Inicializa opciones del comboBox
        cmbTipoAlmacenamiento.removeAllItems();
        cmbTipoAlmacenamiento.addItem("En memoria");
        cmbTipoAlmacenamiento.addItem("Archivo de texto");
        cmbTipoAlmacenamiento.addItem("Archivo binario");

        // 2. Modificar el listener del ComboBox para mostrar u ocultar el JFileChooser
        cmbTipoAlmacenamiento.addActionListener(e -> {
            // Si el índice es 0 ("En memoria"), se oculta el file chooser. Si es otro, se muestra.
            boolean mostrarFileChooser = cmbTipoAlmacenamiento.getSelectedIndex() != 0;
            fileChooser.setVisible(mostrarFileChooser);
            // Reajustamos el tamaño de la ventana para que el JFileChooser quepa correctamente
            this.pack();
        });

        // 3. Modificar el listener del botón para que lea la ruta del JFileChooser del formulario
        btnContinuar.addActionListener(e -> {
            String tipo = (String) cmbTipoAlmacenamiento.getSelectedItem();

            // Si el tipo de almacenamiento requiere un archivo, validamos la selección
            if (!tipo.equals("En memoria")) {
                java.io.File archivoSeleccionado = fileChooser.getSelectedFile();
                if (archivoSeleccionado == null) {
                    JOptionPane.showMessageDialog(this,
                        "Debe seleccionar una carpeta para el almacenamiento.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Detiene el proceso si no hay carpeta seleccionada
                }
                // Si se seleccionó una carpeta, guardamos su ruta
                rutaSeleccionada = archivoSeleccionado.getAbsolutePath();
            } else {
                // Si es "En memoria", la ruta es nula
                rutaSeleccionada = null;
            }
            // La lógica para cerrar la ventana y continuar se encuentra en la clase Main,
            // que también tiene un listener para este botón. No llamamos a dispose() aquí.
        });

        // Ajustamos el tamaño inicial de la ventana
        pack();
    }

    public JComboBox<String> getCmbTipoAlmacenamiento() {
        return cmbTipoAlmacenamiento;
    }

    public String getRutaSeleccionada() {
        return rutaSeleccionada;
    }

    public JButton getBtnContinuar() {
        return btnContinuar;
    }
}
