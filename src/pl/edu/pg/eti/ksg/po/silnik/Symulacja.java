package pl.edu.pg.eti.ksg.po.silnik;

import pl.edu.pg.eti.ksg.po.Rosliny.*;
import pl.edu.pg.eti.ksg.po.Zwierzeta.*;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

public class Symulacja {
    private final JFrame frame = new JFrame();
    private JButton tura;
    private JButton zapis;
    private JButton odczyt;
    private final JPanel mapa = new JPanel();
    private final JPanel sterowanie = new JPanel();
    private final JTextArea tekst = new JTextArea();
    private final JSplitPane splitpanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private final JPanel SluchaczStrzalek = new JPanel();
    private JButton[][] arr;
    private List<Organizm> O;
    private  Swiat S;
    private int Y, X;

    public Symulacja(int Y, int X) {
        this.Y = Y;
        this.X = X;
        S = new Swiat(Y, X, this);
        O = dodajOrganizmy();
        S.dodajOrganizmy(O);
        inicjujGuziki();

        splitpanel.setLeftComponent(mapa);
        splitpanel.setRightComponent(sterowanie);
        SluchaczStrzalek.addKeyListener(new SluczaczRuchu());
        splitpanel.setRightComponent(sterowanie); //

    }

    public void symuluj(){
        frame.setContentPane(splitpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // frame.add(splitpanel);
       // frame.setSize(720,720);
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
        mapa.setLayout(new GridLayout(Y+ 1, X));
        InitMapa();
        InitTura();
        InitZapis();
        InitOdczyt();
        tekst.append("rtdetg");
        tekst.setEditable(false);
        sterowanie.add(tekst);
    }

    private void InitMapa(){
        arr = new JButton[Y][X];

        for(int y = 0; y < Y; y++){
            for(int x = 0; x < X; x++){
                arr[y][x] = new JButton();
                arr[y][x].setFocusable(false);
                mapa.add(arr[y][x]);
            }
        }
    }
    private void InitOdczyt(){
        odczyt = new JButton();
        odczyt.setFocusable(false);
        odczyt.setText("Wczytaj");
        odczyt.addActionListener(new SluchaczOdczytu());
        sterowanie.add(odczyt);
    }
    private void InitZapis(){
        zapis = new JButton();
        zapis.setFocusable(false);
        zapis.setText("Zapisz");
        zapis.addActionListener(new SluchaczZapisu());
        sterowanie.add(zapis);
    }
    private void InitTura(){
        tura = new JButton();
        tura.setFocusable(false);
        tura.addActionListener(new SluchaczNowejTury());
        tura.setText("Nowa Tura!");
        tura.setBounds(new Rectangle(50, 10));
        sterowanie.add(tura);
    }

    public void test(){
        System.out.println("TEST");
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
            S.rysujSwiat();
            aktualizujMape();
            frame.setTitle("Tura "+S.GetTura());
        }
    }
    private class SluczaczRuchu implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                System.out.println("TEST");

            }
            //wczytajMenu();
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }

    public void nasluchujRuchu(){
        splitpanel.remove(sterowanie);
        splitpanel.setRightComponent(SluchaczStrzalek);
        //SluchaczStrzalek.setFocusable(true);
    }
   public void wczytajMenu(){
       splitpanel.remove(SluchaczStrzalek);
       splitpanel.setRightComponent(sterowanie);
   }


    private List<Organizm> dodajOrganizmy() {
        List<Organizm> L = new LinkedList<>();
        L.add(new Czlowiek(0, 0, S));
        // L.add(new Owca(0, 0));
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
