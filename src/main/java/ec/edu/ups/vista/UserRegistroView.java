package ec.edu.ups.vista;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

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
    private JTextField txtRespuesta1;
    private JPanel panelFormulario;
    private JLabel lblPregunta1;
    private JLabel lblPregunta2;
    private JTextField txtRespuesta2;
    private JLabel lblPregunta3;
    private JTextField txtRespuesta3;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    private List<String> preguntasMostradas;

    public UserRegistroView(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler){
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;

        inicializarComponentes();
        setTextos(mensajeInternacionalizacionHandler);
    }

    private void inicializarComponentes() {


        setContentPane(panelPrincipal);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        setTextos(mensajeInternacionalizacionHandler);

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }


    public void mostrarMensaje(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void limpiarCampos() {
        txtUsername.setText("");
        pswContra.setText("");
        pswContra2.setText("");
    }

    public void mostrarPreguntas(List<String> preguntas) {
        this.preguntasMostradas = preguntas;

        if (preguntas.size() >= 3) {
            lblPregunta1.setText(preguntas.get(0));
            lblPregunta2.setText(preguntas.get(1));
            lblPregunta3.setText(preguntas.get(2));
        }
    }

    public List<String> getRespuestas() {
        List<String> respuestas = new ArrayList<>();
        respuestas.add(txtRespuesta1.getText().trim());
        respuestas.add(txtRespuesta2.getText().trim());
        respuestas.add(txtRespuesta3.getText().trim());
        return respuestas;
    }

    public List<String> getPreguntasMostradas() {
        return preguntasMostradas;
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

    public JTextField getTxtRespuesta1() {
        return txtRespuesta1;
    }

    public void setTxtRespuesta1(JTextField txtRespuesta1) {
        this.txtRespuesta1 = txtRespuesta1;
    }

    public JPanel getPanelFormulario() {
        return panelFormulario;
    }

    public void setPanelFormulario(JPanel panelFormulario) {
        this.panelFormulario = panelFormulario;
    }

    public JLabel getLblPregunta1() {
        return lblPregunta1;
    }

    public void setLblPregunta1(JLabel lblPregunta1) {
        this.lblPregunta1 = lblPregunta1;
    }

    public JLabel getLblPregunta2() {
        return lblPregunta2;
    }

    public void setLblPregunta2(JLabel lblPregunta2) {
        this.lblPregunta2 = lblPregunta2;
    }

    public JTextField getTxtRespuesta2() {
        return txtRespuesta2;
    }

    public void setTxtRespuesta2(JTextField txtRespuesta2) {
        this.txtRespuesta2 = txtRespuesta2;
    }

    public JLabel getLblPregunta3() {
        return lblPregunta3;
    }

    public void setLblPregunta3(JLabel lblPregunta3) {
        this.lblPregunta3 = lblPregunta3;
    }

    public JTextField getTxtRespuesta3() {
        return txtRespuesta3;
    }

    public void setTxtRespuesta3(JTextField txtRespuesta3) {
        this.txtRespuesta3 = txtRespuesta3;
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