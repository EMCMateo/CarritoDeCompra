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
        setSize(800, 850);
        setLocationRelativeTo(null);
        setResizable(false);

        // Configurar el JFileChooser que ya está en el formulario
        // 1. Configurar estado inicial de los controles
        btnContinuar.setEnabled(true); // Habilitado por defecto para "En memoria"

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setVisible(false);

        // Aplicar textos internacionalizados
        setTextos();

        // Listener del ComboBox para mostrar u ocultar el JFileChooser
        // 2. Listener del ComboBox para habilitar/deshabilitar controles

        cmbTipoAlmacenamiento.addActionListener(e -> {
            AlmacenamientoOpcion seleccion = (AlmacenamientoOpcion) cmbTipoAlmacenamiento.getSelectedItem();
            boolean mostrarFileChooser = seleccion != null && !seleccion.getKey().equals("MEMORIA");
            fileChooser.setVisible(mostrarFileChooser);
            boolean necesitaRuta = seleccion != null && !seleccion.getKey().equals("MEMORIA");

            fileChooser.setVisible(necesitaRuta);
            // Se habilita si NO necesita ruta, se deshabilita si SÍ la necesita.
            btnContinuar.setEnabled(!necesitaRuta);

            // Limpiamos la ruta seleccionada al cambiar de opción para evitar errores
            rutaSeleccionada = null;
            fileChooser.setSelectedFile(null);

            this.pack();
        });


        // 3. Listener del JFileChooser para re-habilitar el botón "Continuar"
        fileChooser.addActionListener(e -> {
            // Si el usuario presiona "Abrir" (o el botón de aprobación)
            if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                java.io.File archivoSeleccionado = fileChooser.getSelectedFile();
                if (archivoSeleccionado != null) {
                    this.rutaSeleccionada = archivoSeleccionado.getAbsolutePath();
                    btnContinuar.setEnabled(true); // ¡Habilitamos el botón principal!
                } else {
                    // Si por alguna razón el archivo es nulo, la ruta es nula y el botón se mantiene deshabilitado
                    this.rutaSeleccionada = null;
                    btnContinuar.setEnabled(false);
                }
            } else if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
                // Si el usuario presiona "Cancelar" en el FileChooser, la ruta es nula y el botón sigue deshabilitado
                this.rutaSeleccionada = null;
                btnContinuar.setEnabled(false);
            }
        });


        // El listener del botón "Continuar" ya no es necesario aquí,
        // porque la clase Main añade su propio listener para gestionar la transición.
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
