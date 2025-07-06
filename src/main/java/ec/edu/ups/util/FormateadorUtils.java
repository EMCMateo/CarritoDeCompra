package ec.edu.ups.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class FormateadorUtils {

    public static double parsearMoneda(String texto, Locale locale) throws ParseException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new ParseException("Texto vacío", 0);
        }
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        Number number = nf.parse(texto.trim());
        return number.doubleValue();
    }

    public static double parsearMonedaFlexible(String texto) throws NumberFormatException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new NumberFormatException("Texto vacío");
        }
        texto = texto.trim().replace(",", ".");
        return Double.parseDouble(texto);
    }


    public static String formatearMoneda(double valor, Locale locale) {
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(valor);
    }

    public static String formatearMonedaConSimbolo(double valor, Locale locale) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        return currencyFormat.format(valor);
    }


    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }

    public static String formatearMonedaPersonalizado(double valor, String patron, Locale locale) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        df.applyPattern(patron);
        return df.format(valor);
    }
}




