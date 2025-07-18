package ec.edu.ups.util;

import java.io.*;
import java.util.Properties;

/**
 * Clase utilitaria encargada de gestionar la configuración relacionada con el tipo
 * y ruta de almacenamiento del sistema. Utiliza un archivo `config.properties` como persistencia.
 */
public class ConfiguracionAlmacenamiento {

    /** Nombre del archivo de configuración */
    private static final String CONFIG_FILE = "config.properties";

    /** Propiedades cargadas desde el archivo */
    private static Properties props = new Properties();

    /**
     * Guarda la configuración de almacenamiento en el archivo.
     *
     * @param tipo Tipo de almacenamiento (por ejemplo, "memoria", "archivo", etc.)
     * @param ruta Ruta del archivo (puede ser nulo si el tipo no requiere ruta)
     */
    public static void guardarConfiguracion(String tipo, String ruta) {
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.setProperty("tipo_almacenamiento", tipo);
            if (ruta != null) {
                props.setProperty("ruta_almacenamiento", ruta);
            }
            props.store(out, "Configuración de almacenamiento");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el tipo de almacenamiento configurado.
     *
     * @return Tipo de almacenamiento, por defecto "En memoria" si no se encuentra definido.
     */
    public static String getTipoAlmacenamiento() {
        cargarConfiguracion();
        return props.getProperty("tipo_almacenamiento", "En memoria");
    }

    /**
     * Obtiene la ruta de almacenamiento configurada.
     *
     * @return Ruta definida, o una cadena vacía si no está establecida.
     */
    public static String getRutaAlmacenamiento() {
        cargarConfiguracion();
        return props.getProperty("ruta_almacenamiento", "");
    }

    /**
     * Carga el archivo de configuración si existe en el sistema.
     * Si no existe, se omite silenciosamente.
     */
    private static void cargarConfiguracion() {
        File config = new File(CONFIG_FILE);
        if (config.exists()) {
            try (FileInputStream in = new FileInputStream(config)) {
                props.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
