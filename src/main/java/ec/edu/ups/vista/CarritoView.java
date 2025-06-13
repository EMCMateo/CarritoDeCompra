package ec.edu.ups.vista;

import ec.edu.ups.modelo.ItemCarrito;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class CarritoView extends JFrame {

   private JLabel lblPrincipal;
   private JLabel lblProducto;
   private JComboBox comboBoxProducto;
   private JLabel lblCantidad;
   private JTextField txtCantidad;
   private JButton btnAceptar;
   private JButton btnCancelar;
   private JTextField textField1;
   private JPanel panelPrincipal;

   public CarritoView (){
      setContentPane(panelPrincipal);
      setTitle("Carrito de Compras");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(500, 500);
      setResizable(false);
      setLocationRelativeTo(null);
      setVisible(true);
      pack();
   }

   public JLabel getLblPrincipal() {
      return lblPrincipal;
   }

   public void setLblPrincipal(JLabel lblPrincipal) {
      this.lblPrincipal = lblPrincipal;
   }

   public JLabel getLblProducto() {
      return lblProducto;
   }

   public void setLblProducto(JLabel lblProducto) {
      this.lblProducto = lblProducto;
   }

   public JComboBox getComboBoxProducto() {
      return comboBoxProducto;
   }

   public void setComboBoxProducto(JComboBox comboBoxProducto) {
      this.comboBoxProducto = comboBoxProducto;
   }

   public JLabel getLblCantidad() {
      return lblCantidad;
   }

   public void setLblCantidad(JLabel lblCantidad) {
      this.lblCantidad = lblCantidad;
   }

   public JTextField getTxtCantidad() {
      return txtCantidad;
   }

   public void setTxtCantidad(JTextField txtCantidad) {
      this.txtCantidad = txtCantidad;
   }

   public JButton getBtnAceptar() {
      return btnAceptar;
   }

   public void setBtnAceptar(JButton btnAceptar) {
      this.btnAceptar = btnAceptar;
   }

   public JButton getBtnCancelar() {
      return btnCancelar;
   }

   public void setBtnCancelar(JButton btnCancelar) {
      this.btnCancelar = btnCancelar;
   }

   public JTextField getTextField1() {
      return textField1;
   }

   public void setTextField1(JTextField textField1) {
      this.textField1 = textField1;
   }

   public JPanel getPanelPrincipal() {
      return panelPrincipal;
   }

   public void setPanelPrincipal(JPanel panelPrincipal) {
      this.panelPrincipal = panelPrincipal;
   }

   public static void main(String [] args){
      new CarritoView();
   }
}