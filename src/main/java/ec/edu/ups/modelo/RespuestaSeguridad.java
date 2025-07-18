package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Representa una respuesta de seguridad asociada a una pregunta específica,
 * utilizada para la verificación de identidad del usuario.
 */
public class RespuestaSeguridad implements Serializable {

    private Pregunta pregunta;
    private String respuesta;

    /**
     * Crea una nueva respuesta de seguridad con la pregunta dada y su respuesta correspondiente.
     *
     * @param pregunta La pregunta de seguridad.
     * @param respuesta La respuesta del usuario.
     */
    public RespuestaSeguridad(Pregunta pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * Verifica si una respuesta dada coincide con la registrada por el usuario.
     *
     * @param respuestaAValidar La respuesta ingresada que se desea validar.
     * @return true si la respuesta es correcta, false en caso contrario.
     */
    public boolean esRespuestaCorrecta(String respuestaAValidar) {
        if (this.respuesta == null || respuestaAValidar == null) {
            return false;
        }
        return this.respuesta.trim().equalsIgnoreCase(respuestaAValidar.trim());
    }

    @Override
    public String toString() {
        return "RespuestaSeguridad{" +
                "pregunta=" + pregunta +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}
