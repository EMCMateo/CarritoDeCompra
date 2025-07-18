package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;

/**
 * Representa un carrito de compras asociado a un usuario.
 * Permite agregar, eliminar productos, y calcular totales.
 */
public class Carrito implements Serializable {

    /**
     * Porcentaje del IVA aplicado a los productos.
     */
    private final double IVA = 0.12;

    private GregorianCalendar fechaCreacion;
    private int codigo;
    private Usuario usuario;
    private List<ItemCarrito> items;

    /**
     * Lleva el control del último código generado para el carrito.
     */
    private static int ultimoCodigo = 0;

    /**
     * Constructor que inicializa un nuevo carrito con código único y fecha de creación.
     */
    public Carrito() {
        this.codigo = ultimoCodigo++;
        this.fechaCreacion = new GregorianCalendar();
        this.items = new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Agrega un producto al carrito. Si ya existe, aumenta la cantidad.
     *
     * @param producto Producto a agregar.
     * @param cantidad Cantidad del producto.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    /**
     * Elimina un producto del carrito según su código.
     *
     * @param codigoProducto Código del producto a eliminar.
     */
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    /**
     * Elimina todos los productos del carrito.
     */
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Calcula el IVA total del carrito.
     *
     * @return Valor del IVA.
     */
    public double calcularIVA() {
        return calcularSubTotal() * IVA;
    }

    /**
     * Calcula el total a pagar incluyendo el IVA.
     *
     * @return Total con impuestos.
     */
    public double calcularTotal() {
        return calcularSubTotal() + calcularIVA();
    }

    /**
     * Calcula el subtotal del carrito (sin impuestos).
     *
     * @return Subtotal sin IVA.
     */
    public double calcularSubTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total;
    }

    /**
     * Obtiene los ítems actuales del carrito.
     *
     * @return Lista de {@link ItemCarrito}.
     */
    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    /**
     * Verifica si el carrito está vacío.
     *
     * @return {@code true} si no hay productos, de lo contrario {@code false}.
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }
}
