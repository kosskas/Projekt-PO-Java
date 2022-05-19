package pl.edu.pg.eti.ksg.po.Silnik.Aplikacja;

import javax.swing.*;
import java.awt.*;

public class HexButton extends JButton {
    private static final int SIDES = 6;
    private static final int SIDE_LENGTH = 25;
    public static final int LENGTH = 47;
    public static final int WIDTH = 52;

    public HexButton() {
        setFocusable(false);
        setContentAreaFilled(false);
        setFocusPainted(true);
        setBorderPainted(false);
        setPreferredSize(new Dimension(WIDTH, LENGTH));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Polygon hex = new Polygon();
        for (int i = 0; i < SIDES; i++) {
            hex.addPoint((int) (25 + SIDE_LENGTH * Math.cos(i * 2 * Math.PI / SIDES)), //calculation for side
                    (int) (25 + SIDE_LENGTH * Math.sin(i * 2 * Math.PI / SIDES)));   //calculation for side
        }
        g.drawPolygon(hex);
    }
}