package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representa una pregunta de seguridad o validación.
 * Cada pregunta tiene un identificador único y su texto.
 */
public class Pregunta implements Serializable {

    private int id;
    private String texto;

    /**
     * Crea una pregunta con el ID y texto especificados.
     *
     * @param id    Identificador único.
     * @param texto Texto de la pregunta.
     */
    public Pregunta(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                '}';
    }

    /**
     * Compara dos preguntas por su ID.
     *
     * @param o Objeto a comparar.
     * @return {@code true} si los IDs coinciden.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pregunta pregunta = (Pregunta) o;
        return id == pregunta.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
