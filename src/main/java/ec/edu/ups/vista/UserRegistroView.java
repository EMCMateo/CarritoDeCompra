package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class UserRegistroView extends JFrame{
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JPasswordField pswContra;
    private JPasswordField pswContra2;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblPasswordConfirm;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    public UserRegistroView( MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler){
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        setTextos(mensajeInternacionalizacionHandler);
        setContentPane(panelPrincipal);
        setSize(500,500);
        setTitle("Registro de Cliente");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
    }
    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void limpiarCampos() {
        txtUsername.setText("");
        pswContra.setText("");
        pswContra2.setText("");
    }


    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }

    public JPasswordField getPswContra2() {
        return pswContra2;
    }

    public JPasswordField getPswContra() {
        return pswContra;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTextos(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("registro.titulo"));
        btnConfirmar.setText(mensajeHandler.get("registro.btn.confirmar"));
        btnCancelar.setText(mensajeHandler.get("registro.btn.cancelar"));
        lblUsername.setText(mensajeHandler.get("registro.label.usuario"));
        lblPassword.setText(mensajeHandler.get("registro.label.contraseña"));
        lblPasswordConfirm.setText(mensajeHandler.get("registro.label.repetir"));

    }
}
