package ec.edu.ups.vista;

import javax.swing.*;
public class ProductoEliminarView extends JFrame{
    private JPanel panelPrincipal;
    public ProductoEliminarView() {
        setContentPane(panelPrincipal);
        setTitle("Datos del Producto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        //pack();
    }
}
