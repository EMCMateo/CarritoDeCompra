    package ec.edu.ups.modelo;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Objects;
    import java.io.Serializable;
    import ec.edu.ups.excepciones.ValidacionException;

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

        public Usuario(String cedula, String password, Rol rol) throws ValidacionException {
            this.cedula = cedula;
            this.setPassword(password); // validación incluida
            this.rol = rol;
            this.respuestasSeguridad = new ArrayList<>();
        }

        public Usuario() {
            this.respuestasSeguridad = new ArrayList<>();
        }

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


    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        if (!validarCedulaEcuatoriana(cedula)) {
            throw new IllegalArgumentException("Cédula ecuatoriana no válida");
        }
        this.cedula = cedula;
    }

    // Validación de cédula ecuatoriana
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


    public void addRespuesta(Pregunta pregunta, String respuestaTexto) {
        if (this.respuestasSeguridad == null) {
            this.respuestasSeguridad = new ArrayList<>();
        }
        this.respuestasSeguridad.add(new RespuestaSeguridad(pregunta, respuestaTexto));
    }

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
