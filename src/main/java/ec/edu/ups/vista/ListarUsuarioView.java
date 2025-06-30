package ec.edu.ups.vista;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
    private DefaultTableModel modelo;
    private UsuarioDAO usuarioDAO;

    public ListarUsuarioView(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;

        setTitle("Listado de Usuarios");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);

        modelo = new DefaultTableModel(new Object[]{"Usuario", "Rol", "Eliminar"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        tblUsuarios.setModel(modelo);
        tblUsuarios.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        tblUsuarios.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox()));



        setContentPane(panelPrincipal);

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
                mostrarMensaje("Usuario no encontrado.");
            }
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
            modelo.addRow(new Object[]{usuario.getUsername(), usuario.getRol(), "Eliminar"});
        }
        modelo.fireTableDataChanged();
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // ==== Renderizador de botón ====
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText((value == null) ? "Eliminar" : value.toString());
            return this;
        }
    }

    // ==== Editor de botón funcional ====
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String username;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> eliminarUsuario());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            username = table.getValueAt(row, 0).toString();
            Rol rol = Rol.valueOf(table.getValueAt(row, 1).toString());

            if (rol == Rol.CLIENTE) {
                button.setText("Eliminar");
                button.setEnabled(true);
            } else {
                button.setText("No permitido");
                button.setEnabled(false);
            }

            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Eliminar";
        }

        private void eliminarUsuario() {
            if (isPushed) {
                int confirm = JOptionPane.showConfirmDialog(
                        ListarUsuarioView.this,
                        "¿Desea eliminar al usuario \"" + username + "\"?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    usuarioDAO.eliminar(username);
                    cargarDatos(usuarioDAO.listarTodos());
                }
            }
            isPushed = false;
        }
    }

}
