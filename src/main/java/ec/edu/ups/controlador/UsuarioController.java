package ec.edu.ups.controlador;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.RespuestaSeguridad;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final UserRegistroView userRegistroView;
    private final ListarUsuarioView listarUsuarioView;
    private final MensajeInternacionalizacionHandler mensajeHandler;
    private final PreguntaDAO preguntaDAO;

    private Usuario usuario;

    public UsuarioController(UsuarioDAO usuarioDAO,
                             LoginView loginView,
                             UserRegistroView userRegistroView,
                             ListarUsuarioView listarUsuarioView,
                             MensajeInternacionalizacionHandler mensajeHandler,
                             PreguntaDAO preguntaDAO) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.userRegistroView = userRegistroView;
        this.listarUsuarioView = listarUsuarioView;
        this.mensajeHandler = mensajeHandler;
        this.preguntaDAO = preguntaDAO;

        cargarEventosLogin();
        cargarEventosRegistro();
        cargarEventosListarUsuarios();
    }

    private void cargarEventosLogin() {
        loginView.getBtnIniciarSesion().addActionListener(e -> {
            if (autenticar()) {
                userRegistroView.dispose();
                loginView.dispose();
            } else {
                loginView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.login.error"));
            }
        });

        loginView.getBtnRegistro().addActionListener(e -> {
            loginView.dispose();
            userRegistroView.setVisible(true);
        });

        loginView.getBtnRecuperar().addActionListener(e -> recuperarContrasenia());
    }

    private void cargarEventosRegistro() {
        userRegistroView.getBtnConfirmar().addActionListener(e -> {
            String username = userRegistroView.getTxtUsername().getText().trim();
            String contrasena1 = new String(userRegistroView.getPswContra().getPassword());
            String contrasena2 = new String(userRegistroView.getPswContra2().getPassword());

            if (username.isEmpty() || contrasena1.isEmpty() || contrasena2.isEmpty()) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.error.camposVacios"));
                return;
            }

            if (!contrasena1.equals(contrasena2)) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.register.contrasenaRepetida"));
                return;
            }

            if (usuarioDAO.buscarPorUsername(username) != null) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.error.nombreUsado"));
                return;
            }

            Usuario nuevoUsuario = new Usuario(username, contrasena1, Rol.CLIENTE);

            List<Pregunta> todas = preguntaDAO.listarTodas();
            if (todas.size() < 3) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.error.sinPreguntas"));
                return;
            }

            Collections.shuffle(todas);
            Set<Pregunta> preguntasUnicas = new LinkedHashSet<>();
            for (Pregunta p : todas) {
                preguntasUnicas.add(p);
                if (preguntasUnicas.size() == 3) break;
            }

            userRegistroView.dispose();

            UsuarioPreguntaView pv = new UsuarioPreguntaView(
                    new ArrayList<>(preguntasUnicas), nuevoUsuario, mensajeHandler,
                    () -> {
                        String nombreCompleto = JOptionPane.showInputDialog(null, mensajeHandler.get("mensaje.ingrese.nombre"));
                        String fechaNacimiento = JOptionPane.showInputDialog(null, mensajeHandler.get("mensaje.ingrese.fecha"));
                        String correo = JOptionPane.showInputDialog(null, mensajeHandler.get("mensaje.ingrese.correo"));
                        String telefono = JOptionPane.showInputDialog(null, mensajeHandler.get("mensaje.ingrese.telefono"));

                        if (nombreCompleto == null || fechaNacimiento == null || correo == null || telefono == null ||
                                nombreCompleto.trim().isEmpty() || fechaNacimiento.trim().isEmpty() ||
                                correo.trim().isEmpty() || telefono.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, mensajeHandler.get("mensaje.usuario.error.camposVacios"));
                            loginView.setVisible(true);
                            return;
                        }

                        if (!validarCorreo(correo)) {
                            JOptionPane.showMessageDialog(null, mensajeHandler.get("mensaje.usuario.error.email"));
                            loginView.setVisible(true);
                            return;
                        }

                        if (!validarTelefono(telefono)) {
                            JOptionPane.showMessageDialog(null, mensajeHandler.get("mensaje.usuario.error.telefono"));
                            loginView.setVisible(true);
                            return;
                        }

                        if (!validarFecha(fechaNacimiento)) {
                            JOptionPane.showMessageDialog(null, mensajeHandler.get("mensaje.usuario.error.fecha"));
                            loginView.setVisible(true);
                            return;
                        }

                        nuevoUsuario.setNombreCompleto(nombreCompleto);
                        nuevoUsuario.setFechaNacimiento(fechaNacimiento);
                        nuevoUsuario.setCorreo(correo);
                        nuevoUsuario.setTelefono(telefono);

                        usuarioDAO.crear(nuevoUsuario);
                        JOptionPane.showMessageDialog(null, mensajeHandler.get("mensaje.usuario.registrado"));
                        loginView.setVisible(true);
                    }
            );
            pv.setVisible(true);
        });

        userRegistroView.getBtnCancelar().addActionListener(e -> {
            userRegistroView.dispose();
            loginView.setVisible(true);
        });
    }

    private boolean validarCorreo(String correo) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, correo);
    }

    private boolean validarTelefono(String telefono) {
        return telefono.matches("^\\d{7,10}$");
    }

    private boolean validarFecha(String fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void cargarEventosListarUsuarios() {
        listarUsuarioView.getBtnListarTodos().addActionListener(e ->
                listarUsuarioView.cargarDatos(usuarioDAO.listarTodos())
        );

        listarUsuarioView.getBtnClientes().addActionListener(e ->
                listarUsuarioView.cargarDatos(usuarioDAO.listarClientes())
        );

        listarUsuarioView.getBtnAdmin().addActionListener(e ->
                listarUsuarioView.cargarDatos(usuarioDAO.listarAdmin())
        );

        listarUsuarioView.getBtnBuscar().addActionListener(e -> {
            String username = listarUsuarioView.getTxtUsername().getText().trim();
            Usuario u = usuarioDAO.buscarPorUsername(username);
            if (u != null) {
                listarUsuarioView.cargarDatos(List.of(u));
            } else {
                listarUsuarioView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.buscarUsername.noEncontrado"));
            }
        });
    }

    public boolean autenticar() {
        String username = loginView.getTxtUsername().getText().trim();
        String password = new String(loginView.getTxtPassword().getPassword());
        usuario = usuarioDAO.autenticar(username, password);

        if (usuario == null) {
            return false;
        } else {
            Main.iniciarApp(usuario, "es", "EC");
            return true;
        }
    }

    private void recuperarContrasenia() {
        String username = JOptionPane.showInputDialog(loginView, mensajeHandler.get("mensaje.recuperar.ingreseUsuario"));
        if (username == null || username.trim().isEmpty()) return;

        Usuario u = usuarioDAO.buscarPorUsername(username.trim());
        if (u == null) {
            JOptionPane.showMessageDialog(loginView, mensajeHandler.get("mensaje.recuperar.usuarioNoExiste"));
            return;
        }

        List<RespuestaSeguridad> respuestas = u.getRespuestasSeguridad();
        if (respuestas == null || respuestas.size() < 3) {
            JOptionPane.showMessageDialog(loginView, mensajeHandler.get("mensaje.recuperar.usuarioSinPreguntas"));
            return;
        }

        PreguntasModificarView vista = new PreguntasModificarView(mensajeHandler);
        vista.mostrarPreguntasDelUsuario(respuestas);
        vista.setVisible(true);

        vista.getBtnVerificar().addActionListener(ev -> {
            Map<Pregunta, String> ingresadas = vista.getRespuestasIngresadas();
            boolean todasCorrectas = true;

            for (RespuestaSeguridad r : respuestas) {
                boolean encontrada = ingresadas.entrySet().stream()
                        .anyMatch(entry -> entry.getKey().getId() == r.getPregunta().getId()
                                && entry.getValue().equalsIgnoreCase(r.getRespuesta()));
                if (!encontrada) {
                    todasCorrectas = false;
                    break;
                }
            }

            if (todasCorrectas) {
                String nueva = JOptionPane.showInputDialog(vista, mensajeHandler.get("mensaje.recuperar.nuevaContra"));
                if (nueva != null && !nueva.isEmpty()) {
                    u.setPassword(nueva);
                    JOptionPane.showMessageDialog(vista, mensajeHandler.get("mensaje.recuperar.exito"));
                    vista.dispose();
                } else {
                    JOptionPane.showMessageDialog(vista, mensajeHandler.get("mensaje.recuperar.vacia"));
                }
            } else {
                JOptionPane.showMessageDialog(vista, mensajeHandler.get("mensaje.recuperar.fallido"));
                vista.limpiarCampos();
            }
        });
    }

    public Usuario getUsuarioAutenticado() {
        return usuario;
    }
}
