package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalView extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;
    private JPanel panelPrincipal;
    private JDesktopPane jDesktopPane;

    public PrincipalView(){

        jDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();
        menuProducto = new JMenu("Producto ");
        menuItemCrearProducto = new JMenuItem("Crear Producto");
        menuItemEliminarProducto = new JMenuItem("Eliminar Producto");
        menuItemActualizarProducto = new JMenuItem("Actualizar Producto");
        menuItemBuscarProducto = new JMenuItem("Buscar Producto");

        menuBar.add(menuProducto);
        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);

        setJMenuBar(menuBar);
        setContentPane(jDesktopPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        //pack();
        /*
        menuItemCrearProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame jInternalFrame = new JInternalFrame("Crear Producto: ");
                jInternalFrame.setSize(200,200);

                //jInternalFrame.setMaximizable(true);
                jInternalFrame.setClosable(true);
                //jInternalFrame.setResizable(true);
                //jInternalFrame.setIconifiable(true);
                jInternalFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
                jInternalFrame.setTitle("<<<Crear Productos>>>");



                jInternalFrame.setVisible(true);
                jDesktopPane.add(jInternalFrame);
            }
        });
        */

    }

    public JMenuItem getMenuItemCrearProducto() {
        return menuItemCrearProducto;
    }

    public void setMenuItemCrearProducto(JMenuItem menuItemCrearProducto) {
        this.menuItemCrearProducto = menuItemCrearProducto;
    }

    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) {
        this.menuItemEliminarProducto = menuItemEliminarProducto;
    }

    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    public void setMenuItemActualizarProducto(JMenuItem menuItemActualizarProducto) {
        this.menuItemActualizarProducto = menuItemActualizarProducto;
    }

    public JMenuItem getMenuItemBuscarProducto() {
        return menuItemBuscarProducto;
    }

    public void setMenuItemBuscarProducto(JMenuItem menuItemBuscarProducto) {
        this.menuItemBuscarProducto = menuItemBuscarProducto;
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    public void setjDesktopPane(JDesktopPane jDesktopPane) {
        this.jDesktopPane = jDesktopPane;
    }
}
