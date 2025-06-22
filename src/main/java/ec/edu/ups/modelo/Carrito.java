package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class Carrito {

    private static int ultimoCodigo = 0;
    private final double IVA = 0.12;
    private GregorianCalendar fechaCreacion;
    private int codigo;

    private List<ItemCarrito> items;

    public Carrito() {
        this.codigo = ++ultimoCodigo;
        this.fechaCreacion = new GregorianCalendar(); // ✅ ahora no será null
        this.items = new ArrayList<>();
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

    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    public void vaciarCarrito() {
        items.clear();
    }

    public double calcularIVA() {
        return calcularSubTotal() * IVA;
    }

    public double calcularTotal() {
        return calcularSubTotal() + calcularIVA();
    }


    public double calcularSubTotal(){
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total;
    }



    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }
}


/*
package ec.edu.ups.modelo;



import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Carrito {
    private ArrayList<ItemCarrito> listaItems;
    private int codigo;
    private GregorianCalendar fechaCreacion;


    public Carrito(ArrayList<ItemCarrito> listaItems, int codigo, GregorianCalendar fechaCreacion) {
        this.listaItems = listaItems;
        this.codigo = codigo;

    }

    public List<ItemCarrito> getListaItems() {
        return listaItems;
    }

    public void setListaItems(ArrayList<ItemCarrito> listaItems) {
        this.listaItems = listaItems;
    }



    public void agregarItem(ItemCarrito items){
        listaItems.add(items);


    }

    public double calcularCosto() {
        double total = 0;
        for (ItemCarrito items : listaItems) {
            total = total + (items.getProducto().getPrecio() * items.getCantidad());

        }
        return total;
    }

    public void imprimirCarrito(ArrayList<ItemCarrito> listaItems){
        System.out.println("Carrito: ");
        for (ItemCarrito items: listaItems){
            System.out.println(items);
        }
        System.out.println("Costo Total del carrito: "+calcularCosto());

    }

    @Override
    public String toString() {
        return "Carrito" +
                "listaItems=" + listaItems +
                ", totalCompra=" + calcularCosto() +
                '}';
    }
}

 */
