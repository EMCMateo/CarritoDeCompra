package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;

import java.util.ArrayList;
import java.util.List;

public class PreguntaDAOMemoria implements PreguntaDAO {

    private final List<Pregunta> listaPreguntas;

    public PreguntaDAOMemoria() {
        this.listaPreguntas = new ArrayList<>();
    }

    @Override
    public List<Pregunta> listarTodas() {
        return new ArrayList<>(this.listaPreguntas);
    }

    public void agregarPregunta(Pregunta pregunta) {
        this.listaPreguntas.add(pregunta);
    }

}