package ec.edu.ups.vista;

import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.RespuestaSeguridad;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreguntasModificarView extends JFrame {

    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JScrollPane scrollPane;
    private JPanel containerPanel;
    private JButton btnVerificar;

    private final MensajeInternacionalizacionHandler mensajes;
    private final Map<Pregunta, JTextField> camposDeRespuesta;
    private List<RespuestaSeguridad> respuestasActuales;

    public PreguntasModificarView(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes = mensajes;
        this.camposDeRespuesta = new HashMap<>();

        setTitle(mensajes.get("pregunta.recuperar.titulo"));
        if (panelPrincipal.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
            border.setTitle(mensajes.get("modificar.preguntas.panel.titulo"));
            panelPrincipal.repaint();
        }
        setContentPane(panelPrincipal);
        setSize(600, 400);
        setLocationRelativeTo(null); // centrar ventana

        actualizarTextos();
        URL urlVerificar = getClass().getResource("/md-done-all.png");
        btnVerificar.setIcon(new ImageIcon(urlVerificar));
    }

    public void mostrarPreguntasDelUsuario(List<RespuestaSeguridad> respuestasDelUsuario) {
        this.respuestasActuales = respuestasDelUsuario;

        containerPanel.removeAll();
        camposDeRespuesta.clear();

        if (respuestasDelUsuario == null || respuestasDelUsuario.isEmpty()) {
            JLabel vacio = new JLabel(mensajes.get("pregunta.recuperar.vacio"));
            containerPanel.add(vacio);
        } else {
            containerPanel.setLayout(new GridLayout(respuestasDelUsuario.size(), 2, 10, 10));

            for (RespuestaSeguridad respuesta : respuestasDelUsuario) {
                Pregunta pregunta = respuesta.getPregunta();
                JLabel lbl = new JLabel(mensajes.get("pregunta.seguridad." + pregunta.getId()));
                JTextField txt = new JTextField();

                camposDeRespuesta.put(pregunta, txt);

                containerPanel.add(lbl);
                containerPanel.add(txt);
            }
        }

        containerPanel.revalidate();
        containerPanel.repaint();
    }

    public void actualizarTextos() {
        lblTitulo.setText(mensajes.get("pregunta.recuperar.titulo"));
        btnVerificar.setText(mensajes.get("global.boton.verificar"));

    }

    public Map<Pregunta, String> getRespuestasIngresadas() {
        Map<Pregunta, String> respuestas = new HashMap<>();
        for (Map.Entry<Pregunta, JTextField> entry : camposDeRespuesta.entrySet()) {
            respuestas.put(entry.getKey(), entry.getValue().getText().trim());
        }
        return respuestas;
    }

    public JButton getBtnVerificar() {
        return btnVerificar;
    }

    public void limpiarCampos() {
        for (JTextField campo : camposDeRespuesta.values()) {
            campo.setText("");
        }
    }

    public void setIconos() {

        setIconoEscalado(btnVerificar, "/md-done-all.png", 20, 20);
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
