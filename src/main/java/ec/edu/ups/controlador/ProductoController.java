package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.util.List;
/**
 * Controlador para gestionar las operaciones relacionadas con los productos.
 * Permite añadir, listar, buscar, actualizar y eliminar productos.
 */

public class ProductoController {

    private final ProductoDAO productoDAO;
    private final MensajeInternacionalizacionHandler mensajeHandler;
    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoActualizarView productoActualizarView;
    private final ProductoEliminarView productoEliminarView;
    private final CarritoAñadirView carritoAñadirView;

    private boolean procesandoAnadir = false;
    private boolean procesandoActualizar = false;
    private boolean procesandoEliminar = false;
    /**
     * Constructor que inicializa el controlador con las vistas y el DAO de productos.
     *
     * @param productoDAO           DAO para operaciones de productos
     * @param productoAnadirView    Vista para añadir productos
     * @param productoListaView     Vista para listar y buscar productos
     * @param carritoAñadirView     Vista para añadir productos al carrito
     * @param productoEliminarView  Vista para eliminar productos
     * @param productoActualizarView Vista para actualizar productos
     * @param mensajeHandler        Handler para mensajes de internacionalización
     */
    public ProductoController(
            ProductoDAO productoDAO,
            ProductoAnadirView productoAnadirView,
            ProductoListaView productoListaView,
            CarritoAñadirView carritoAñadirView,
            ProductoEliminarView productoEliminarView,
            ProductoActualizarView productoActualizarView,
            MensajeInternacionalizacionHandler mensajeHandler) {

        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.carritoAñadirView = carritoAñadirView;
        this.productoEliminarView = productoEliminarView;
        this.productoActualizarView = productoActualizarView;
        this.mensajeHandler = mensajeHandler;

        inicializarEventos();
    }
    /**
     * Inicializa los eventos de las vistas.
     * Configura los listeners para añadir, listar, buscar, actualizar y eliminar productos.
     */

    private void inicializarEventos() {
        configurarEventosAnadir();
        configurarEventosLista();
        configurarEventosActualizar();
        configurarEventosEliminar();
    }
    /**
     * Configura los eventos para añadir productos.
     * Incluye validaciones y manejo de errores.
     */

    private void configurarEventosAnadir() {
        productoAnadirView.getBtnAceptar().addActionListener(e -> {
            if (procesandoAnadir) return;
            procesandoAnadir = true;

            try {
                String codTxt = productoAnadirView.getTxtCodigo().getText().trim();
                String nomTxt = productoAnadirView.getTxtNombre().getText().trim();
                String precTxt = productoAnadirView.getTxtPrecio().getText().trim();

                if (codTxt.isEmpty() || nomTxt.isEmpty() || precTxt.isEmpty()) {
                    productoAnadirView.mostrarMensaje(mensajeHandler.get("producto.datos.invalidos"));
                    return;
                }

                int codigo = Integer.parseInt(codTxt);
                String nombre = nomTxt;


                double precio = FormateadorUtils.parsearMonedaFlexible(precTxt);


                if (productoDAO.buscarPorCodigo(codigo) != null) {
                    productoAnadirView.mostrarMensaje(mensajeHandler.get("producto.codigo.repetido"));
                    return;
                }

                Producto p = new Producto(codigo, nombre, precio);
                productoDAO.crear(p);
                productoAnadirView.mostrarMensaje(mensajeHandler.get("producto.guardado"));
                productoAnadirView.limpiarCampos();

            } catch (NumberFormatException ex) {
                productoAnadirView.mostrarMensaje(mensajeHandler.get("producto.datos.invalidos"));
            } finally {
                procesandoAnadir = false;
            }
        });
    }


    /**
     * Configura los eventos para listar y buscar productos.
     * Incluye la carga de datos y manejo de errores.
     */

    private void configurarEventosLista() {
        productoListaView.getBtnListar().addActionListener(e -> listarTodosProductos());
        productoListaView.getBtnBuscar().addActionListener(e -> buscarProductoPorCodigo());
    }
    /**
     * Lista todos los productos y los muestra en la vista.
     * Utiliza el DAO para obtener la lista de productos.
     */

