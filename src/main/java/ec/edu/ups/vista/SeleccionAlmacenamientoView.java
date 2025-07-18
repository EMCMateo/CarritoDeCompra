package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class SeleccionAlmacenamientoView extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox<AlmacenamientoOpcion> cmbTipoAlmacenamiento;
    private JLabel lblChooseRoot;
    private JButton btnContinuar;
    private JFileChooser fileChooser;
    private String rutaSeleccionada;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public SeleccionAlmacenamientoView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 750);
        setLocationRelativeTo(null);
        setResizable(false);

        // Configurar el JFileChooser que ya está en el formulario
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setControlButtonsAreShown(false);
        fileChooser.setVisible(false);

        // Aplicar textos internacionalizados
        setTextos();

        // Listener del ComboBox para mostrar u ocultar el JFileChooser
        cmbTipoAlmacenamiento.addActionListener(e -> {
            AlmacenamientoOpcion seleccion = (AlmacenamientoOpcion) cmbTipoAlmacenamiento.getSelectedItem();
            boolean mostrarFileChooser = seleccion != null && !seleccion.getKey().equals("MEMORIA");
            fileChooser.setVisible(mostrarFileChooser);
            this.pack();
        });

        // Listener del botón para leer la ruta del JFileChooser del formulario
        btnContinuar.addActionListener(e -> {
            AlmacenamientoOpcion seleccion = getSeleccion();

            if (seleccion != null && !seleccion.getKey().equals("MEMORIA")) {
                java.io.File archivoSeleccionado = fileChooser.getSelectedFile();
                if (archivoSeleccionado == null) {
                    rutaSeleccionada = null; // Se deja nulo para que Main lo valide
                } else {
                    rutaSeleccionada = archivoSeleccionado.getAbsolutePath();
                }
            } else {
                rutaSeleccionada = null;
            }
        });

        pack();
    }

    private void setTextos() {
        setTitle(mensajeHandler.get("seleccion.titulo"));
        lblChooseRoot.setText(mensajeHandler.get("seleccion.label.tipo"));
        btnContinuar.setText(mensajeHandler.get("seleccion.boton.continuar"));
        fileChooser.setDialogTitle(mensajeHandler.get("seleccion.chooser.titulo"));

        cmbTipoAlmacenamiento.removeAllItems();
        cmbTipoAlmacenamiento.addItem(new AlmacenamientoOpcion("MEMORIA", mensajeHandler.get("seleccion.opcion.memoria")));
        cmbTipoAlmacenamiento.addItem(new AlmacenamientoOpcion("TEXTO", mensajeHandler.get("seleccion.opcion.texto")));
        cmbTipoAlmacenamiento.addItem(new AlmacenamientoOpcion("BINARIO", mensajeHandler.get("seleccion.opcion.binario")));
    }

    /**
     * Clase interna para manejar las opciones del JComboBox,
     * separando el valor lógico (key) del texto mostrado (displayValue).
     */
    public static class AlmacenamientoOpcion {
        private final String key;
        private final String displayValue;

        public AlmacenamientoOpcion(String key, String displayValue) {
            this.key = key;
            this.displayValue = displayValue;
        }

        public String getKey() {
            return key;
        }

        @Override
        public String toString() {
            return displayValue; // Esto es lo que JComboBox mostrará
        }
    }

    public AlmacenamientoOpcion getSeleccion() {
        return (AlmacenamientoOpcion) cmbTipoAlmacenamiento.getSelectedItem();
    }

    public JComboBox<AlmacenamientoOpcion> getCmbTipoAlmacenamiento() {
        return cmbTipoAlmacenamiento;
    }

    public String getRutaSeleccionada() {
        return rutaSeleccionada;
    }

    public JButton getBtnContinuar() {
        return btnContinuar;
    }
}
