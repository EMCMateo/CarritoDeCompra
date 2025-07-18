package ec.edu.ups.vista;

import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Vista para que el usuario responda preguntas de seguridad.
 * Permite seleccionar una pregunta y proporcionar una respuesta.
 */

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

    /**
     * Constructor de la vista UsuarioPreguntaView.
     * Inicializa los componentes y configura la ventana.
     *
     * @param mensajeHandler Manejador de mensajes para internacionalización.
     */
    public UsuarioPreguntaView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setTitle(mensajeHandler.get("preguntas.titulo"));
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTextos();
        setIconos();
    }
    /**
     * Inicializa los componentes de la vista.
     * Configura los textos y los iconos de los botones.
     */

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
    public void setIconos() {
        URL urlFinalizar = getClass().getResource("/md-done-all.png");
        btnFinalizar.setIcon(new ImageIcon(urlFinalizar));

        setIconoEscalado(btnFinalizar, "/md-done-all.png", 20, 20);
        setIconoEscalado(btnGuardar, "/md-add-circle.png", 20, 20);


    }
    private void setIconoEscalado(JButton boton, String ruta, int ancho, int alto) {
        URL url = getClass().getResource(ruta);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
        }
    }

}


