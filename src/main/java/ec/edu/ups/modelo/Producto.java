package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Representa un producto disponible para la venta.
 * Contiene información básica como código, nombre y precio.
 */
public class Producto implements Serializable {

    private int codigo;
    private String nombre;
    private double precio;

    /**
     * Constructor vacío requerido para serialización.
     */
    public Producto() {
    }

    /**
     * Crea un producto con los datos especificados.
     *
     * @param codigo Código identificador.
     * @param nombre Nombre del producto.
     * @param precio Precio unitario.
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }
}