    private void listarTodosProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos, mensajeHandler.getLocale());
    }

    /**
     * Busca un producto por su código y lo muestra en la vista.
     * Si no se encuentra, muestra un mensaje de error.
     * Utiliza el DAO para buscar el producto.
     */
    private void buscarProductoPorCodigo() {
        try {
            int codigo = Integer.parseInt(productoListaView.getTxtBuscar().getText());
            Producto p = productoDAO.buscarPorCodigo(codigo);
            if (p != null) {
                productoListaView.cargarDatos(List.of(p), mensajeHandler.getLocale());
            } else {
                productoListaView.mostrarMensaje(mensajeHandler.get("producto.no.encontrado"));
                productoListaView.cargarDatos(List.of(), mensajeHandler.getLocale());
            }
        } catch (NumberFormatException ex) {
            productoListaView.mostrarMensaje(mensajeHandler.get("producto.codigo.invalido"));
        }
    }


    /**
     * Configura los eventos para actualizar productos.
     * Incluye la búsqueda del producto y la actualización de sus datos.
     * Maneja errores y validaciones.
     */

    private void configurarEventosActualizar() {
        productoActualizarView.getBtnBuscar().addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(productoActualizarView.getTxtCodigo().getText());
                Producto p = productoDAO.buscarPorCodigo(codigo);

                if (p != null) {
                    productoActualizarView.mostrarProducto(p.getNombre(), p.getPrecio(), mensajeHandler.getLocale());
                } else {
                    productoActualizarView.mostrarMensaje(mensajeHandler.get("producto.no.encontrado"));
                    productoActualizarView.limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                productoActualizarView.mostrarMensaje(mensajeHandler.get("producto.codigo.invalido"));
            }
        });

        productoActualizarView.getBtnActualizar().addActionListener(e -> {
            if (procesandoActualizar) return;
            procesandoActualizar = true;

            try {
                int codigo = Integer.parseInt(productoActualizarView.getTxtCodigo().getText());
                String nombre = productoActualizarView.getTxtNombre().getText();
                double precio = Double.parseDouble(productoActualizarView.getTxtPrecio().getText().replace(",", "."));

                Producto p = productoDAO.buscarPorCodigo(codigo);
                if (p != null) {
                    p.setNombre(nombre);
                    p.setPrecio(precio);
                    productoDAO.actualizar(p);
                    productoActualizarView.mostrarMensaje(mensajeHandler.get("producto.actualizado.ok"));
                    productoActualizarView.limpiarCampos();
                } else {
                    productoActualizarView.mostrarMensaje(mensajeHandler.get("producto.no.encontrado"));
                }
            } catch (NumberFormatException ex) {
                productoActualizarView.mostrarMensaje(mensajeHandler.get("producto.datos.invalidos"));
            } finally {
                procesandoActualizar = false;
            }
        });
    }
    /**
     * Configura los eventos para eliminar productos.
     * Incluye la búsqueda del producto y la confirmación de eliminación.
     * Maneja errores y validaciones.
     */

    private void configurarEventosEliminar() {
        productoEliminarView.getBtnBuscar().addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
                Producto p = productoDAO.buscarPorCodigo(codigo);

                if (p != null) {
                    productoEliminarView.mostrarProducto(p.getNombre(), p.getPrecio());
                } else {
                    productoEliminarView.mostrarMensaje(mensajeHandler.get("producto.no.encontrado"));
                    productoEliminarView.limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                productoEliminarView.mostrarMensaje(mensajeHandler.get("producto.codigo.invalido"));
            }
        });

        productoEliminarView.getBtnEliminar().addActionListener(e -> {
            if (procesandoEliminar) return;
            procesandoEliminar = true;

            try {
                int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
                Producto p = productoDAO.buscarPorCodigo(codigo);

                if (p != null) {
                    int confirm = JOptionPane.showConfirmDialog(null,
                            mensajeHandler.get("producto.eliminar.confirmar"),
                            mensajeHandler.get("ventana.confirmacion"),
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        productoDAO.eliminar(codigo);
                        productoEliminarView.mostrarMensaje(mensajeHandler.get("producto.eliminado"));
                        productoEliminarView.limpiarCampos();
                    }
                } else {
                    productoEliminarView.mostrarMensaje(mensajeHandler.get("producto.no.encontrado"));
                }
            } catch (NumberFormatException ex) {
                productoEliminarView.mostrarMensaje(mensajeHandler.get("producto.codigo.invalido"));
            } finally {
                procesandoEliminar = false;
            }
        });
    }
}
