package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class MiJDesktopPane extends JDesktopPane {
    private MensajeInternacionalizacionHandler mensajes;

    public MiJDesktopPane(MensajeInternacionalizacionHandler mensajes) {
        super();
        this.mensajes = mensajes;
    }

    public void actualizarTextos() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cielo degradado estilo "UN VERANO SIN TI"
        GradientPaint cielo = new GradientPaint(0, 0, new Color(255, 182, 193), // Rosa claro
                0, height / 2, new Color(255, 218, 185)); // Melocotón
        g2.setPaint(cielo);
        g2.fillRect(0, 0, width, height);

        // Nubes blancas características
        g2.setColor(Color.WHITE);
        drawCloud(g2, width / 8, height / 10, 1.5);
        drawCloud(g2, width / 3, height / 8, 1.8);
        drawCloud(g2, width / 2 + 100, height / 12, 2.0);
        drawCloud(g2, width - 300, height / 9, 1.6);
        drawCloud(g2, width / 6, height / 6, 1.2);

        // Sol amarillo intenso
        int sunSize = height / 8;
        int sunX = width - sunSize - 50;
        int sunY = 40;
        g2.setColor(new Color(255, 255, 0)); // Amarillo puro
        g2.fillOval(sunX, sunY, sunSize, sunSize);

        // Rayos del sol
        g2.setColor(new Color(255, 215, 0)); // Dorado
        int rayLength = sunSize + 20;
        for (int i = 0; i < 360; i += 15) {
            double angle = Math.toRadians(i);
            int x1 = sunX + sunSize / 2 + (int) (sunSize / 2 * Math.cos(angle));
            int y1 = sunY + sunSize / 2 + (int) (sunSize / 2 * Math.sin(angle));
            int x2 = sunX + sunSize / 2 + (int) (rayLength * Math.cos(angle));
            int y2 = sunY + sunSize / 2 + (int) (rayLength * Math.sin(angle));
            g2.drawLine(x1, y1, x2, y2);
        }

        // Mar azul turquesa
        GradientPaint mar = new GradientPaint(0, height / 2, new Color(64, 224, 208), // Turquesa
                0, height * 3 / 4, new Color(32, 178, 170)); // Turquesa oscuro
        g2.setPaint(mar);
        g2.fillRect(0, height / 2, width, height / 4);

        // Ondas del mar
        g2.setColor(new Color(255, 255, 255, 180));
        for (int i = 0; i < width; i += 30) {
            g2.drawArc(i, height / 2 - 15, 40, 20, 0, 180);
            g2.drawArc(i + 15, height / 2 + 30, 40, 20, 0, 180);
        }

        // Arena rosada característica
        GradientPaint arena = new GradientPaint(0, height * 3 / 4,
                new Color(255, 182, 193), // Rosa arena
                0, height,
                new Color(255, 160, 160)); // Rosa más intenso
        g2.setPaint(arena);
        g2.fillRect(0, height * 3 / 4, width, height / 4);

        // Palmera mejorada
        int troncoBaseX = 100;
        int troncoBaseY = height * 3 / 4 - 280;

        // Tronco con textura más realista
        g2.setColor(new Color(139, 69, 19));
        for (int i = 0; i < 18; i++) {
            int grosor = 25 - i / 3; // Tronco más grueso en la base
            g2.fillRoundRect(troncoBaseX + i * 2, troncoBaseY + i * 15, grosor, 18, 8, 8);
        }

        // Textura del tronco
        g2.setColor(new Color(101, 67, 33));
        for (int i = 0; i < 18; i += 2) {
            g2.drawLine(troncoBaseX + i * 2, troncoBaseY + i * 15,
                    troncoBaseX + i * 2 + 20, troncoBaseY + i * 15);
        }

        // Hojas de palmera más detalladas
        g2.setColor(new Color(34, 139, 34));
        int hojaX = troncoBaseX + 20;
        int hojaY = troncoBaseY - 80;

        // 6 hojas principales
        for (int i = 0; i < 6; i++) {
            double angulo = (i * 60) * Math.PI / 180;
            int hojaCentroX = hojaX + (int)(40 * Math.cos(angulo));
            int hojaCentroY = hojaY + (int)(40 * Math.sin(angulo));

            // Hoja principal
            g2.fillArc(hojaCentroX - 60, hojaCentroY - 15, 120, 30, (int)(i * 60 - 15), 30);

            // Sombra de la hoja
            g2.setColor(new Color(0, 100, 0));
            g2.fillArc(hojaCentroX - 58, hojaCentroY - 13, 116, 26, (int)(i * 60 - 15), 30);
            g2.setColor(new Color(34, 139, 34));
        }

        // Cocos
        g2.setColor(new Color(139, 69, 19));
        g2.fillOval(hojaX - 15, hojaY - 10, 15, 20);
        g2.fillOval(hojaX + 10, hojaY - 5, 12, 18);
        g2.fillOval(hojaX - 5, hojaY + 10, 14, 16);

        // CARRITO DE SUPERMERCADO en la arena
        drawShoppingCart(g2, width / 2 - 100, height * 3 / 4 + 20);
    }

    private void drawCloud(Graphics2D g2, int x, int y, double scale) {
        int s = (int) (60 * scale);
        g2.fillOval(x, y, s, (int)(40 * scale));
        g2.fillOval(x + s / 3, y - s / 3, s, s);
        g2.fillOval(x + 2 * s / 3, y, s, (int)(40 * scale));
    }



    private void drawShoppingCart(Graphics2D g2, int x, int y) {
        // Configuración del carrito en la arena
        g2.setStroke(new BasicStroke(4));

        // Sombra del carrito en la arena
        g2.setColor(new Color(0, 0, 0, 60));
        g2.fillOval(x - 15, y + 120, 220, 25);

        // Cesta del carrito (estructura principal)
        g2.setColor(new Color(255, 255, 255)); // Blanco
        g2.fillRoundRect(x + 20, y, 120, 80, 10, 10);

        // Borde de la cesta
        g2.setColor(new Color(70, 130, 180)); // Azul acero
        g2.drawRoundRect(x + 20, y, 120, 80, 10, 10);

        // Rejilla del carrito
        g2.setStroke(new BasicStroke(2));
        for (int i = 0; i < 4; i++) {
            g2.drawLine(x + 30 + i * 25, y + 10, x + 30 + i * 25, y + 70);
        }
        for (int i = 0; i < 3; i++) {
            g2.drawLine(x + 30, y + 20 + i * 20, x + 130, y + 20 + i * 20);
        }

        // Mango del carrito
        g2.setStroke(new BasicStroke(6));
        g2.setColor(new Color(70, 130, 180));
        g2.drawLine(x + 150, y + 10, x + 180, y + 10);
        g2.drawLine(x + 180, y + 10, x + 180, y + 60);

        // Parte frontal del carrito
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(x + 20, y + 80, x + 20, y + 100);
        g2.drawLine(x + 140, y + 80, x + 140, y + 100);
        g2.drawLine(x + 20, y + 100, x + 140, y + 100);

        // Ruedas del carrito
        g2.setColor(new Color(64, 64, 64)); // Gris oscuro
        g2.fillOval(x + 5, y + 95, 30, 30);
        g2.fillOval(x + 125, y + 95, 30, 30);

        // Centros de las ruedas
        g2.setColor(new Color(220, 220, 220)); // Gris claro
        g2.fillOval(x + 15, y + 105, 10, 10);
        g2.fillOval(x + 135, y + 105, 10, 10);

        // Brillo en el carrito
        g2.setColor(new Color(255, 255, 255, 120));
        g2.fillRoundRect(x + 25, y + 5, 30, 20, 5, 5);

        // Detalles adicionales del carrito
        g2.setColor(new Color(70, 130, 180));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(x + 10, y + 50, x + 20, y + 50);
        g2.drawLine(x + 140, y + 50, x + 150, y + 50);

        // Huellas del carrito en la arena
        g2.setColor(new Color(255, 160, 160, 100));
        g2.fillOval(x + 10, y + 130, 25, 8);
        g2.fillOval(x + 130, y + 130, 25, 8);
        g2.fillOval(x + 5, y + 140, 25, 8);
        g2.fillOval(x + 125, y + 140, 25, 8);
    }
}