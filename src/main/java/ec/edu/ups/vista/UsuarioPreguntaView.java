package ec.edu.ups.vista;

import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class UsuarioPreguntaView extends JFrame {

    private JPanel panelPrincipal;
    private JComboBox<String> cmbPreguntas;
    private JTextField txtRespuesta;
    private JButton btnGuardar;
    private JButton btnFinalizar;
    private JTextArea txtAreaRespuestas;
    private JLabel lblPreguntasLBL;
    private JLabel lblPregunta1;
    private JLabel lblConsejo;
    private JLabel lblRespuesta;

    private final MensajeInternacionalizacionHandler mensajeHandler;

    public UsuarioPreguntaView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setTitle(mensajeHandler.get("preguntas.titulo"));
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTextos();
    }

    private void setTextos() {
        lblPregunta1.setText(mensajeHandler.get("preguntas.titulo"));
        lblConsejo.setText(mensajeHandler.get("preguntas.consejo"));
        lblPreguntasLBL.setText(mensajeHandler.get("preguntas.pregunta"));
        lblRespuesta.setText(mensajeHandler.get("preguntas.respuesta"));
        btnGuardar.setText(mensajeHandler.get("boton.guardar"));
        btnFinalizar.setText(mensajeHandler.get("pregunta.finalizar"));
    }

    public JComboBox<String> getCmbPreguntas() {
        return cmbPreguntas;
    }

    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnFinalizar() {
        return btnFinalizar;
    }

    public JTextArea getTxtAreaRespuestas() {
        return txtAreaRespuestas;
    }

    public JLabel getLblPreguntasLBL() {
        return lblPreguntasLBL;
    }

    public JLabel getLblPregunta1() {
        return lblPregunta1;
    }

    public JLabel getLblConsejo() {
        return lblConsejo;
    }

    public JLabel getLblRespuesta() {
        return lblRespuesta;
    }
}
