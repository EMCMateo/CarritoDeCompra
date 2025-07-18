package ec.edu.ups.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que permite gestionar la internacionalización del sistema
 * mediante un archivo de recursos (mensajes.properties).
 */
public class MensajeInternacionalizacionHandler {

    /** Paquete de recursos que contiene los textos internacionalizados */
    private ResourceBundle bundle;

    /** Locale actual utilizado para cargar los textos */
    private Locale locale;

    /**
     * Constructor que inicializa el handler con un lenguaje y país específicos.
     *
     * @param lenguaje Código del lenguaje (por ejemplo "es", "en")
     * @param pais Código del país (por ejemplo "EC", "US")
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el mensaje correspondiente a una clave internacionalizada.
     *
     * @param key Clave del mensaje en el archivo de recursos
     * @return Texto traducido correspondiente
     */
    public String get(String key) {
        return bundle.getString(key);
    }

    /**
     * Cambia el lenguaje y país del sistema, actualizando el bundle activo.
     *
     * @param lenguaje Código del nuevo lenguaje
     * @param pais Código del nuevo país
     */
    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el {@link Locale} actual utilizado para la internacionalización.
     *
     * @return Objeto Locale activo
     */
    public Locale getLocale() {
        return locale;
    }
}
