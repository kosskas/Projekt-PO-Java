package pl.edu.pg.eti.ksg.po.silnik;

import pl.edu.pg.eti.ksg.po.Rosliny.*;
import pl.edu.pg.eti.ksg.po.Zwierzeta.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class Symulacja {
    private JFrame frame = new JFrame();
    private JButton tura;
    private JButton zapis;
    private JButton odczyt;
    private JPanel panel = new JPanel();
    private JButton[][] arr;
    private List<Organizm> O = dodajOrganizmy();
    private  Swiat S;
    private int Y, X;

    public Symulacja(int Y, int X) {
        this.Y = Y;
        this.X = X;
        S = new Swiat(Y, X, O);
        inicjujGuziki();
    }

    public void symuluj(){
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void aktualizujMape(){
        char[][] plansza = S.GetPlansza();
        for(int y = 0; y < Y; y++){
            for(int x = 0; x < X; x++){
                arr[y][x].setText(""+plansza[y][x]);
            }
        }
    }

    private void inicjujGuziki(){
        arr = new JButton[Y][X];

        tura = new JButton();
        zapis = new JButton();
        odczyt = new JButton();

        panel.setLayout(new GridLayout(0, X));

        for(int y = 0; y < Y; y++){
            for(int x = 0; x < X; x++){
                arr[y][x] = new JButton();
                arr[y][x].setSize(1, 1);
                panel.add(arr[y][x]);
            }
        }

        tura.addActionListener(new SluchaczNowejTury());
        tura.setText("Nowa Tura!");
        zapis.setText("Zapisz");
        zapis.addActionListener(new SluchaczZapisu());

        odczyt.addActionListener(new SluchaczOdczytu());
        panel.add(tura);
        panel.add(zapis);
        panel.add(odczyt);
    }

    private class SluchaczNowejTury implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            S.nowaTura();
            aktualizujMape();
            frame.setTitle("Tura "+S.GetTura());
        }
    }
    private class SluchaczZapisu implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            S.zapiszSwiat();
        }
    }
    private class SluchaczOdczytu implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            S.wczytajSwiat();
        }
    }

    private List<Organizm> dodajOrganizmy(){
        List<Organizm> L = new LinkedList<>();
        L.add(new Owca(0, 0));
        L.add(new Owca(1, 0));
        L.add(new Owca(0, 1));
        L.add(new BarszczSosnowskiego(4, 0));
        L.add(new BarszczSosnowskiego(0, 4));
        L.add(new BarszczSosnowskiego(3, 4));
        L.add(new Lis(5, 4));
        L.add(new Lis(4, 3));
        L.add(new Zolw(7, 7));
        L.add(new Zolw(1, 9));
        L.add(new Owca(7, 8));
        L.add(new Trawa(7, 2));
        L.add(new Mlecz(8, 5));
        L.add(new Guarana(2, 7));
        L.add(new Antylopa(8, 3));
        L.add(new Antylopa(6, 7));
        L.add(new Antylopa(8, 7));
        L.add(new Antylopa(2, 2));
        L.add(new WilczeJagody(5, 7));
        L.add(new Wilk(3, 6));
        L.add(new Wilk(3, 5));
        return L;
    }
}
