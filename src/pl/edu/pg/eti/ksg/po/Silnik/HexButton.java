package pl.edu.pg.eti.ksg.po.Silnik;

import javax.swing.*;
import java.awt.*;

public class HexButton extends JButton {
    private static final int Boki = 6;
    private static final int Bok_dl = 25;
    public static final int wys = 47;
    public static final int szer = 52;

    public HexButton() {
        setFocusable(false);
        setContentAreaFilled(false);
        setFocusPainted(true);
        setBorderPainted(false);
        setPreferredSize(new Dimension(szer, wys));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Polygon hex = new Polygon();
        for (int i = 0; i < Boki; i++) {
            hex.addPoint((int) (25 + Bok_dl * Math.cos(i * 2 * Math.PI / Boki)),
                    (int) (25 + Bok_dl * Math.sin(i * 2 * Math.PI / Boki)));
        }
        g.drawPolygon(hex);
    }
}