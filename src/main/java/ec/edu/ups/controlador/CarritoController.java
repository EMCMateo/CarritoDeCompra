package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.CarritoAñadirView;
import ec.edu.ups.vista.CarritoEditarView;
import ec.edu.ups.vista.ListarCarritoView;
import ec.edu.ups.vista.ListarCarritoUsuarioView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CarritoController {
    private final JDesktopPane desktopPane;
    private final ProductoDAO productoDAO;
    private final CarritoAñadirView carritoAñadirView;
    private final ListarCarritoView listarCarritoView;
    private Carrito carrito;
    private final CarritoDAO carritoDAO;
    private final UsuarioDAO usuarioDAO;
    private boolean eventosRegistrados = false;
    private Usuario usuarioActual;
    private boolean visualizarCarrito = false;
    private boolean visualizarCarritoEditar = false;

    private final MensajeInternacionalizacionHandler mensajeHandler;
    private final ListarCarritoUsuarioView listarCarritoUsuarioView;
    private final CarritoEditarView carritoEditarView;

    public CarritoController(CarritoDAO carritoDAO, CarritoAñadirView carritoAñadirView,
                             ProductoDAO productoDAO, Carrito carrito, ListarCarritoView listarCarritoView,
                             Usuario usuarioActual, MensajeInternacionalizacionHandler mensajeHandler,
                             ListarCarritoUsuarioView listarCarritoUsuarioView, UsuarioDAO usuarioDAO, CarritoEditarView carritoEditarView,  JDesktopPane desktopPane) {
        this.productoDAO = productoDAO;
        this.carritoAñadirView = carritoAñadirView;
        this.carritoDAO = carritoDAO;
        this.usuarioDAO = usuarioDAO;
        this.listarCarritoView = listarCarritoView;
        this.usuarioActual = usuarioActual;
        this.mensajeHandler = mensajeHandler;
        this.listarCarritoUsuarioView = listarCarritoUsuarioView;
        this.carritoEditarView = carritoEditarView;
        this.desktopPane = desktopPane;
    }

    public void carritoEventos() {
        if (eventosRegistrados) return;
        eventosRegistrados = true;
        inicializarEventosAñadirView();
        inicializarEventosListarCarritoView();
        inicializarEventosListarCarritoUsuarioView();
        inicializarEventosEditarCarritoView();
    }



    public void mostrarVentanaEditarDesdeMenu() {
        // Cierra la vista si ya está abierta (por seguridad)
        if (carritoEditarView.isVisible()) {
            carritoEditarView.setVisible(false);
        }

        // Selecciona carrito desde comboBox
        seleccionarCarritoDesdeComboBox();
    }

    private void seleccionarCarritoDesdeComboBox() {
        carrito = null;
        visualizarCarritoEditar = false;

        List<Carrito> carritos = carritoDAO.listarTodos();
        if (carritos.isEmpty()) {
            JOptionPane.showMessageDialog(null, mensajeHandler.get("carrito.vacio"));
            return;
        }

        JComboBox<String> combo = new JComboBox<>();
        Locale locale = mensajeHandler.getLocale();
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, locale);

        for (Carrito c : carritos) {
            String fechaFormateada = sdf.format(c.getFechaCreacion().getTime());
            combo.addItem(c.getCodigo() + " - " + fechaFormateada);
        }

        int opcion = JOptionPane.showConfirmDialog(null, combo,
                mensajeHandler.get("carrito.seleccionar.editar"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION) {
            String seleccionado = (String) combo.getSelectedItem();
            if (seleccionado != null) {
                int codigo = Integer.parseInt(seleccionado.split(" - ")[0]);
                Carrito seleccionadoCarrito = carritoDAO.buscarPorCodigo(codigo);
                if (seleccionadoCarrito != null) {
                    carrito = seleccionadoCarrito;
                    abrirCarritoEditarView(false); // ¡IMPORTANTE! false = modo editable
                }
            }
        }
    }



    private void inicializarEventosEditarCarritoView() {
        carritoEditarView.getBtnBuscar().addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(carritoEditarView.getTxtCodigo().getText());
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    carritoEditarView.getTxtNombre().setText(producto.getNombre());
                    carritoEditarView.getTxtPrecio().setText(
                            FormateadorUtils.formatearMoneda(producto.getPrecio(), mensajeHandler.getLocale()));
                    carritoEditarView.getBtnAnadir().setEnabled(true);
                } else {
                    carritoEditarView.mostrarMensaje(mensajeHandler.get("producto.no.encontrado"));
                    carritoEditarView.limpiarCampos();
                    carritoEditarView.getBtnAnadir().setEnabled(false);
                }
            } catch (NumberFormatException ex) {
                carritoEditarView.mostrarMensaje(mensajeHandler.get("producto.codigo.invalido"));
            }
        });

        carritoEditarView.getBtnAnadir().addActionListener(e -> {
            if (carrito == null) return;
            try {
                int codigo = Integer.parseInt(carritoEditarView.getTxtCodigo().getText());
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                int cantidad = Integer.parseInt(carritoEditarView.getCmBoxCantidad().getSelectedItem().toString());
                carrito.agregarProducto(producto, cantidad);
                carritoEditarView.cargarDatosTabla(carrito.obtenerItems());
                mostrarTotalesEditar();
                carritoEditarView.limpiarCampos();
                carritoEditarView.getBtnAnadir().setEnabled(false);
            } catch (NumberFormatException ex) {
                carritoEditarView.mostrarMensaje(mensajeHandler.get("producto.codigo.invalido"));
            }
        });

        carritoEditarView.getBtnGuardar().addActionListener(e -> {
            if (carrito != null) {
                carritoDAO.actualizar(carrito);
                carritoEditarView.mostrarMensaje(mensajeHandler.get("carrito.actualizado.ok"));
                carritoEditarView.setVisible(false);
                carrito = null;
                listarCarrito();
            }
        });

        carritoEditarView.getBtnBorrar().addActionListener(e -> {
            if (carrito != null) {
                carrito.vaciarCarrito();
                carritoEditarView.cargarDatosTabla(carrito.obtenerItems());
                mostrarTotalesEditar();
                carritoEditarView.limpiarCampos();
            }
        });

        carritoEditarView.getTblCarrito().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (carrito == null || e.getClickCount() != 1) return;
                JTable tabla = carritoEditarView.getTblCarrito();
                int fila = tabla.rowAtPoint(e.getPoint());
                if (fila >= 0) {
                    int codigoProducto = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
                    Object[] opciones = {
                            mensajeHandler.get("opcion.editar"),
                            mensajeHandler.get("opcion.eliminar"),
                            mensajeHandler.get("opcion.cancelar")
                    };
                    int respuesta = JOptionPane.showOptionDialog(null,
                            mensajeHandler.get("producto.accion"),
                            mensajeHandler.get("ventana.opciones"),
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, opciones, opciones[2]);
                    if (respuesta == 0) {
                        String nuevaCantidadStr = JOptionPane.showInputDialog(mensajeHandler.get("producto.ingresar.cantidad"));
                        if (nuevaCantidadStr != null) {
                            int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
                            for (ItemCarrito item : carrito.obtenerItems()) {
                                if (item.getProducto().getCodigo() == codigoProducto) {
                                    item.setCantidad(nuevaCantidad);
                                    break;
                                }
                            }
                            carritoEditarView.cargarDatosTabla(carrito.obtenerItems());
                            mostrarTotalesEditar();
                        }
                    } else if (respuesta == 1) {
                        int confirmar = JOptionPane.showConfirmDialog(null,
                                mensajeHandler.get("producto.eliminar.confirmacion"),
                                mensajeHandler.get("ventana.confirmacion"),
                                JOptionPane.YES_NO_OPTION);
                        if (confirmar == JOptionPane.YES_OPTION) {
                            carrito.eliminarProducto(codigoProducto);
                            carritoEditarView.cargarDatosTabla(carrito.obtenerItems());
                            mostrarTotalesEditar();
                        }
                    }
                }
            }
        });
    }

    private void abrirCarritoEditarView(boolean soloVisualizar) {
        visualizarCarritoEditar = soloVisualizar;

        if (!desktopPane.isAncestorOf(carritoEditarView)) {
            desktopPane.add(carritoEditarView);
        }

        carritoEditarView.setDatosCarrito(carrito);
        carritoEditarView.cargarDatosTabla(carrito.obtenerItems());
        mostrarTotalesEditar();


        boolean habilitar = !soloVisualizar;


        carritoEditarView.getBtnGuardar().setEnabled(habilitar);
        carritoEditarView.getBtnBorrar().setEnabled(habilitar);
        carritoEditarView.getBtnAnadir().setEnabled(habilitar);
        carritoEditarView.getBtnBuscar().setEnabled(habilitar);
        carritoEditarView.getTxtCodigo().setEditable(habilitar);
        carritoEditarView.getCmBoxCantidad().setEnabled(habilitar);

        if (soloVisualizar) {
            for (MouseListener ml : carritoEditarView.getTblCarrito().getMouseListeners()) {
                carritoEditarView.getTblCarrito().removeMouseListener(ml);
            }
        } else {

            inicializarEventosEditarCarritoView();
        }

        carritoEditarView.setVisible(true);
        carritoEditarView.toFront();
    }


    private void mostrarTotalesEditar() {
        Locale locale = mensajeHandler.getLocale();
        carritoEditarView.getTxtSubtotal().setText(
                FormateadorUtils.formatearMoneda(carrito.calcularSubTotal(), locale));
        carritoEditarView.getTxtIVA().setText(
                FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
        carritoEditarView.getTxtTotal().setText(
                FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));
    }

    private void inicializarEventosAñadirView() {
        carritoAñadirView.getBtnBuscar().addActionListener(e -> {
            if (visualizarCarrito) return;
            int codigo = Integer.parseInt(carritoAñadirView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                carritoAñadirView.getTxtNombre().setText(producto.getNombre());
                carritoAñadirView.getTxtPrecio().setText(
                        FormateadorUtils.formatearMoneda(producto.getPrecio(), mensajeHandler.getLocale())
                );
                carritoAñadirView.getBtnAnadir().setEnabled(true);
            } else {
                carritoAñadirView.mostrarMensaje(mensajeHandler.get("producto.no.encontrado"));
                carritoAñadirView.limpiarCampos();
                carritoAñadirView.getBtnAnadir().setEnabled(false);
            }
        });

        carritoAñadirView.getBtnAnadir().addActionListener(e -> {
            if (visualizarCarrito) return;
            añadirProductoAlCarrito();
        });

        carritoAñadirView.getBtnGuardar().addActionListener(e -> {
            if (visualizarCarrito) return;
            guardarCarrito();
        });

        carritoAñadirView.getBtnBorrar().addActionListener(e -> {
            if (visualizarCarrito) return;
            carrito.vaciarCarrito();
            carritoAñadirView.cargarDatosTabla(carrito.obtenerItems());
            limpiarTotales();
            carritoAñadirView.limpiarCampos();
        });

        carritoAñadirView.getTblCarrito().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 1 || visualizarCarrito) return;

                JTable tabla = carritoAñadirView.getTblCarrito();
                int fila = tabla.rowAtPoint(e.getPoint());
                if (fila >= 0) {
                    int codigoProducto = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
                    Object[] opciones = {
                            mensajeHandler.get("opcion.editar"),
                            mensajeHandler.get("opcion.eliminar"),
                            mensajeHandler.get("opcion.cancelar")
                    };
                    int respuesta = JOptionPane.showOptionDialog(null,
                            mensajeHandler.get("producto.accion"),
                            mensajeHandler.get("ventana.opciones"),
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, opciones, opciones[2]);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog(
                                mensajeHandler.get("producto.ingresar.cantidad")));
                        for (ItemCarrito item : carrito.obtenerItems()) {
                            if (item.getProducto().getCodigo() == codigoProducto) {
                                item.setCantidad(nuevaCantidad);
                                break;
                            }
                        }
                        carritoAñadirView.cargarDatosTabla(carrito.obtenerItems());
                        mostrarTotales();
                    } else if (respuesta == JOptionPane.NO_OPTION) {
                        int confirmar = JOptionPane.showConfirmDialog(null,
                                mensajeHandler.get("producto.eliminar.confirmacion"),
                                mensajeHandler.get("ventana.confirmacion"),
                                JOptionPane.YES_NO_OPTION);
                        if (confirmar == JOptionPane.YES_OPTION) {
                            carrito.eliminarProducto(codigoProducto);
                            carritoAñadirView.cargarDatosTabla(carrito.obtenerItems());
                            mostrarTotales();
                        }
                    }
                }
            }
        });
    }




    private void inicializarEventosListarCarritoView() {
        listarCarritoView.getBtnListar().addActionListener(e -> listarCarrito());
        listarCarritoView.getBtnBuscar().addActionListener(e -> buscarCarrito());
        listarCarritoView.getTblCarrito().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 1) return;
                JTable tabla = listarCarritoView.getTblCarrito();
                int fila = tabla.rowAtPoint(e.getPoint());
                 if (fila >= 0) {
                    int codigoCarrito = Integer.parseInt(tabla.getValueAt(fila, 1).toString());
                    Object[] opciones = {
                            mensajeHandler.get("opcion.editar"),
                            mensajeHandler.get("opcion.eliminar"),
                            mensajeHandler.get("opcion.visualizar"),
                            mensajeHandler.get("opcion.cancelar")
                    };
                    int respuesta = JOptionPane.showOptionDialog(listarCarritoView,
                            mensajeHandler.get("carrito.accion"),
                            mensajeHandler.get("ventana.opciones"),
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, opciones, opciones[3]);
                     if (respuesta == 0) { // EDITAR
                         Carrito c = carritoDAO.buscarPorCodigo(codigoCarrito);
                         if (c != null) {
                             carrito = c;
                             abrirCarritoEditarView(false);
                         }
                     } else if (respuesta == 2) { // VISUALIZAR
                         Carrito c = carritoDAO.buscarPorCodigo(codigoCarrito);
                         if (c != null) {
                             carrito = c;
                             abrirCarritoEditarView(true);
                         }
                     }
                     else if (respuesta == 1) {
                        int confirmar = JOptionPane.showConfirmDialog(listarCarritoView,
                                mensajeHandler.get("carrito.eliminar.confirmacion"),
                                mensajeHandler.get("ventana.confirmacion"),
                                JOptionPane.YES_NO_OPTION);
                        if (confirmar == JOptionPane.YES_OPTION) {
                            eliminarCarrito(codigoCarrito);
                        }
                    }
                }
            }
        });
    }

    private void inicializarEventosListarCarritoUsuarioView() {
        listarCarritoUsuarioView.getBtnBuscar().setEnabled(usuarioActual.getRol() == Rol.ADMINISTRADOR);

        listarCarritoUsuarioView.getBtnListar().addActionListener(e -> {
            List<Carrito> carritos;
            if (usuarioActual.getRol() == Rol.ADMINISTRADOR) {
                carritos = carritoDAO.listarTodos();
            } else {
                carritos = carritoDAO.buscarPorUsuario(usuarioActual);
            }
            listarCarritoUsuarioView.cargarDatosConFormato(carritos, mensajeHandler);
        });

        listarCarritoUsuarioView.getTblCarrito().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 1) return;

                JTable tabla = listarCarritoUsuarioView.getTblCarrito();
                int fila = tabla.rowAtPoint(e.getPoint());
                if (fila >= 0) {
                    Object valorCodigo = tabla.getValueAt(fila, 1);
                    if (valorCodigo == null) return;

                    int codigoCarrito;
                    try {
                        codigoCarrito = Integer.parseInt(valorCodigo.toString());
                    } catch (NumberFormatException ex) {
                        return;
                    }

                    Carrito c = carritoDAO.buscarPorCodigo(codigoCarrito);
                    if (c == null || c.getUsuario() == null) {
                        listarCarritoUsuarioView.mostrarMensaje(mensajeHandler.get("carrito.no.encontrado"));
                        return;
                    }

                    if (usuarioActual.getRol() != Rol.ADMINISTRADOR &&
                            !usuarioActual.getCedula().equals(c.getUsuario().getCedula())) {
                        listarCarritoUsuarioView.mostrarMensaje(mensajeHandler.get("carrito.visualizacion.denegada"));
                        return;
                    }

                    Object[] opciones = {
                            mensajeHandler.get("opcion.editar"),
                            mensajeHandler.get("opcion.eliminar"),
                            mensajeHandler.get("opcion.visualizar"),
                            mensajeHandler.get("opcion.cancelar")
                    };

                    int respuesta = JOptionPane.showOptionDialog(listarCarritoUsuarioView,
                            mensajeHandler.get("carrito.accion"),
                            mensajeHandler.get("ventana.opciones"),
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, opciones, opciones[3]);

                    if (respuesta == 0) {
                        carrito = c;
                        abrirCarritoEditarView(false);
                    } else if (respuesta == 1) {
                        int confirmar = JOptionPane.showConfirmDialog(listarCarritoUsuarioView,
                                mensajeHandler.get("carrito.eliminar.confirmacion"),
                                mensajeHandler.get("ventana.confirmacion"),
                                JOptionPane.YES_NO_OPTION);
                        if (confirmar == JOptionPane.YES_OPTION) {
                            carritoDAO.eliminar(c.getCodigo());
                            listarCarritoUsuarioView.getBtnListar().doClick();
                        }
                    } else if (respuesta == 2) {
                        carrito = c;
                        abrirCarritoEditarView(true);
                    }
                }
            }
        });
    }



    private void añadirProductoAlCarrito() {
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setUsuario(usuarioActual);
            carritoAñadirView.setDatosCarrito(carrito);
        }
        int codigo = Integer.parseInt(carritoAñadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad = Integer.parseInt(carritoAñadirView.getCmBoxCantidad().getSelectedItem().toString());
        if (producto != null) {
            carrito.agregarProducto(producto, cantidad);
            carritoAñadirView.cargarDatosTabla(carrito.obtenerItems());
            mostrarTotales();
            carritoAñadirView.limpiarCampos();
            carritoAñadirView.getBtnAnadir().setEnabled(false);
        }
    }

    private void mostrarTotales() {
        Locale locale = mensajeHandler.getLocale();
        carritoAñadirView.getTxtSubtotal().setText(
                FormateadorUtils.formatearMoneda(carrito.calcularSubTotal(), locale));
        carritoAñadirView.getTxtIVA().setText(
                FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
        carritoAñadirView.getTxtTotal().setText(
                FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));
    }

    private void limpiarTotales() {
        carritoAñadirView.getTxtSubtotal().setText("");
        carritoAñadirView.getTxtIVA().setText("");
        carritoAñadirView.getTxtTotal().setText("");
    }

    private void guardarCarrito() {
        carritoDAO.crear(carrito);
        carritoAñadirView.mostrarMensaje(mensajeHandler.get("carrito.guardado.ok"));
        carrito = null;
        carritoAñadirView.limpiarCampos();
        carritoAñadirView.cargarDatosTabla(null);
        limpiarTotales();
    }

    public void listarCarrito() {
        List<Carrito> carritos = carritoDAO.listarTodos();
        listarCarritoView.cargarDatosConFormato(carritos, mensajeHandler);
    }

    private void buscarCarrito() {
        String texto = listarCarritoUsuarioView.getTxtCodigo().getText().trim();

        if (texto.isEmpty()) {
            listarCarritoUsuarioView.mostrarMensaje(mensajeHandler.get("carrito.busqueda.vacia"));
            return;
        }

        // Si es administrador, puede buscar por código o por nombre de usuario
        if (usuarioActual.getRol() == Rol.ADMINISTRADOR) {
            try {
                int codigo = Integer.parseInt(texto);  // intento como código
                Carrito c = carritoDAO.buscarPorCodigo(codigo);
                if (c != null) {
                    listarCarritoUsuarioView.cargarDatosConFormato(List.of(c), mensajeHandler);
                } else {
                    listarCarritoUsuarioView.mostrarMensaje(mensajeHandler.get("carrito.no.encontrado"));
                }
            } catch (NumberFormatException e) {
                // No es número, buscar por nombre de usuario
                Usuario u = usuarioDAO.buscarPorUsername(texto);
                if (u != null) {
                    List<Carrito> carritos = carritoDAO.buscarPorUsuario(u);
                    listarCarritoUsuarioView.cargarDatosConFormato(carritos, mensajeHandler);
                } else {
                    listarCarritoUsuarioView.mostrarMensaje(mensajeHandler.get("usuario.no.encontrado"));
                }
            }
        } else {
            // Si no es administrador, solo puede buscar su propio carrito por código
            try {
                int codigo = Integer.parseInt(texto);
                Carrito c = carritoDAO.buscarPorCodigo(codigo);
                if (c != null && c.getUsuario().equals(usuarioActual)) {
                    listarCarritoUsuarioView.cargarDatosConFormato(List.of(c), mensajeHandler);
                } else {
                    listarCarritoUsuarioView.mostrarMensaje(mensajeHandler.get("carrito.no.encontrado"));
                }
            } catch (NumberFormatException e) {
                listarCarritoUsuarioView.mostrarMensaje(mensajeHandler.get("carrito.codigo.invalido"));
            }
        }
    }


    private void eliminarCarrito(int codigo) {
        carritoDAO.eliminar(codigo);
        listarCarrito();
        listarCarritoView.mostrarMensaje(mensajeHandler.get("carrito.eliminado.ok"));
    }

    private void configurarCarritoEditarView() {
        carritoEditarView.setDatosCarrito(carrito);
        carritoEditarView.cargarDatosTabla(carrito.obtenerItems());
        mostrarTotalesEditar();

        boolean habilitado = !visualizarCarritoEditar;

        carritoEditarView.getBtnGuardar().setEnabled(habilitado);
        carritoEditarView.getBtnBorrar().setEnabled(habilitado);
        carritoEditarView.getBtnAnadir().setEnabled(habilitado);
        carritoEditarView.getBtnBuscar().setEnabled(habilitado);
        carritoEditarView.getTxtCodigo().setEditable(habilitado);
        carritoEditarView.getCmBoxCantidad().setEnabled(habilitado);

        // Importante: Evitar que se dispare doble clics para editar/eliminar productos
        if (visualizarCarritoEditar) {
            for (MouseListener ml : carritoEditarView.getTblCarrito().getMouseListeners()) {
                carritoEditarView.getTblCarrito().removeMouseListener(ml);
            }
        } else {
            // Solo si no está visualizando
            inicializarEventosEditarCarritoView(); // solo una vez si deseas
        }

        carritoEditarView.setVisible(true);
        carritoEditarView.toFront();
    }


}
