package ec.edu.ups.dao;

import ec.edu.ups.modelo.Pregunta;

import java.util.List;


public interface PreguntaDAO {


    List<Pregunta> listarTodas();


    void agregarPregunta(Pregunta pregunta);

}