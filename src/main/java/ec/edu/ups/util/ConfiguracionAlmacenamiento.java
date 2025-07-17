package ec.edu.ups.util;

import java.io.*;
import java.util.Properties;

public class ConfiguracionAlmacenamiento {
    private static final String CONFIG_FILE = "config.properties";
    private static Properties props = new Properties();

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

    public static String getTipoAlmacenamiento() {
        cargarConfiguracion();
        return props.getProperty("tipo_almacenamiento", "En memoria");
    }

    public static String getRutaAlmacenamiento() {
        cargarConfiguracion();
        return props.getProperty("ruta_almacenamiento", "");
    }

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
