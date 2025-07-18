package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.excepciones.ValidacionException;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de {@link UsuarioDAO} que almacena los usuarios en memoria usando una lista.
 */
public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    /**
     * Constructor que inicializa la lista de usuarios con algunos administradores por defecto.
     *
     * @throws ValidacionException si alguno de los usuarios iniciales es inválido.
     */
    public UsuarioDAOMemoria() throws ValidacionException {
        usuarios = new ArrayList<>();
        crear(new Usuario("0150363232", "yp8dfN5q_10", Rol.ADMINISTRADOR));
        crear(new Usuario("0701277634", "yp8dfN5q_10", Rol.ADMINISTRADOR));
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario autenticar(String cedula, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equalsIgnoreCase(cedula)
                    && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Usuario buscarPorUsername(String cedula) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equalsIgnoreCase(cedula)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCedula().equalsIgnoreCase(usuario.getCedula())) {
                usuarios.set(i, usuario);
                return;
            }
        }
    }

    @Override
    public void eliminar(String cedula) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getCedula().equalsIgnoreCase(cedula)) {
                iterator.remove();
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
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                admins.add(usuario);
            }
        }
        return admins;
    }

    @Override
    public List<Usuario> listarClientes() {
        List<Usuario> clientes = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == Rol.CLIENTE) {
                clientes.add(usuario);
            }
        }
        return clientes;
    }
}

