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
      //setResizable(false);
      setLocationRelativeTo(null);
      setVisible(true);
      //pack();
   }

   public static void main(String [] args){
      new CarritoView();
   }
}