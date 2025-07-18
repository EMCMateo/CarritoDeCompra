package ec.edu.ups.vista;

import javax.swing.*;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.awt.*;
import java.net.URL;

public class PrincipalView extends JFrame {

    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuUsuarios;
    private JMenu menuIdiomas;
    private JMenu menuSalir;
    private JMenu menuYo;

    private MiJDesktopPane desktop;

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
    private JMenuItem menutItemListarCarritoUsuario;
    private JMenuItem menuItemYo;
    private JMenuItem menuItemEditarCarrito;
    private JPanel panelPrincipal;


    public PrincipalView(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;


        inicializarComponentes();
        setTextos(mensajeInternacionalizacionHandler);

        setContentPane(desktop);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarComponentes() {
        desktop = new MiJDesktopPane(mensajeInternacionalizacionHandler);
        menuBar = new JMenuBar();

        menuYo = new JMenu();

        menuProducto = new JMenu();
        menuCarrito = new JMenu();
        menuUsuarios = new JMenu();
        menuIdiomas = new JMenu();
        menuSalir = new JMenu();


        menuItemCrearProducto = new JMenuItem();
        menuItemYo = new JMenuItem();
        menuItemEliminarProducto = new JMenuItem();
        menuItemActualizarProducto = new JMenuItem();
        menuItemBuscarProducto = new JMenuItem();
        menuItemCrearCarrito = new JMenuItem();
        menuItemListarCarrito = new JMenuItem();
        menuItemEditarCarrito = new JMenuItem();
        menuItemListarUsuarios = new JMenuItem();
        menuItemCerrarSesion = new JMenuItem();
        menuItemSalir = new JMenuItem();
        menuItemES = new JMenuItem();
        menuItemEN = new JMenuItem();
        menuItemIT = new JMenuItem();
        menutItemListarCarritoUsuario = new JMenuItem();

        menuYo.add(menuItemYo);

        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemListarCarrito);
        menuCarrito.add(menuItemEditarCarrito);
        menuCarrito.add(menutItemListarCarritoUsuario);

        menuUsuarios.add(menuItemListarUsuarios);

        menuIdiomas.add(menuItemES);
        menuIdiomas.add(menuItemEN);
        menuIdiomas.add(menuItemIT);

        menuSalir.add(menuItemCerrarSesion);
        menuSalir.add(menuItemSalir);


        menuBar.add(menuYo);
        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuUsuarios);
        menuBar.add(menuIdiomas);
        menuBar.add(menuSalir);


        setJMenuBar(menuBar);
    }

    public void setTextos(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        setTitle(mensajeInternacionalizacionHandler.get("app.titulo"));
        desktop.actualizarTextos();
        menuProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito"));
        menuUsuarios.setText(mensajeInternacionalizacionHandler.get("menu.usuarios"));
        menuIdiomas.setText(mensajeInternacionalizacionHandler.get("menu.idiomas"));
        menuSalir.setText(mensajeInternacionalizacionHandler.get("menu.salir"));
        menuYo.setText(mensajeInternacionalizacionHandler.get("menu.yo"));

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
        menuItemYo.setText(mensajeInternacionalizacionHandler.get("menu.yo"));
        menutItemListarCarritoUsuario.setText(mensajeInternacionalizacionHandler.get("menuitem.listarcarritousuario"));
        menuItemEditarCarrito.setText(mensajeInternacionalizacionHandler.get("menuitem.editarCarrito"));
    }

    // ==== Getters para controladores ====

    public JMenuItem getMenuItemCrearProducto() {
        return menuItemCrearProducto;
    }

    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    public JMenuItem getMenuItemBuscarProducto() {
        return menuItemBuscarProducto;
    }

    public JMenuItem getMenuItemCrearCarrito() {
        return menuItemCrearCarrito;
    }

    public JMenuItem getMenuItemListarCarrito() {
        return menuItemListarCarrito;
    }

    public JMenuItem getMenuItemListarUsuarios() {
        return menuItemListarUsuarios;
    }

    public JMenuItem getMenuItemCerrarSesion() {
        return menuItemCerrarSesion;
    }

    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    public JMenuItem getMenuItemES() {
        return menuItemES;
    }

    public JMenuItem getMenuItemEN() {
        return menuItemEN;
    }

    public JMenuItem getMenuItemIT() {
        return menuItemIT;
    }

    public JMenuItem getMenutItemListarCarritoUsuario() {
        return menutItemListarCarritoUsuario;
    }

    public JMenuItem getMenuItemYo() {
        return menuItemYo;
    }

    public MiJDesktopPane getDesktop() {
        return desktop;
    }

    public JMenuItem getMenuItemEditarCarrito() {
        return menuItemEditarCarrito;
    }


    public void setIconos() {
        setIcono(menuItemCrearProducto, "/ios-add-circle.png");
        setIcono(menuItemEliminarProducto, "/ios-trash.png");
        setIcono(menuItemActualizarProducto, "/ios-create.png");
        setIcono(menuItemBuscarProducto, "/ios-search.png");
        setIcono(menuItemCrearCarrito, "/md-add-circle.png");
        setIcono(menuItemListarCarrito, "/md-paper.png");
        setIcono(menuItemEditarCarrito, "/ios-create.png");
        setIcono(menuItemListarUsuarios, "/md-paper.png");
        setIcono(menuItemCerrarSesion, "/md-log-out.png");
        setIcono(menuItemSalir, "/md-close.png");
        setIcono(menuItemES, "/ios-airplane.png");
        setIcono(menuItemEN, "/ios-airplane.png");
        setIcono(menuItemIT, "/ios-airplane.png");
        setIcono(menuItemYo, "/md-person.png");
        setIcono(menutItemListarCarritoUsuario, "/md-paper.png");
    }

    private void setIcono(JMenuItem menuItem, String ruta) {
        URL url = getClass().getResource(ruta);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            menuItem.setIcon(new ImageIcon(imagenEscalada));
        }
    }


}
