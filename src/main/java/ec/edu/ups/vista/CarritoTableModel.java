package ec.edu.ups.vista;

import ec.edu.ups.modelo.ItemCarrito;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CarritoTableModel extends AbstractTableModel {

    private final String[] columnas = {"Código", "Nombre", "Precio", "Cantidad", "Subtotal"};
    private final List<ItemCarrito> items;

    public CarritoTableModel(List<ItemCarrito> items) {
        this.items = items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        ItemCarrito item = items.get(fila);
        switch (columna) {
            case 0:
                return item.getProducto().getCodigo();
            case 1:
                return item.getProducto().getNombre();
            case 2:
                return item.getProducto().getPrecio();
            case 3:
                return item.getCantidad();
            case 4:
                return item.getCantidad() * item.getProducto().getPrecio();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }
}
