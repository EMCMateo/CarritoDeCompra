package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;
import java.io.*;
import java.util.*;

/**
 * PreguntaDAOArchivoTexto
 * Implementa el guardado/lectura de preguntas en archivo de texto (CSV)
 * Formato del archivo preguntas.txt:
 * id,texto
 */
public class PreguntaDAOArchivoTexto implements PreguntaDAO {
    private final String ruta;
    private final List<Pregunta> preguntas = new ArrayList<>();

    public PreguntaDAOArchivoTexto(String ruta) {
        this.ruta = ruta;
        // Solo verificamos que exista la carpeta
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        // Cargamos las preguntas existentes
        cargarPreguntas();
    }

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

    private void guardarPreguntas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta + "/preguntas.txt"))) {
            for (Pregunta p : preguntas) {
                pw.println(p.getId() + "," + p.getTexto());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar preguntas: " + e.getMessage());
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
