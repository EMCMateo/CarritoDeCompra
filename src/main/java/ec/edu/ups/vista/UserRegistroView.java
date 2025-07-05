package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class UserRegistroView extends JFrame {
    private JPanel panelPrincipal;
    private JLabel lblNombreCompleto;
    private JTextField txtNombreCompleto;
    private JLabel lblCorreo;
    private JTextField txtCorreo;
    private JLabel lblTelefono;
    private JTextField txtTelefono;
    private JLabel lblFechaNacimiento;
    private JTextField txtFechaNacimiento;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JLabel lblPassword;
    private JPasswordField pswContra;
    private JLabel lblPasswordConfirm;
    private JPasswordField pswContra2;
    private JButton btnConfirmar;
    private JButton btnCancelar;

    private MensajeInternacionalizacionHandler mensajeHandler;

    public UserRegistroView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setContentPane(panelPrincipal);
        setResizable(true);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setTitle(mensajeHandler.get("usuario.registro.panel.titulo"));
        setTextos();
    }


    public void setTextos() {
        setTitle(mensajeHandler.get("usuario.registro.panel.titulo"));

        lblNombreCompleto.setText(mensajeHandler.get("usuario.view.nombre"));
        lblCorreo.setText(mensajeHandler.get("usuario.view.correo"));
        lblTelefono.setText(mensajeHandler.get("usuario.view.telefono"));
        lblFechaNacimiento.setText(mensajeHandler.get("usuario.view.fechaNac"));

        lblUsername.setText(mensajeHandler.get("login.view.username"));
        lblPassword.setText(mensajeHandler.get("login.view.password"));
        lblPasswordConfirm.setText(mensajeHandler.get("login.view.repetir.password"));

        btnConfirmar.setText(mensajeHandler.get("usuario.view.guardar"));
        btnCancelar.setText(mensajeHandler.get("boton.cancelar"));
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JTextField getTxtNombreCompleto() {
        return txtNombreCompleto;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtFechaNacimiento() {
        return txtFechaNacimiento;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getPswContra() {
        return pswContra;
    }

    public JPasswordField getPswContra2() {
        return pswContra2;
    }

    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }



    public void limpiarTodo(){
        txtUsername.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");
        txtNombreCompleto.setText("");
        pswContra.setText("");
        pswContra2.setText("");
    }
}
