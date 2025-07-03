package ec.edu.ups.vista;

import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.List;

public class UsuarioPreguntaView extends JFrame {

    private JPanel panelPrincipal;
    private JButton btnGuardar;
    private JLabel lblPregunta1, lblPregunta2, lblPregunta3;
    private JTextField txtRespuesta1, txtRespuesta2, txtRespuesta3;

    private final List<Pregunta> preguntas;
    private final Usuario usuario;
    private final MensajeInternacionalizacionHandler mensajeHandler;
    private final Runnable onGuardar;

    // Constructor que usa tu .form (GUI Builder)
    public UsuarioPreguntaView(List<Pregunta> preguntas,
                               Usuario usuario,
                               MensajeInternacionalizacionHandler mensajeHandler,
                               Runnable onGuardar) {
        this.preguntas = preguntas;
        this.usuario   = usuario;
        this.mensajeHandler = mensajeHandler;
        this.onGuardar = onGuardar;

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        setTitle(mensajeHandler.get("registro.pregunta.titulo"));
        cargarEventos();
        cargarTextos();

    }

    private void cargarTextos() {
        lblPregunta1.setText(preguntas.get(0).getTexto());
        lblPregunta2.setText(preguntas.get(1).getTexto());
        lblPregunta3.setText(preguntas.get(2).getTexto());
        btnGuardar.setText(mensajeHandler.get("global.boton.guardar"));
    }

    private void cargarEventos() {
        btnGuardar.addActionListener(e -> {
            String r1 = txtRespuesta1.getText().trim();
            String r2 = txtRespuesta2.getText().trim();
            String r3 = txtRespuesta3.getText().trim();

            if (r1.isEmpty() || r2.isEmpty() || r3.isEmpty()) {
                JOptionPane.showMessageDialog(this, mensajeHandler.get("mensaje.usuario.error.camposVacios"));
                return;
            }

            usuario.addRespuesta(preguntas.get(0), r1);
            usuario.addRespuesta(preguntas.get(1), r2);
            usuario.addRespuesta(preguntas.get(2), r3);

            onGuardar.run();   // Graba el usuario
            dispose();         // Cierra esta ventana
        });
    }

}
