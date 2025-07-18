package ec.edu.ups.vista;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.excepciones.PersistenciaException;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class ListarUsuarioView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtCedula; // Cambiado: era txtUsername
    private JTable tblUsuarios;
    private JButton btnBuscar;
    private JButton btnClientes;
    private JButton btnAdmin;
    private JButton btnListarTodos;
    private JLabel lblListar;
    private JLabel lblCedulaUsuarioView; // Cambiado: era lblUsernameUsuarioView
    private DefaultTableModel modelo;
    private UsuarioDAO usuarioDAO;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public ListarUsuarioView(UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler mensajeHandler) {
        this.usuarioDAO = usuarioDAO;
        this.mensajeHandler = mensajeHandler;
        setContentPane(panelPrincipal);

        // Configurar propiedades del JInternalFrame
        setTitle(mensajeHandler.get("panel.usuario.listar"));
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setSize(1000, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        // Verificar que panelPrincipal no sea null
        if (panelPrincipal == null) {
            throw new IllegalStateException("panelPrincipal no está inicializado. Verifica el archivo .form.");
        }

        // Establecer el contentPane


        // Inicializar el modelo de la tabla
        modelo = new DefaultTableModel(new Object[]{
                mensajeHandler.get("usuario.listar.tabla.usuario"),
                mensajeHandler.get("usuario.listar.tabla.rol"),
                mensajeHandler.get("usuario.listar.tabla.eliminar")
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblUsuarios.setModel(modelo);
        tblUsuarios.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());


        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tblUsuarios.rowAtPoint(e.getPoint());
                int column = tblUsuarios.columnAtPoint(e.getPoint());
                if (row >= 0 && column == 2) {
                    String cedula = tblUsuarios.getValueAt(row, 0).toString();
                    Rol rol = Rol.valueOf(tblUsuarios.getValueAt(row, 1).toString());

                    if (rol == Rol.CLIENTE) {
                        int confirm = JOptionPane.showConfirmDialog(
                                ListarUsuarioView.this,
                                mensajeHandler.get("usuario.eliminar.confirmacion").replace("{0}", cedula),
                                mensajeHandler.get("usuario.eliminar.titulo"),
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            try {
                                usuarioDAO.eliminar(cedula);
                            } catch (PersistenciaException ex) {
                                throw new RuntimeException(ex);
                            }
                            cargarDatos(usuarioDAO.listarTodos());
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                ListarUsuarioView.this,
                                mensajeHandler.get("usuario.listar.tabla.nopermitido")
                        );
                    }
                }
            }
        });

        // Configurar eventos de los botones
        btnListarTodos.addActionListener(e -> cargarDatos(usuarioDAO.listarTodos()));
        btnClientes.addActionListener(e -> cargarDatos(usuarioDAO.listarClientes()));
        btnAdmin.addActionListener(e -> cargarDatos(usuarioDAO.listarAdmin()));
        btnBuscar.addActionListener(e -> {
            String cedula = txtCedula.getText().trim();
            Usuario u = usuarioDAO.buscarPorUsername(cedula);
            if (u != null) {
                cargarDatos(List.of(u));
            } else {
                mostrarMensaje(mensajeHandler.get("usuario.listar.mensaje.noencontrado"));
            }
        });

        // Configurar textos de la interfaz
        setTextos();

        // Hacer visible la ventana al final
        setVisible(true);
    }

    public void setTextos() {
        setTitle(mensajeHandler.get("usuario.listar.titulo"));
        if (lblListar != null) lblListar.setText(mensajeHandler.get("usuario.listar.lbl.titulo"));
        if (lblCedulaUsuarioView != null) lblCedulaUsuarioView.setText(mensajeHandler.get("usuario.listar.lbl.usuario")); // Cambiado: era lblUsernameUsuarioView, ahora lblCedulaUsuarioView
        if (btnBuscar != null) btnBuscar.setText(mensajeHandler.get("usuario.listar.btn.buscar"));
        if (btnClientes != null) btnClientes.setText(mensajeHandler.get("usuario.listar.btn.clientes"));
        if (btnAdmin != null) btnAdmin.setText(mensajeHandler.get("usuario.listar.btn.admin"));
        if (btnListarTodos != null) btnListarTodos.setText(mensajeHandler.get("usuario.listar.btn.todos"));
        if (panelPrincipal.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
            border.setTitle(mensajeHandler.get("usuario.listar.panel.titulo"));
            panelPrincipal.repaint();
        }

        modelo.setColumnIdentifiers(new Object[]{
                mensajeHandler.get("usuario.listar.tabla.usuario"),
                mensajeHandler.get("usuario.listar.tabla.rol"),
                mensajeHandler.get("usuario.listar.tabla.eliminar")
        });
    }

    public JTextField getTxtCedula() { // Cambiado: era getTxtUsername
        return txtCedula;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnClientes() {
        return btnClientes;
    }

    public JButton getBtnAdmin() {
        return btnAdmin;
    }

    public JButton getBtnListarTodos() {
        return btnListarTodos;
    }

    public void cargarDatos(List<Usuario> listaUsuarios) {
        modelo.setRowCount(0);
        for (Usuario usuario : listaUsuarios) {
            modelo.addRow(new Object[]{usuario.getCedula(), usuario.getRol().name(), mensajeHandler.get("usuario.listar.tabla.eliminar")});
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Rol rol = Rol.valueOf(table.getValueAt(row, 1).toString());
            if (rol == Rol.CLIENTE) {
                setText(mensajeHandler.get("usuario.listar.tabla.eliminar"));
                setEnabled(true);
            } else {
                setText(mensajeHandler.get("usuario.listar.tabla.nopermitido"));
                setEnabled(false);
            }
            return this;
        }
    }
    /**
     * Actualiza el mensajeHandler y refresca los textos para nuevo idioma.
     */
    public void actualizarMensajeHandler(MensajeInternacionalizacionHandler nuevoHandler) {
        this.mensajeHandler = nuevoHandler;
        setTextos();
    }


    public void setIconos() {
        setIconoEscalado(btnBuscar, "/ios-search.png", 20, 20);
        setIconoEscalado(btnListarTodos, "/md-paper.png", 20, 20);
        setIconoEscalado(btnAdmin, "/md-star-outline.png", 20, 20);
        setIconoEscalado(btnClientes, "/ios-man.png", 20, 20);
    }
    private void setIconoEscalado(JButton boton, String ruta, int ancho, int alto) {
        URL url = getClass().getResource(ruta);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
        }
    }



}