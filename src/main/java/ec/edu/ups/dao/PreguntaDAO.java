package ec.edu.ups.dao;

import ec.edu.ups.modelo.Pregunta;

import java.util.List;

/**
 * Interfaz para la gestión de preguntas de seguridad.
 */
public interface PreguntaDAO {

    /**
     * Lista todas las preguntas almacenadas.
     * @return una lista de preguntas.
     */
    List<Pregunta> listarTodas();

    /**
     * Agrega una nueva pregunta al repositorio.
     * @param pregunta la pregunta a agregar.
     */
    void agregarPregunta(Pregunta pregunta);
}
