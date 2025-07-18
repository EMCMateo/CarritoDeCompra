package ec.edu.ups.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Clase utilitaria para parsear y formatear fechas y valores monetarios
 * en función del locale seleccionado.
 */
public class FormateadorUtils {

    /**
     * Parsea una cadena de texto que representa un valor monetario
     * utilizando un {@link Locale} específico.
     *
     * @param texto Texto con el número a parsear
     * @param locale Locale que determina el formato esperado
     * @return Valor como {@code double}
     * @throws ParseException Si no se puede parsear el texto
     */
    public static double parsearMoneda(String texto, Locale locale) throws ParseException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new ParseException("Texto vacío", 0);
        }
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        Number number = nf.parse(texto.trim());
        return number.doubleValue();
    }

    /**
     * Parsea una cadena de texto a número decimal usando conversión básica,
     * reemplazando comas por puntos si es necesario.
     *
     * @param texto Texto a convertir
     * @return Número como {@code double}
     * @throws NumberFormatException Si el texto no es un número válido
     */
    public static double parsearMonedaFlexible(String texto) throws NumberFormatException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new NumberFormatException("Texto vacío");
        }
        texto = texto.trim().replace(",", ".");
        return Double.parseDouble(texto);
    }

    /**
     * Formatea un número decimal como valor monetario con 2 decimales según el locale.
     *
     * @param valor Valor numérico a formatear
     * @param locale Locale deseado
     * @return Cadena formateada
     */
    public static String formatearMoneda(double valor, Locale locale) {
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(valor);
    }

    /**
     * Formatea un valor como moneda incluyendo símbolo (como $ o €) según el locale.
     *
     * @param valor Valor monetario
     * @param locale Locale que determina el símbolo
     * @return Cadena formateada con símbolo de moneda
     */
    public static String formatearMonedaConSimbolo(double valor, Locale locale) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        return currencyFormat.format(valor);
    }

    /**
     * Formatea una fecha en formato legible de acuerdo al locale.
     *
     * @param fecha Fecha a formatear
     * @param locale Locale que define el formato de salida
     * @return Fecha formateada como texto
     */
    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }

    /**
     * Formatea un número con un patrón personalizado (ej. "#,##0.00").
     *
     * @param valor Valor numérico a formatear
     * @param patron Patrón de formato
     * @param locale Locale que define el estilo
     * @return Cadena formateada con patrón
     */
    public static String formatearMonedaPersonalizado(double valor, String patron, Locale locale) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        df.applyPattern(patron);
        return df.format(valor);
    }
}
