package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;
import java.io.*;
import java.util.*;

/**
 * Implementación de {@link PreguntaDAO} que almacena preguntas en un archivo de texto plano.
 * <p>
 * Cada línea del archivo representa una pregunta en el formato:
 * {@code id,texto}
 */
public class PreguntaDAOArchivoTexto implements PreguntaDAO {

    /** Ruta donde se encuentra el archivo preguntas.txt */
    private final String ruta;

    /** Lista en memoria de preguntas cargadas desde el archivo */
    private final List<Pregunta> preguntas = new ArrayList<>();

    /**
     * Constructor que inicializa el DAO con la ruta dada y carga las preguntas desde archivo.
     *
     * @param ruta Carpeta donde se ubicará el archivo preguntas.txt
     */
    public PreguntaDAOArchivoTexto(String ruta) {
        this.ruta = ruta;
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        cargarPreguntas();
    }

    /**
     * Carga las preguntas desde el archivo preguntas.txt.
     */
    private void cargarPreguntas() {
        File archivo = new File(ruta + "/preguntas.txt");
        if (!archivo.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    int id = Integer.parseInt(partes[0]);
                    String texto = partes[1];
                    preguntas.add(new Pregunta(id, texto));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar preguntas: " + e.getMessage());
        }
    }

    /**
     * Guarda todas las preguntas actuales en el archivo preguntas.txt.
     */
    private void guardarPreguntas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta + "/preguntas.txt"))) {
            for (Pregunta p : preguntas) {
                pw.println(p.getId() + "," + p.getTexto());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar preguntas: " + e.getMessage());
        }
    }
    /**
     * Lista todas las preguntas almacenadas en memoria.
     *
     * @return Lista de preguntas
     */

    @Override
    public List<Pregunta> listarTodas() {
        return new ArrayList<>(preguntas);
    }

    /**
     * Agrega una nueva pregunta a la lista y la guarda en el archivo.
     *
     * @param pregunta La pregunta a agregar
     */
    @Override
    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
        guardarPreguntas();
    }
}
