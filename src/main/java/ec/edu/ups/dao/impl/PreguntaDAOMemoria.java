package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de {@link PreguntaDAO} que almacena las preguntas en memoria.
 * <p> Ideal para pruebas o entorno sin persistencia real.
 */
public class PreguntaDAOMemoria implements PreguntaDAO {

    /** Lista en memoria de todas las preguntas */
    private final List<Pregunta> listaPreguntas;

    /**
     * Constructor por defecto. Inicializa la lista de preguntas vacía.
     */
    public PreguntaDAOMemoria() {
        this.listaPreguntas = new ArrayList<>();
    }

    /**
     * Lista todas las preguntas almacenadas en memoria.
     *
     * @return Lista de preguntas
     */
    @Override
    public List<Pregunta> listarTodas() {
        return new ArrayList<>(this.listaPreguntas);
    }

    /**
     * Agrega una nueva pregunta a la lista en memoria.
     *
     * @param pregunta Pregunta a agregar
     */
    public void agregarPregunta(Pregunta pregunta) {
        this.listaPreguntas.add(pregunta);
    }
}
