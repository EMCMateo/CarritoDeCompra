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
