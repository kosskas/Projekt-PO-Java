package pl.edu.pg.eti.ksg.po.silnik;

import pl.edu.pg.eti.ksg.po.Rosliny.*;
import pl.edu.pg.eti.ksg.po.Zwierzeta.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

public class Symulacja {
    private final JFrame okno = new JFrame();
    private JPanel test;
    private JButton[] nowyOrg;
    private JButton tura;
    private JButton zapis;
    private JButton odczyt;

    private final JPanel mapa = new JPanel();
    private  SluchaczDodawaniaOrganizmu[][] sdo;
    private final JPanel sterowanie = new JPanel();
    private final JTextArea tekst = new JTextArea();
    private final JSplitPane splitpanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JFrame dodawanie = new JFrame();


    private JButton[][] elemMapy;

    private List<Organizm> O;
    private  Swiat S;
    private int Y, X;

    public Symulacja(int Y, int X) {
        this.Y = Y;
        this.X = X;
        S = new Swiat(Y, X, this);
        O = dodajBazoweOrganizmy();
        S.dodajOrganizmy(O);
        sdo = new SluchaczDodawaniaOrganizmu[Y][X];
        for(int y = 0; y < Y; y++){
            for(int x = 0; x < X; x++){
                sdo[y][x] = new SluchaczDodawaniaOrganizmu(y, x);
            }
        }
        inicjujGuziki();
        inicjujOkno();
    }

    public void symuluj(){

        okno.setVisible(true);

    }

    private void aktualizujMape(){
        char[][] plansza = S.GetPlansza();
        for(int y = 0; y < Y; y++){
            for(int x = 0; x < X; x++){
                elemMapy[y][x].setText(""+plansza[y][x]);
                //if(plansza[y][x] == ' ')

            }
        }
    }

    private void inicjujOkno(){
        splitpanel.setLeftComponent(mapa);
        splitpanel.setRightComponent(sterowanie);
        splitpanel.setRightComponent(sterowanie);
        okno.setContentPane(splitpanel);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.pack();
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
        elemMapy = new JButton[Y][X];
        for(int y = 0; y < Y; y++){
            for(int x = 0; x < X; x++){
                elemMapy[y][x] = new JButton();
                elemMapy[y][x].setFocusable(false);
                elemMapy[y][x].addActionListener(sdo[y][x]);
                mapa.add(elemMapy[y][x]);
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
    private void InitMenuDodawania(){
        test = new JPanel();
        nowyOrg = new JButton[10];
        for(int i = 0; i < 10; i++)
            nowyOrg[i] = new JButton();
        nowyOrg[0].setText("Wilk");
        nowyOrg[1].setText("Owca");
        nowyOrg[2].setText("Zolw");
        nowyOrg[3].setText("Lis");
        nowyOrg[4].setText("Antylopa");
        nowyOrg[5].setText("Trawa");
        nowyOrg[6].setText("Mlecz");
        nowyOrg[7].setText("Guarana");
        nowyOrg[8].setText("Wilcze Jagody");
        nowyOrg[9].setText("Barszcz");
        for(int i = 0; i < 10; i++){
            usunSluchaczy(nowyOrg[i]);
            test.remove(nowyOrg[i]);
        }
    }
    public void test(){
        System.out.println("TEST");
    }

    private List<Organizm> dodajBazoweOrganizmy() {
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

    private class SluchaczNowejTury implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            S.nowaTura();
            aktualizujMape();
            okno.setTitle("Tura "+S.GetTura());
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
            okno.setTitle("Tura "+S.GetTura());
        }
    }
    private class SluchaczDodawaniaOrganizmu implements ActionListener{
        private int  y1, x1;
        public SluchaczDodawaniaOrganizmu(int y, int x){
            this.y1 = y;
            this.x1 = x;

        }
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("{" +y1+" "+x1);
            InitMenuDodawania();



            for(int i = 0; i < 10; i++){
                test.add(nowyOrg[i]);
                nowyOrg[i].addActionListener(new SluchaczDodawaniaKonkretnegoOrganizmu(i, y1, x1));
            }
            test.setLayout(new GridLayout(10, 1));
            dodawanie.pack();
            dodawanie.add(test);
            dodawanie.setTitle("Dodaj organizm");

            //dodawanie.setAutoRequestFocus(true);
            dodawanie.setAlwaysOnTop(true);
            dodawanie.setVisible(true);
            dodawanie.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        }
        private class SluchaczDodawaniaKonkretnegoOrganizmu implements ActionListener{
            private int id, y2, x2;
            public SluchaczDodawaniaKonkretnegoOrganizmu(int id, int y, int x){
                this.id = id;
                this.y2 = y;
                this.x2 = x;
                System.out.println(y2+" "+x2+"}");
            }
            @Override
            public void actionPerformed(ActionEvent e) {
               // S.dodajNowyOrganizmNa(id, y, x);
                System.out.println(y2+" "+x2+"}");
                //String text = ((JButton) e.getSource()).getText();
                //JOptionPane.showMessageDialog(null, text);
                dodawanie.setVisible(false);

                //aktualizujMape();
            }
        }
    }

    private void usunSluchaczy(JButton Guzik){
        for(ActionListener al : Guzik.getActionListeners()){
            Guzik.removeActionListener(al);
        }
    }





}
/*
    private class SluczaczRuchu implements KeyListener {
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
 */