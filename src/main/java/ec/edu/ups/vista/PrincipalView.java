package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class PrincipalView extends JFrame {

    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuUsuarios;
    private JMenu menuIdiomas;
    private JMenu menuSalir;

    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;
    private JMenuItem menuItemCrearCarrito;
    private JMenuItem menuItemListarCarrito;
    private JMenuItem menuItemListarUsuarios;
    private JMenuItem menuItemCerrarSesion;
    private JMenuItem menuItemSalir;
    private JMenuItem menuItemES;
    private JMenuItem menuItemEN;
    private JMenuItem menuItemIT;
    private JPanel panelPrincipal;

    private JDesktopPane jDesktopPane;

    public PrincipalView(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;

        inicializarComponentes();
        setTextos(mensajeInternacionalizacionHandler);

        setContentPane(jDesktopPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarComponentes() {
        jDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();

        menuProducto = new JMenu();
        menuCarrito = new JMenu();
        menuUsuarios = new JMenu();
        menuIdiomas = new JMenu();
        menuSalir = new JMenu();

        menuItemCrearProducto = new JMenuItem();
        menuItemEliminarProducto = new JMenuItem();
        menuItemActualizarProducto = new JMenuItem();
        menuItemBuscarProducto = new JMenuItem();
        menuItemCrearCarrito = new JMenuItem();
        menuItemListarCarrito = new JMenuItem();
        menuItemListarUsuarios = new JMenuItem();
        menuItemCerrarSesion = new JMenuItem();
        menuItemSalir = new JMenuItem();
        menuItemES = new JMenuItem();
        menuItemEN = new JMenuItem();
        menuItemIT = new JMenuItem();

        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemListarCarrito);

        menuUsuarios.add(menuItemListarUsuarios);

        menuIdiomas.add(menuItemES);
        menuIdiomas.add(menuItemEN);
        menuIdiomas.add(menuItemIT);

        menuSalir.add(menuItemCerrarSesion);
        menuSalir.add(menuItemSalir);

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuUsuarios);
        menuBar.add(menuIdiomas);
        menuBar.add(menuSalir);

        setJMenuBar(menuBar);
    }

    public void setTextos(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        setTitle(mensajeInternacionalizacionHandler.get("app.titulo"));

        menuProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito"));
        menuUsuarios.setText(mensajeInternacionalizacionHandler.get("menu.usuarios"));
        menuIdiomas.setText(mensajeInternacionalizacionHandler.get("menu.idiomas"));
        menuSalir.setText(mensajeInternacionalizacionHandler.get("menu.salir"));

        menuItemCrearProducto.setText(mensajeInternacionalizacionHandler.get("menuitem.crearproducto"));
        menuItemEliminarProducto.setText(mensajeInternacionalizacionHandler.get("menuitem.eliminarproducto"));
        menuItemActualizarProducto.setText(mensajeInternacionalizacionHandler.get("menuitem.actualizarproducto"));
        menuItemBuscarProducto.setText(mensajeInternacionalizacionHandler.get("menuitem.buscarproducto"));
        menuItemCrearCarrito.setText(mensajeInternacionalizacionHandler.get("menuitem.crearcarrito"));
        menuItemListarCarrito.setText(mensajeInternacionalizacionHandler.get("menuitem.listarcarrito"));
        menuItemListarUsuarios.setText(mensajeInternacionalizacionHandler.get("menuitem.listarusuarios"));
        menuItemCerrarSesion.setText(mensajeInternacionalizacionHandler.get("menuitem.cerrarsesion"));
        menuItemSalir.setText(mensajeInternacionalizacionHandler.get("menuitem.salir"));
        menuItemES.setText(mensajeInternacionalizacionHandler.get("menuitem.es"));
        menuItemEN.setText(mensajeInternacionalizacionHandler.get("menuitem.en"));
        menuItemIT.setText(mensajeInternacionalizacionHandler.get("menuitem.it"));
    }

    // ==== Getters para controladores ====
    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }
    public JMenuItem getMenuItemCrearProducto() { return menuItemCrearProducto; }
    public JMenuItem getMenuItemEliminarProducto() { return menuItemEliminarProducto; }
    public JMenuItem getMenuItemActualizarProducto() { return menuItemActualizarProducto; }
    public JMenuItem getMenuItemBuscarProducto() { return menuItemBuscarProducto; }
    public JMenuItem getMenuItemCrearCarrito() { return menuItemCrearCarrito; }
    public JMenuItem getMenuItemListarCarrito() { return menuItemListarCarrito; }
    public JMenuItem getMenuItemListarUsuarios() { return menuItemListarUsuarios; }
    public JMenuItem getMenuItemCerrarSesion() { return menuItemCerrarSesion; }
    public JMenuItem getMenuItemSalir() { return menuItemSalir; }
    public JMenuItem getMenuItemES() { return menuItemES; }
    public JMenuItem getMenuItemEN() { return menuItemEN; }
    public JMenuItem getMenuItemIT() { return menuItemIT; }
}
