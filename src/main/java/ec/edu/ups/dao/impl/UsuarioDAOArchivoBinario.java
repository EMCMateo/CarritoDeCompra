package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.excepciones.ValidacionException;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import java.io.*;
import java.util.*;

public class UsuarioDAOArchivoBinario implements UsuarioDAO {
    private final String ruta;
    private final List<Usuario> usuarios = new ArrayList<>();

    // Cada entidad se guarda en su propio archivo binario: usuarios.bin
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
        // Si no hay usuarios, creamos los de prueba
        if (usuarios.isEmpty()) {
            crear(new Usuario("0150363232", "yp8dfN5q_10", Rol.ADMINISTRADOR));
            crear(new Usuario("0701277634", "yp8dfN5q_10", Rol.ADMINISTRADOR));
        }
    }

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
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
        guardarUsuarios();
    }

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

    @Override
    public void eliminar(String cedula) {
        usuarios.removeIf(u -> u.getCedula().equals(cedula));
        guardarUsuarios();
    }

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

    @Override
    public List<Usuario> listarTodos() {
        cargarUsuarios();
        return new ArrayList<>(usuarios);
    }

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
