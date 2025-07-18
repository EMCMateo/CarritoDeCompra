package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Representa un ítem dentro del carrito, compuesto por un producto y su cantidad.
 */
public class ItemCarrito implements Serializable {

    private Producto producto;
    private int cantidad;

    /**
     * Constructor vacío necesario para serialización.
     */
    public ItemCarrito() {
    }

    /**
     * Crea un ítem con un producto y su cantidad asociada.
     *
     * @param producto Producto seleccionado.
     * @param cantidad Cantidad seleccionada.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    /**
     * Calcula el subtotal correspondiente a este ítem.
     *
     * @return Precio total = precio del producto × cantidad.
     */
    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return producto.toString() + " x " + cantidad + " = $" + getSubtotal();
    }
}
