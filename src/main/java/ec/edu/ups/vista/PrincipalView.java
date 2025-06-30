package ec.edu.ups.vista;

import javax.swing.*;

public class PrincipalView extends JFrame {
    private JMenuBar menuBar;
    private JMenuItem menuItemCrearCarrito;
    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuSalir;
    private JMenu menuIdiomas;
    private JMenu menuUsuarios;
    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;
    private JMenuItem menuItemListarCarrito;
    private JMenuItem menuItemListarUsuarios;
    private JMenuItem menuItemSalir;
    private JMenuItem menuItemCerrarSesion;
    private JMenuItem menuItemES;
    private JMenuItem menuItemEN;
    private JMenuItem menuItemFR;

    private JPanel panelPrincipal;
    private JTable tblCarrito;
    private JDesktopPane jDesktopPane;

    public PrincipalView(){

        jDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();
        menuCarrito = new JMenu("Carrito");
        menuProducto = new JMenu("Producto ");
        menuUsuarios = new JMenu("Usuarios");
        menuIdiomas = new JMenu("Idiomas");
        menuSalir = new JMenu("Salir");

        menuItemCrearCarrito = new JMenuItem("Añadir Carrito");
        menuItemCrearProducto = new JMenuItem("Crear Producto");
        menuItemEliminarProducto = new JMenuItem("Eliminar Producto");
        menuItemActualizarProducto = new JMenuItem("Actualizar Producto");
        menuItemBuscarProducto = new JMenuItem("Buscar Producto");
        menuItemListarCarrito = new JMenuItem("Listar Carritos");
        menuItemSalir = new JMenuItem("Salir");
        menuItemCerrarSesion = new JMenuItem("Cerrar Sesion");
        menuItemListarUsuarios = new JMenuItem("Listar Usuarios");
        menuItemES = new JMenuItem("Español");
        menuItemEN = new JMenuItem("Inglés");
        menuItemFR = new JMenuItem("Francés");

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuUsuarios);
        menuBar.add(menuIdiomas);
        menuBar.add(menuSalir);
        menuProducto.add(menuItemCrearProducto);
        menuCarrito.add(menuItemCrearCarrito);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuUsuarios.add(menuItemListarUsuarios);
        menuProducto.add(menuItemBuscarProducto);
        menuCarrito.add(menuItemListarCarrito);
        menuSalir.add(menuItemSalir);
        menuSalir.add(menuItemCerrarSesion);
        menuIdiomas.add(menuItemES);
        menuIdiomas.add(menuItemEN);
        menuIdiomas.add(menuItemFR);

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

    public JMenuItem getMenuItemCrearCarrito() {
        return menuItemCrearCarrito;
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    public void setjDesktopPane(JDesktopPane jDesktopPane) {
        this.jDesktopPane = jDesktopPane;
    }

    public JMenuItem getMenuItemListarCarrito() {
        return menuItemListarCarrito;
    }

    public void setMenuItemListarCarrito(JMenuItem menuItemListarCarrito) {
        this.menuItemListarCarrito = menuItemListarCarrito;
    }

    public JMenu getMenuProducto() {
        return menuProducto;
    }

    public JMenu getMenuCarrito() {
        return menuCarrito;
    }

    public JMenu getMenuSalir() {
        return menuSalir;
    }

    public JMenu getMenuIdiomas() {
        return menuIdiomas;
    }

    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    public JMenuItem getMenuItemCerrarSesion() {
        return menuItemCerrarSesion;
    }

    public JMenuItem getMenuItemES() {
        return menuItemES;
    }

    public JMenuItem getMenuItemEN() {
        return menuItemEN;
    }

    public JMenuItem getMenuItemFR() {
        return menuItemFR;
    }

    public JMenuItem getMenuItemListarUsuarios() {
        return menuItemListarUsuarios;
    }

    public void setMenuItemListarUsuarios(JMenuItem menuItemListarUsuarios) {
        this.menuItemListarUsuarios = menuItemListarUsuarios;
    }
}
