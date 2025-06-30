package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<Usuario>();
        crear(new Usuario("admin", "12345678", Rol.ADMINISTRADOR));
        crear(new Usuario("admin2", "12345678", Rol.ADMINISTRADOR));
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario autenticar(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equalsIgnoreCase(username)
                    && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equalsIgnoreCase(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equalsIgnoreCase(usuario.getUsername())) {
                usuarios.set(i, usuario);
                return;
            }
        }
    }

    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equalsIgnoreCase(username)) {
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
