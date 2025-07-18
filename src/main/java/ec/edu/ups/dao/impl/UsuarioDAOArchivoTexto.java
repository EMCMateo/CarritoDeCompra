package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.excepciones.PersistenciaException;
import ec.edu.ups.excepciones.ValidacionException;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol;

import java.io.*;
import java.util.*;

/**
 * Implementación de {@link UsuarioDAO} que guarda usuarios en un archivo de texto plano.
 */
public class UsuarioDAOArchivoTexto implements UsuarioDAO {

    private final String ruta;
    private final List<Usuario> usuarios = new ArrayList<>();

    /**
     * Constructor que recibe la ruta donde se almacenarán los archivos.
     *
     * @param ruta directorio donde se almacenará el archivo usuarios.txt.
     * @throws ValidacionException si hay errores de validación al cargar datos iniciales.
     * @throws PersistenciaException si ocurre un error al cargar o guardar usuarios.
     */
    public UsuarioDAOArchivoTexto(String ruta) throws ValidacionException, PersistenciaException {
        if (ruta == null || ruta.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta no puede ser nula o vacía.");
        }
        this.ruta = ruta;

        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        cargarUsuarios();

        if (usuarios.isEmpty()) {
            usuarios.add(new Usuario("0150363232", "yp8dfN5q_10", Rol.ADMINISTRADOR));
            usuarios.add(new Usuario("0701277634", "yp8dfN5q_10", Rol.ADMINISTRADOR));
            guardarUsuarios();
        }
    }

    /**
     * Carga usuarios desde el archivo usuarios.txt en memoria.
     *
     * @throws PersistenciaException si ocurre un error de lectura o formato.
     */
    private void cargarUsuarios() throws PersistenciaException {
        File archivo = new File(ruta + "/usuarios.txt");
        if (!archivo.exists()) return;

        usuarios.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",", -1);
                if (partes.length >= 8) {
                    Usuario u = new Usuario();
                    u.setCedula(partes[0]);
                    u.setPassword(partes[1]);
                    u.setRol(Rol.valueOf(partes[2]));
                    u.setNombreCompleto(partes[3].isEmpty() ? null : partes[3]);
                    u.setFechaNacimiento(partes[4].isEmpty() ? null : partes[4]);
                    u.setCorreo(partes[5].isEmpty() ? null : partes[5]);
                    u.setTelefono(partes[6].isEmpty() ? null : partes[6]);
                    u.setGenero(partes[7].isEmpty() ? null : partes[7]);
                    usuarios.add(u);
                }
            }
        } catch (IOException | ValidacionException e) {
            throw new PersistenciaException("Error al cargar usuarios desde el archivo", e);
        }
    }

    /**
     * Guarda todos los usuarios en el archivo usuarios.txt.
     *
     * @throws PersistenciaException si ocurre un error al escribir el archivo.
     */
    private void guardarUsuarios() throws PersistenciaException {
        File archivo = new File(ruta + "/usuarios.txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Usuario u : usuarios) {
                pw.println(String.join(",",
                        u.getCedula(),
                        u.getPassword(),
                        u.getRol().name(),
                        u.getNombreCompleto() != null ? u.getNombreCompleto() : "",
                        u.getFechaNacimiento() != null ? u.getFechaNacimiento() : "",
                        u.getCorreo() != null ? u.getCorreo() : "",
                        u.getTelefono() != null ? u.getTelefono() : "",
                        u.getGenero() != null ? u.getGenero() : ""
                ));
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar usuarios en el archivo", e);
        }
    }

    @Override
    public Usuario autenticar(String cedula, String password) {
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) throws PersistenciaException {
        usuarios.add(usuario);
        guardarUsuarios();
    }

    @Override
    public Usuario buscarPorUsername(String cedula) {
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String cedula) throws PersistenciaException {
        usuarios.removeIf(u -> u.getCedula().equals(cedula));
        guardarUsuarios();
    }

    @Override
    public void actualizar(Usuario usuario) throws PersistenciaException {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCedula().equals(usuario.getCedula())) {
                usuarios.set(i, usuario);
                guardarUsuarios();
                return;
            }
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public List<Usuario> listarAdmin() {
        List<Usuario> admins = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getRol() == Rol.ADMINISTRADOR) {
                admins.add(u);
            }
        }
        return admins;
    }

    @Override
    public List<Usuario> listarClientes() {
        List<Usuario> clientes = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getRol() == Rol.CLIENTE) {
                clientes.add(u);
            }
        }
        return clientes;
    }
}
