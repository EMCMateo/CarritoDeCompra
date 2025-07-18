package ec.edu.ups.excepciones;

/**
 * Excepción personalizada que indica un problema al acceder o manipular datos persistidos,
 * como errores al leer o escribir en almacenamiento.
 */
public class PersistenciaException extends Exception {

    /**
     * Crea una nueva excepción con un mensaje específico.
     * @param mensaje el mensaje que describe el error.
     */
    public PersistenciaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea una nueva excepción con un mensaje y una causa.
     * @param mensaje el mensaje que describe el error.
     * @param causa la causa que originó esta excepción.
     */
    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
