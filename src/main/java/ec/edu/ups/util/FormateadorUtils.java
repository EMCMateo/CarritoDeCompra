package ec.edu.ups.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class FormateadorUtils {

    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        return formatoMoneda.format(cantidad);
    }
    public static double parsearMoneda(String texto, Locale locale) throws ParseException {
        NumberFormat formatoNumero = NumberFormat.getNumberInstance(locale);
        return formatoNumero.parse(texto).doubleValue();
    }

    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }

}

/*

 */
