package ec.edu.ups.vista;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.List;

public class ListarUsuarioView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JTable tblUsuarios;
    private JButton btnBuscar;
    private JButton btnClientes;
    private JButton btnAdmin;
    private JButton btnListarTodos;
    private JLabel lblListar;
    private JLabel lblUsernameUsuarioView;
    private DefaultTableModel modelo;
    private UsuarioDAO usuarioDAO;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public ListarUsuarioView(UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler mensajeHandler) {
        this.usuarioDAO = usuarioDAO;
        this.mensajeHandler = mensajeHandler;
        this.setTitle(mensajeHandler.get("panel.usuario.listar"));
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);

        setContentPane(panelPrincipal);

        modelo = new DefaultTableModel(new Object[]{mensajeHandler.get("usuario.listar.tabla.usuario"), mensajeHandler.get("usuario.listar.tabla.rol"), mensajeHandler.get("usuario.listar.tabla.eliminar")}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblUsuarios.setModel(modelo);
        tblUsuarios.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());

// Añadir MouseListener que detecta clics en la columna del botón
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tblUsuarios.rowAtPoint(e.getPoint());
                int column = tblUsuarios.columnAtPoint(e.getPoint());
                if (row >= 0 && column == 2) {
                    String username = tblUsuarios.getValueAt(row, 0).toString();
                    Rol rol = Rol.valueOf(tblUsuarios.getValueAt(row, 1).toString());

                    if (rol == Rol.CLIENTE) {
                        int confirm = JOptionPane.showConfirmDialog(
                                ListarUsuarioView.this,
                                mensajeHandler.get("usuario.eliminar.confirmacion").replace("{0}", username),
                                mensajeHandler.get("usuario.eliminar.titulo"),
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            usuarioDAO.eliminar(username);
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



        // Eventos
        btnListarTodos.addActionListener(e -> cargarDatos(usuarioDAO.listarTodos()));
        btnClientes.addActionListener(e -> cargarDatos(usuarioDAO.listarClientes()));
        btnAdmin.addActionListener(e -> cargarDatos(usuarioDAO.listarAdmin()));
        btnBuscar.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            Usuario u = usuarioDAO.buscarPorUsername(username);
            if (u != null) {
                cargarDatos(List.of(u));
            } else {
                mostrarMensaje(mensajeHandler.get("usuario.listar.mensaje.noencontrado"));
            }
        });


        setTextos();
    }

    public void setTextos() {
        setTitle(mensajeHandler.get("usuario.listar.titulo"));
        if (lblListar != null) lblListar.setText(mensajeHandler.get("usuario.listar.lbl.titulo"));
        if (lblUsernameUsuarioView != null) lblUsernameUsuarioView.setText(mensajeHandler.get("usuario.listar.lbl.usuario"));
        if (btnBuscar != null) btnBuscar.setText(mensajeHandler.get("usuario.listar.btn.buscar"));
        if (btnClientes != null) btnClientes.setText(mensajeHandler.get("usuario.listar.btn.clientes"));
        if (btnAdmin != null) btnAdmin.setText(mensajeHandler.get("usuario.listar.btn.admin"));
        if (btnListarTodos != null) btnListarTodos.setText(mensajeHandler.get("usuario.listar.btn.todos"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajeHandler.get("usuario.listar.tabla.usuario"),
                mensajeHandler.get("usuario.listar.tabla.rol"),
                mensajeHandler.get("usuario.listar.tabla.eliminar")
        });
    }

    public JTextField getTxtUsername() {
        return txtUsername;
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
            modelo.addRow(new Object[]{usuario.getUsername(), usuario.getRol().name(), mensajeHandler.get("usuario.listar.tabla.eliminar")});
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








public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("usuario.listar.titulo"));
        lblListar.setText(mensajeHandler.get("usuario.listar.lbl.titulo"));
        lblUsernameUsuarioView.setText(mensajeHandler.get("usuario.listar.lbl.usuario"));

        btnBuscar.setText(mensajeHandler.get("usuario.listar.btn.buscar"));
        btnClientes.setText(mensajeHandler.get("usuario.listar.btn.clientes"));
        btnAdmin.setText(mensajeHandler.get("usuario.listar.btn.admin"));
        btnListarTodos.setText(mensajeHandler.get("usuario.listar.btn.todos"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajeHandler.get("usuario.listar.tabla.usuario"),
                mensajeHandler.get("usuario.listar.tabla.rol"),
                mensajeHandler.get("usuario.listar.tabla.eliminar")
        });
    }
}
