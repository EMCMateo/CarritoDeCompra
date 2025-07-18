package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.Serializable;
import ec.edu.ups.excepciones.ValidacionException;

/**
 * Representa un usuario del sistema, ya sea cliente o administrador.
 * Contiene datos personales, credenciales y preguntas de seguridad.
 */
public class Usuario implements Serializable {

    private String cedula;
    private String password;
    private Rol rol;
    private List<RespuestaSeguridad> respuestasSeguridad;
    private String nombreCompleto;
    private String fechaNacimiento;
    private String correo;
    private String telefono;
    private String genero;

    /**
     * Crea un nuevo usuario con cédula, contraseña y rol definidos.
     * Valida la contraseña bajo ciertos requisitos de seguridad.
     *
     * @param cedula Número de cédula del usuario.
     * @param password Contraseña del usuario.
     * @param rol Rol del usuario (ADMINISTRADOR o CLIENTE).
     * @throws ValidacionException Si la contraseña no cumple con los requisitos mínimos.
     */
    public Usuario(String cedula, String password, Rol rol) throws ValidacionException {
        this.cedula = cedula;
        this.setPassword(password); // validación incluida
        this.rol = rol;
        this.respuestasSeguridad = new ArrayList<>();
    }

    /**
     * Constructor vacío requerido para serialización u otras operaciones por defecto.
     */
    public Usuario() {
        this.respuestasSeguridad = new ArrayList<>();
    }

    // --- Getters y Setters

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getCedula() { return cedula; }

    /**
     * Establece la cédula del usuario validando que cumpla con el formato ecuatoriano.
     *
     * @param cedula La cédula a validar y establecer.
     * @throws IllegalArgumentException Si la cédula no es válida.
     */
    public void setCedula(String cedula) {
        if (!validarCedulaEcuatoriana(cedula)) {
            throw new IllegalArgumentException("Cédula ecuatoriana no válida");
        }
        this.cedula = cedula;
    }

    /**
     * Valida si una cédula ingresada cumple con el algoritmo de validación ecuatoriano.
     *
     * @param cedula Cédula a validar.
     * @return true si es válida, false en caso contrario.
     */
    private boolean validarCedulaEcuatoriana(String cedula) {
        if (cedula == null || cedula.length() != 10) return false;
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) return false;
        int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
        if (tercerDigito > 6) return false;
        int[] coef = {2,1,2,1,2,1,2,1,2};
        int suma = 0;
        for (int i = 0; i < coef.length; i++) {
            int val = coef[i] * Integer.parseInt(cedula.substring(i, i+1));
            suma += val > 9 ? val - 9 : val;
        }
        int ultimo = Integer.parseInt(cedula.substring(9, 10));
        int decena = ((suma + 9) / 10) * 10;
        return (decena - suma) == ultimo;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña validando que cumpla con los requisitos de seguridad.
     * Requiere mínimo 6 caracteres, una mayúscula, una minúscula y un símbolo especial.
     *
     * @param password Contraseña del usuario.
     * @throws ValidacionException Si no cumple con los criterios de seguridad.
     */
    public void setPassword(String password) throws ValidacionException {
        if (password == null || password.length() < 6) {
            throw new ValidacionException("mensaje.error.contrasena.longitud");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new ValidacionException("mensaje.error.contrasena.mayuscula");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new ValidacionException("mensaje.error.contrasena.minuscula");
        }
        if (!password.matches(".*[@_-].*")) {
            throw new ValidacionException("mensaje.error.contrasena.caracter");
        }
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<RespuestaSeguridad> getRespuestasSeguridad() {
        return respuestasSeguridad;
    }

    public void setRespuestasSeguridad(List<RespuestaSeguridad> respuestasSeguridad) {
        this.respuestasSeguridad = respuestasSeguridad;
    }

    /**
     * Añade una respuesta de seguridad asociada a una pregunta.
     *
     * @param pregunta La pregunta de seguridad.
     * @param respuestaTexto La respuesta del usuario.
     */
    public void addRespuesta(Pregunta pregunta, String respuestaTexto) {
        if (this.respuestasSeguridad == null) {
            this.respuestasSeguridad = new ArrayList<>();
        }
        this.respuestasSeguridad.add(new RespuestaSeguridad(pregunta, respuestaTexto));
    }

    /**
     * Devuelve la cantidad de preguntas de seguridad asignadas al usuario.
     *
     * @return Número de respuestas asignadas.
     */
    public int preguntasAsignadas(){
        return this.respuestasSeguridad.size();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "cedula='" + cedula + '\'' +
                ", rol=" + rol +
                ", password='" + password + '\'' +
                ", respuestasSeguridad=" + respuestasSeguridad +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(cedula, usuario.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }
}
