package ec.edu.ups.controlador;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.excepciones.PersistenciaException;
import ec.edu.ups.excepciones.ValidacionException;
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
    private UsuarioView usuarioView;

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
        cargarEventoGuardarDatos();
    }

    private void cargarEventosLogin() {
        loginView.getBtnIniciarSesion().addActionListener(e -> {
            // Cambiado: ahora se usa la cédula en vez de username
            String cedula = loginView.getTxtCedula().getText().trim();
            String password = new String(loginView.getTxtPassword().getPassword());
            if (cedula.isEmpty() || password.isEmpty()) {
                loginView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.error.camposVacios"));
                return;
            }
            if (!validarCedula(cedula)) {
                loginView.mostrarMensaje(mensajeHandler.get("usuario.error.cedulaInvalida"));
                return;
            }
            Usuario usuarioAutenticado = usuarioDAO.autenticar(cedula, password);
            if (usuarioAutenticado != null) {

                this.usuario = usuarioAutenticado;

                loginView.dispose();
                Main.iniciarApp(usuarioAutenticado, "es", "EC"); // Cambio: mostrar PrincipalView tras login
            } else {
                loginView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.login.error"));
            }
        });

        loginView.getBtnRegistro().addActionListener(e -> {
            loginView.setVisible(false);
            userRegistroView.setVisible(true);
            userRegistroView.limpiarTodo();
        });

        loginView.getBtnRecuperar().addActionListener(e -> {
            try {
                recuperarContrasenia();
            } catch (ValidacionException | PersistenciaException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void cargarEventosRegistro() {
        userRegistroView.getBtnConfirmar().addActionListener(e -> {
            // Cambiado: ahora se usa la cédula en vez de username
            String cedula = userRegistroView.getTxtCedula().getText().trim();
            String contrasena1 = new String(userRegistroView.getPswContra().getPassword());
            String contrasena2 = new String(userRegistroView.getPswContra2().getPassword());
            String nombreCompleto = userRegistroView.getTxtNombreCompleto().getText().trim();
            String correo = userRegistroView.getTxtCorreo().getText().trim();
            String telefono = userRegistroView.getTxtTelefono().getText().trim();
            String fechaNacimiento = userRegistroView.getTxtFechaNacimiento().getText().trim();
            // Se obtiene el género seleccionado del ComboBox
            String genero = (String) userRegistroView.getCmbGenero().getSelectedItem();


            if (cedula.isEmpty() || contrasena1.isEmpty() || contrasena2.isEmpty()
                    || nombreCompleto.isEmpty() || correo.isEmpty() || telefono.isEmpty() || fechaNacimiento.isEmpty()
                    || genero == null) { // Se valida que se haya seleccionado un género

                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.error.camposVacios"));
                return;
            }

            if (!validarCedula(cedula)) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("usuario.error.cedulaInvalida"));
                return;
            }

            if (!contrasena1.equals(contrasena2)) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.register.contrasenaRepetida"));
                return;
            }

            if (usuarioDAO.buscarPorUsername(cedula) != null) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.error.nombreUsado"));
                return;
            }

            if (!validarCorreo(correo)) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.error.email"));
                return;
            }

            if (!validarTelefono(telefono)) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.error.telefono"));
                return;
            }

            if (!validarFecha(fechaNacimiento)) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.error.fecha"));
                return;
            }

            Usuario nuevoUsuario = null;
            try {
                nuevoUsuario = new Usuario(cedula, contrasena1, Rol.CLIENTE);
            } catch (ValidacionException ex) {
                throw new RuntimeException(ex);
            }
            nuevoUsuario.setNombreCompleto(nombreCompleto);
            nuevoUsuario.setCorreo(correo);
            nuevoUsuario.setTelefono(telefono);
            nuevoUsuario.setFechaNacimiento(fechaNacimiento);
            nuevoUsuario.setGenero(genero);

            List<Pregunta> todas = preguntaDAO.listarTodas();
            if (todas.size() < 3) {
                userRegistroView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.error.sinPreguntas"));
                   return;
            }

            Collections.shuffle(todas);
            Set<Pregunta> preguntasUnicas = new LinkedHashSet<>();
            for (Pregunta p : todas) {
                preguntasUnicas.add(p);
                if (preguntasUnicas.size() == 10) break;
            }

            userRegistroView.dispose();

            UsuarioPreguntaView pv = new UsuarioPreguntaView(mensajeHandler);
            cargarPreguntasEnVista(pv, new ArrayList<>(preguntasUnicas));
            Usuario finalNuevoUsuario = nuevoUsuario;
            cargarEventosUsuarioPregunta(pv, new ArrayList<>(preguntasUnicas), nuevoUsuario,
                    () -> {
                        try {
                            usuarioDAO.crear(finalNuevoUsuario);
                        } catch (PersistenciaException ex) {
                            throw new RuntimeException(ex);
                        }
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
        //METODOS DE VALIDACION
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

    // Validación de cédula ecuatoriana
    private boolean validarCedula(String cedula) {
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
            // Cambiado: ahora se usa la cédula en vez de username
            String cedula = listarUsuarioView.getTxtCedula().getText().trim();
            Usuario u = usuarioDAO.buscarPorUsername(cedula);
            if (u != null) {
                listarUsuarioView.cargarDatos(List.of(u));
            } else {
                listarUsuarioView.mostrarMensaje(mensajeHandler.get("mensaje.usuario.buscarUsername.noEncontrado"));
            }
        });
    }

    public boolean autenticar() {
        // Cambiado: ahora se usa la cédula en vez de username
        String cedula = loginView.getTxtCedula().getText().trim();
        String password = new String(loginView.getTxtPassword().getPassword());
        usuario = usuarioDAO.autenticar(cedula, password);
        if (usuario == null) {
            return false;
        } else {
            Main.iniciarApp(usuario, "es", "EC");
            return true;
        }
    }

    public void cargarDatosUsuarioEnVista() {
        if (usuarioView == null || usuario == null) return;
        usuarioView.cargarDatosUsuario(usuario); // ✅ Llamada centralizada a la vista
    }



    private void cargarEventoGuardarDatos() {
        if (usuarioView == null) return;

        usuarioView.getBtnGuardar().addActionListener(e -> {
            // --- INICIO DE LA CORRECCIÓN ---
            // 1. VERIFICAR QUE LA SESIÓN DEL USUARIO SIGUE ACTIVA
            if (usuario == null) {
                // Si el objeto 'usuario' es nulo, significa que la sesión se ha perdido.
                // Mostramos un mensaje de error claro y evitamos el NullPointerException.
                JOptionPane.showMessageDialog(usuarioView,
                        "La sesión ha expirado o es inválida. Por favor, inicie sesión de nuevo.",
                        "Error de Sesión",
                        JOptionPane.ERROR_MESSAGE);
                // Opcionalmente, podrías cerrar esta ventana y mostrar la de login.
                // usuarioView.dispose();
                // loginView.setVisible(true);
                return; // Detenemos la ejecución del método para no causar el error.
            }
            // --- FIN DE LA CORRECCIÓN ---

            // 2. SI LA SESIÓN ES VÁLIDA, CONTINUAR CON LA LÓGICA EXISTENTE
            String nombre = usuarioView.getTxtNombre().getText().trim();
            String correo = usuarioView.getTxtCorreoUser().getText().trim();
            String telefono = usuarioView.getTxtTelefono().getText().trim();
            String fechaNac = usuarioView.getTxtFechaNac().getText().trim();
            String genero = usuarioView.getTxtGenero().getText().trim();

            // Se añade el género a la validación de campos vacíos
            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || fechaNac.isEmpty() || genero.isEmpty()) {
                JOptionPane.showMessageDialog(usuarioView,
                        mensajeHandler.get("mensaje.usuario.error.camposVacios"));
                return;
            }

            if (!validarCorreo(correo)) {
                JOptionPane.showMessageDialog(usuarioView,
                        mensajeHandler.get("mensaje.usuario.error.email"));
                return;
            }

            if (!validarTelefono(telefono)) {
                JOptionPane.showMessageDialog(usuarioView,
                        mensajeHandler.get("mensaje.usuario.error.telefono"));
                return;
            }

            if (!validarFecha(fechaNac)) {
                JOptionPane.showMessageDialog(usuarioView,
                        mensajeHandler.get("mensaje.usuario.error.fecha"));
                return;
            }

            usuario.setNombreCompleto(nombre);
            usuario.setCorreo(correo);
            usuario.setTelefono(telefono);
            usuario.setFechaNacimiento(fechaNac);
            // Se asigna el género al objeto usuario antes de guardarlo
            usuario.setGenero(genero);

            try {
                usuarioDAO.actualizar(usuario);
            } catch (PersistenciaException ex) {
                throw new RuntimeException(ex);
            }

            JOptionPane.showMessageDialog(usuarioView,
                    mensajeHandler.get("mensaje.usuario.datosActualizados"));
        });
    }

    public void cargarPreguntasEnVista(UsuarioPreguntaView view, List<Pregunta> preguntas) {
        view.getCmbPreguntas().removeAllItems();
        for (Pregunta p : preguntas) {
            view.getCmbPreguntas().addItem(p.getTexto());
        }
        view.getTxtAreaRespuestas().setEditable(false);
        view.getTxtAreaRespuestas().setText("");
    }

    public void cargarEventosUsuarioPregunta(UsuarioPreguntaView view, List<Pregunta> preguntas, Usuario usuario, Runnable callback) {
        Map<String, Pregunta> preguntaMap = new HashMap<>();
        for (Pregunta p : preguntas) {
            preguntaMap.put(p.getTexto(), p);
        }

        view.getBtnGuardar().addActionListener(e -> {
            String textoPregunta = (String) view.getCmbPreguntas().getSelectedItem();
            String respuesta = view.getTxtRespuesta().getText().trim();

            if (textoPregunta == null || respuesta.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                        mensajeHandler.get("preguntas.error.camposVacios"));
                return;
            }

            Pregunta pregunta = preguntaMap.get(textoPregunta);

            for (RespuestaSeguridad r : usuario.getRespuestasSeguridad()) {
                if (r.getPregunta().getId() == pregunta.getId()) {
                    JOptionPane.showMessageDialog(view,
                            mensajeHandler.get("preguntas.error.yaRespondida"));
                    return;
                }
            }

            usuario.getRespuestasSeguridad().add(new RespuestaSeguridad(pregunta, respuesta));
            actualizarAreaRespuestas(view, usuario);
            view.getTxtRespuesta().setText("");
        });

        view.getBtnFinalizar().addActionListener(e -> {
            if (usuario.getRespuestasSeguridad().size() < 3) {
                JOptionPane.showMessageDialog(view,
                        mensajeHandler.get("preguntas.error.minimoTres"));
                return;
            }

            int confirmar = JOptionPane.showConfirmDialog(view,
                    mensajeHandler.get("preguntas.confirmar"),
                    mensajeHandler.get("preguntas.titulo"),
                    JOptionPane.YES_NO_OPTION);

            if (confirmar == JOptionPane.YES_OPTION) {
                view.dispose();
                callback.run();
            }
        });
    }

    private void actualizarAreaRespuestas(UsuarioPreguntaView view, Usuario usuario) {
        StringBuilder sb = new StringBuilder();
        for (RespuestaSeguridad r : usuario.getRespuestasSeguridad()) {
            sb.append("- ").append(r.getPregunta().getTexto()).append("\n");
            sb.append("   ").append(r.getRespuesta()).append("\n\n");
        }
        view.getTxtAreaRespuestas().setText(sb.toString());
    }

    private void recuperarContrasenia() throws ValidacionException, PersistenciaException {
        String username = JOptionPane.showInputDialog(loginView, mensajeHandler.get("mensaje.recuperar.ingreseUsuario"));
        if (username == null || username.trim().isEmpty()) return;

        Usuario u = usuarioDAO.buscarPorUsername(username.trim());
        if (u == null) {
            JOptionPane.showMessageDialog(loginView, mensajeHandler.get("mensaje.recuperar.usuarioNoExiste"));
            return;
        }

        List<RespuestaSeguridad> respuestas = u.getRespuestasSeguridad();
        if (respuestas == null || respuestas.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, mensajeHandler.get("mensaje.recuperar.usuarioSinPreguntas"));
            return;
        }

        Collections.shuffle(respuestas);
        RespuestaSeguridad preguntaElegida = respuestas.get(0);

        String textoPregunta = preguntaElegida.getPregunta().getTexto();
        String respuestaCorrecta = preguntaElegida.getRespuesta();

        String respuestaUsuario = JOptionPane.showInputDialog(
                loginView,
                mensajeHandler.get("mensaje.recuperar.ingreseRespuesta") + "\n\n" + textoPregunta
        );

        if (respuestaUsuario == null || respuestaUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(loginView, mensajeHandler.get("mensaje.recuperar.vacia"));
            return;
        }

        if (respuestaCorrecta.equalsIgnoreCase(respuestaUsuario.trim())) {
            String nueva = JOptionPane.showInputDialog(loginView, mensajeHandler.get("mensaje.recuperar.nuevaContra"));
            if (nueva != null && !nueva.isEmpty()) {
                u.setPassword(nueva);
                usuarioDAO.actualizar(u);
                JOptionPane.showMessageDialog(loginView, mensajeHandler.get("mensaje.recuperar.exito"));
            } else {
                JOptionPane.showMessageDialog(loginView, mensajeHandler.get("mensaje.recuperar.vacia"));
            }
        } else {
            JOptionPane.showMessageDialog(loginView, mensajeHandler.get("mensaje.recuperar.fallido"));
        }
    }


    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    public void setUsuarioView(UsuarioView usuarioView) {
        this.usuarioView = usuarioView;
        cargarEventoGuardarDatos();
        cargarEventoRecuperar();
    }
    private void cargarEventoRecuperar() {
        if (usuarioView == null || usuario == null) return;

        usuarioView.getBtnRecuperar().addActionListener(e -> {
            JPasswordField passwordField = new JPasswordField();
            int option = JOptionPane.showConfirmDialog(
                    usuarioView,
                    passwordField,
                    mensajeHandler.get("mensaje.recuperar.nuevaContra"),
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (option == JOptionPane.OK_OPTION) {
                String nuevaContra = new String(passwordField.getPassword());
                if (nuevaContra.isEmpty()) {
                    JOptionPane.showMessageDialog(usuarioView, mensajeHandler.get("mensaje.recuperar.vacia"));
                    return;
                }
                try {
                    usuario.setPassword(nuevaContra);
                } catch (ValidacionException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    usuarioDAO.actualizar(usuario);
                } catch (PersistenciaException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(usuarioView, mensajeHandler.get("mensaje.recuperar.exito"));
            }
        });
    }
}
