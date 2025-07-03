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
        GradientPaint cielo = new GradientPaint(0, 0, new Color(255, 204, 153),
                0, height / 2, new Color(255, 255, 204));
        g2.setPaint(cielo);
        g2.fillRect(0, 0, width, height);

        g2.setColor(Color.WHITE);
        drawCloud(g2, width / 8, height / 10, 1.5); // aumentar el 1.5 para mas grande
        drawCloud(g2, width / 3, height / 8, 1.8);
        drawCloud(g2, width / 2 + 100, height / 12, 2.0);
        drawCloud(g2, width - 300, height / 9, 1.6);


        int sunSize = height / 8; // Aumentar esto para hacer el sol más grande
        int sunX = width - sunSize - 50;
        int sunY = 40;
        g2.setColor(Color.YELLOW);
        g2.fillOval(sunX, sunY, sunSize, sunSize);

        g2.setColor(Color.ORANGE);
        int rayLength = sunSize + 20;
        for (int i = 0; i < 360; i += 15) {
            double angle = Math.toRadians(i);
            int x1 = sunX + sunSize / 2 + (int) (sunSize / 2 * Math.cos(angle));
            int y1 = sunY + sunSize / 2 + (int) (sunSize / 2 * Math.sin(angle));
            int x2 = sunX + sunSize / 2 + (int) (rayLength * Math.cos(angle));
            int y2 = sunY + sunSize / 2 + (int) (rayLength * Math.sin(angle));
            g2.drawLine(x1, y1, x2, y2);
        }

        GradientPaint mar = new GradientPaint(0, height / 2, new Color(0, 191, 255),
                0, height * 3 / 4, new Color(0, 119, 190));
        g2.setPaint(mar);
        g2.fillRect(0, height / 2, width, height / 4);


        g2.setColor(new Color(255, 255, 255, 180));
        for (int i = 0; i < width; i += 30) {
            g2.drawArc(i, height / 2 - 15, 40, 20, 0, 180);
            g2.drawArc(i + 15, height / 2 + 30, 40, 20, 0, 180);
        }




        GradientPaint arena = new GradientPaint(0, height * 3 / 4,
                new Color(255, 228, 181),
                0, height,
                new Color(238, 214, 175));
        g2.setPaint(arena);
        g2.fillRect(0, height * 3 / 4, width, height / 4);
        int troncoBaseX = 100;
        int troncoBaseY = height * 3 / 4 - 300;
        g2.setColor(new Color(139, 69, 19));
        for (int i = 0; i < 15; i++) {
            g2.fillRoundRect(troncoBaseX + i, troncoBaseY + i * 20, 20, 20, 10, 10);
        }


        g2.setColor(new Color(34, 139, 34));
        for (int i = 0; i < 8; i++) {
            g2.fillArc(troncoBaseX - 80, troncoBaseY - 100, 200, 80, i * 45, 40);
        }
    }

    private void drawCloud(Graphics2D g2, int x, int y, double scale) {
        int s = (int) (60 * scale);
        g2.fillOval(x, y, s, (int)(40 * scale));
        g2.fillOval(x + s / 3, y - s / 3, s, s);
        g2.fillOval(x + 2 * s / 3, y, s, (int)(40 * scale));
    }




}

