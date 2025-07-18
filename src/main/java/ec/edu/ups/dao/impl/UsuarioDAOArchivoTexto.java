package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol;
import java.io.*;
import java.util.*;

public class UsuarioDAOArchivoTexto implements UsuarioDAO {
    private final String ruta;
    private final List<Usuario> usuarios = new ArrayList<>();

    // El archivo se crea en la ruta que el usuario ingresa en SeleccionAlmacenamientoView
    public UsuarioDAOArchivoTexto(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta no puede ser nula o vacía.");
        }
        this.ruta = ruta;
        // Solo verificamos que exista la carpeta
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        // Cargamos los usuarios existentes
        cargarUsuarios();

        // Si no hay usuarios, creamos los de prueba
        if (usuarios.isEmpty()) {
            // Añadimos directamente a la lista y guardamos una sola vez
            usuarios.add(new Usuario("0150363232", "12345", Rol.ADMINISTRADOR));
            usuarios.add(new Usuario("0701277634", "12345", Rol.ADMINISTRADOR));
            guardarUsuarios();
        }
    }

    private void cargarUsuarios() {
        File archivo = new File(ruta + "/usuarios.txt");
        if (!archivo.exists()) {
            // Si el archivo no existe, simplemente retornamos (la lista estará vacía)
            return;
        }
        usuarios.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Leer usuarios en formato CSV
                String[] partes = linea.split(",", -1); // -1 para incluir campos vacíos al final
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
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    private void guardarUsuarios() {
        File archivo = new File(ruta + "/usuarios.txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Usuario u : usuarios) {
                // Guardamos cada usuario en una línea con formato CSV
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
            System.out.println("Error al guardar usuarios: " + e.getMessage());
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
        // Se busca sobre la lista en memoria, no se recarga.
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
        // Devuelve una copia de la lista en memoria.
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
