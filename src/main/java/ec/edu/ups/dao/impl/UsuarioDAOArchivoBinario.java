package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.excepciones.ValidacionException;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import java.io.*;
import java.util.*;


/**
 * Implementación de UsuarioDAO que almacena los usuarios en un archivo binario.
 * Se crea un archivo "usuarios.bin" en la ruta indicada.
 * Esta clase también se encarga de crear usuarios por defecto si el archivo está vacío.
 */

public class UsuarioDAOArchivoBinario implements UsuarioDAO {
    private final String ruta;

    /**
     * Lista en memoria de usuarios cargados desde el archivo binario.
     */

    private final List<Usuario> usuarios = new ArrayList<>();

    /**
     * Constructor que inicializa el DAO, carga los usuarios y crea el archivo si no existe.
     * @param ruta Carpeta donde se encuentra o se creará el archivo.
     * @throws ValidacionException si ocurre un error al crear usuarios por defecto.
     */

    public UsuarioDAOArchivoBinario(String ruta) throws ValidacionException {
        this.ruta = ruta;
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        File archivo = new File(ruta + "/usuarios.bin");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cargarUsuarios();
        if (usuarios.isEmpty()) {
            crear(new Usuario("0150363232", "yp8dfN5q_10", Rol.ADMINISTRADOR));
            crear(new Usuario("0701277634", "yp8dfN5q_10", Rol.ADMINISTRADOR));
        }
    }
    /**
     * Carga la lista de usuarios desde el archivo binario.
     */
    private void cargarUsuarios() {
        File archivo = new File(ruta + "/usuarios.bin");
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                usuarios.clear();
                usuarios.addAll((List<Usuario>) obj);
            }
        } catch (EOFException eof) {
            // Archivo vacío, no hacer nada
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Guarda la lista actual de usuarios en el archivo binario.
     */
    private void guardarUsuarios() {
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + "/usuarios.bin"))) {
            oos.writeObject(usuarios);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Autentica a un usuario comparando cédula y contraseña.
     * @param cedula Cédula del usuario.
     * @param password Contraseña del usuario.
     * @return Usuario autenticado o null si no coincide.
     */
    @Override
    public Usuario autenticar(String cedula, String password) {
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Agrega un nuevo usuario a la lista y lo guarda en el archivo.
     * @param usuario Usuario a crear.
     */
    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
        guardarUsuarios();
    }
    /**
     * Busca un usuario por su cédula.
     * @param cedula Cédula del usuario.
     * @return Usuario encontrado o null.
     */

    @Override
    public Usuario buscarPorUsername(String cedula) {
        cargarUsuarios();
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula)) {
                return u;
            }
        }
        return null;
    }
    /**
     * Elimina un usuario según su cédula.
     * @param cedula Cédula del usuario.
     */
    @Override
    public void eliminar(String cedula) {
        usuarios.removeIf(u -> u.getCedula().equals(cedula));
        guardarUsuarios();
    }
    /**
     * Actualiza los datos de un usuario existente.
     * @param usuario Usuario con datos actualizados.
     */
    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCedula().equals(usuario.getCedula())) {
                usuarios.set(i, usuario);
                guardarUsuarios();
                return;
            }
        }
    }
    /**
     * Retorna una copia de la lista de todos los usuarios.
     * @return Lista de usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        cargarUsuarios();
        return new ArrayList<>(usuarios);
    }
    /**
     * Lista todos los usuarios con rol ADMIN.
     * @return Lista de administradores.
     */
    @Override
    public List<Usuario> listarAdmin() {
        cargarUsuarios();
        List<Usuario> admins = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getRol() != null && u.getRol().name().equalsIgnoreCase("ADMIN")) {
                admins.add(u);
            }
        }
        return admins;
    }
    /**
     * Lista todos los usuarios con rol CLIENTE.
     * @return Lista de clientes.
     */
    @Override
    public List<Usuario> listarClientes() {
        cargarUsuarios();
        List<Usuario> clientes = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getRol() != null && u.getRol().name().equalsIgnoreCase("CLIENTE")) {
                clientes.add(u);
            }
        }
        return clientes;
    }
}
