package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {

    private String username;
    private String password;
    private Rol rol;
    private List<RespuestaSeguridad> respuestasSeguridad;
    private String nombreCompleto;
    private String fechaNacimiento;
    private String correo;
    private String telefono;

    public Usuario(String username, String password, Rol rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.respuestasSeguridad = new ArrayList<>();
    }

    public Usuario(){}

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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
                "username='" + username + '\'' +
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
        return Objects.equals(username, usuario.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
