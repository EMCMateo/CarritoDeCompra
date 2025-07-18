package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;
import java.io.*;
import java.util.*;

/**
 * PreguntaDAOArchivoBinario
 * Implementa el guardado/lectura de preguntas en archivo binario
 * usando los métodos exactos de la interfaz PreguntaDAO.
 */
public class PreguntaDAOArchivoBinario implements PreguntaDAO {
    private final String ruta;
    private final List<Pregunta> preguntas = new ArrayList<>();

    public PreguntaDAOArchivoBinario(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta no puede ser nula o vacía.");
        }
        this.ruta = ruta;
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        File archivo = new File(ruta + "/preguntas.bin");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cargarPreguntas();
    }
    /**
     * Carga las preguntas desde el archivo binario al inicio.
     * Si el archivo está vacío, no hace nada.
     */

    private void cargarPreguntas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta + "/preguntas.bin"))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                preguntas.clear();
                preguntas.addAll((List<Pregunta>) obj);
            }
        } catch (EOFException eof) {
            // Archivo vacío, es normal al inicio
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Guarda las preguntas en el archivo binario.
     * Se llama cada vez que se agrega una nueva pregunta.
     */

    private void guardarPreguntas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + "/preguntas.bin"))) {
            oos.writeObject(preguntas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Pregunta> listarTodas() {
        return new ArrayList<>(preguntas);
    }


    @Override
    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
        guardarPreguntas();
    }
}
