package ec.edu.ups.excepciones;

/**
 * Excepción personalizada que se lanza cuando una validación de datos falla.
 */
public class ValidacionException extends Exception {

    /**
     * Crea una nueva excepción con un mensaje específico.
     * @param mensaje el mensaje que describe el error de validación.
     */
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}
