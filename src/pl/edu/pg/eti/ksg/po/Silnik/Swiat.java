package pl.edu.pg.eti.ksg.po.Silnik;

import pl.edu.pg.eti.ksg.po.Rosliny.*;
import pl.edu.pg.eti.ksg.po.Zwierzeta.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class Swiat {
    //sortowanie??
    ////////////////Organizmy/////////////////
    public static final int WIEK_ROZMNAZANIA = 2;
    public static final int SZANSA_JAGODY = 3;
    public static final int SZANSA_BARSZCZ = 1;
    public static final int SZANSA_MLECZ = 2;
    public static final int SZANSA_GUARANA = 2;
    public static final int SZANSA_TRAWA = 3;
    public static final Random rand = new Random();
    private char[][] plansza;
    private int wymX;
    private int wymY;
    private Czlowiek czlowiek = null;
    private List<Organizm> organizm;
    private List<Organizm> dzieci = new LinkedList<>();
    private int tura;
    private long seed;
    private int czlowiek_dx = 0, czlowiek_dy = 0;

    //////////////Aplikacja/////////////
    private final JFrame okno = new JFrame();
    private JButton[][] elemMapy;
    private final JPanel mapa = new JPanel();
    private final JPanel sterowanie = new JPanel();
    private final JSplitPane splitpanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    ////////////////////////////////////////////
    public Swiat(int Y, int X) {
        wymX = X;
        wymY = Y;
        plansza = new char[Y][X];
        seed = System.currentTimeMillis();
        tura = 0;
        rand.setSeed(seed);
        dodajOrganizmy(dodajBazoweOrganizmy());
        inicjujGuziki();
        inicjujOkno();
    }

    public char[][] GetPlansza(){
        return plansza;
    }

    private void wyczyscMape(){
        for(int y = 0; y < wymY; y++){
            for(int x = 0; x < wymX; x++){
                plansza[y][x] = ' ';
            }
        }
    }

    private void naniesOrganizmyNaMape(){
        for(Organizm N : organizm){
            if(N.CzyZyje()){
                plansza[N.GetY()][N.GetX()] = N.rysowanie();
            }
        }
    }

    public void dodajOrganizmy(List<Organizm> L){
        organizm = L;
        for(Organizm N : L){
            N.SetSwiat(this);
            if(N instanceof Czlowiek){
                czlowiek = (Czlowiek)N;
            }
        }
    }

    public void zapiszSwiat(){
        try {
            PrintWriter zapis = new PrintWriter("zapis.txt", StandardCharsets.UTF_8);
            zapis.println("[stan]");
            zapis.println("MAPA"+" "+GetY()+" "+GetX());
            zapis.println("SEED"+" "+seed);
            zapis.println("TURA"+" "+tura);
            zapis.println("[org]");
            for(Organizm N : organizm) {
                zapis.println(N.rysowanie()+" "+N.GetY()+" "+N.GetX()+" "+N.GetWiek());
            }
            zapis.println("[org]");
            zapis.println("[stan]");
            zapis.close();
        }catch (IOException ignored){}
    }

    public void wczytajSwiat(){
        try{
            FileInputStream odczyt = new FileInputStream("zapis.txt");
            DataInputStream in = new DataInputStream(odczyt);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            ArrayList<String> komendy = new ArrayList<>();
            while ((strLine = br.readLine()) != null)   {
                String[] tokens = strLine.split(" ");
                for (String individual : tokens)
                    komendy.add(individual);
            }
            wczytajGre(komendy);
            in.close();
        }
        catch (Exception ignored){}
    }

    public void rysujSwiat(){
        wyczyscMape();
        naniesOrganizmyNaMape();
    }

    public int GetX() {
        return wymX;
    }

    public int GetY() {
        return wymY;
    }

    public int GetTura(){
        return tura;
    }

    public void wykonajTure(){
        tura++;
        zarzadzajOrganizmami();

        czlowiek.nowaTura();
        if(czlowiek.GetWiek() > 1 && czlowiek.CzyZyje())
            czlowiek.akcja();
        for(Organizm N : organizm){
            if(N instanceof Czlowiek)
                continue;
            N.nowaTura();
            if(N.GetWiek() > 1 && N.CzyZyje())
                N.akcja();
        }
    }

    public void nowaTura(){
        wykonajTure();
        rysujSwiat();
    }

    public void symuluj(){
        okno.setVisible(true);
    }

    public Organizm pobierzWspolrzedne(int y, int x){
        for (Organizm N : organizm) {
            if (N.GetX() == x && N.GetY() == y && N.CzyZyje())
                return N;
        }
        for (Organizm M : dzieci){
            if (M.GetX() == x && M.GetY() == y && M.CzyZyje())
                return M;
        }
        return null;
    }

    public boolean sprawdzPoprawnoscWspolrzednych(int y, int x){
        return x < wymX && x >= 0 && y < wymY && y >= 0;
    }

    public void dodajOrganizm(Organizm nowy){
        nowy.SetSwiat(this);
        dzieci.add(nowy);
    }

    private void zarzadzajOrganizmami(){
        organizm.addAll(dzieci);
        organizm.removeIf(N -> !N.CzyZyje());
        dzieci.clear();
        organizm.sort(Organizm::porownajOrganizmy);
    }

    public void dodajNowyOrganizmNa(int id, int y, int x){
        Organizm N = null;
        switch (id) {
            case 0 -> N = new Wilk(y, x);
            case 1 -> N = new Owca(y, x);
            case 2 -> N = new Zolw(y, x);
            case 3 -> N = new Lis(y, x);
            case 4 -> N = new Antylopa(y, x);
            case 5 -> N = new Trawa(y, x);
            case 6 -> N = new Mlecz(y, x);
            case 7 -> N = new Guarana(y, x);
            case 8 -> N = new WilczeJagody(y, x);
            case 9 -> N = new BarszczSosnowskiego(y, x);
            default -> {}
        }
        if(N != null){
            organizm.add(N);
            N.SetSwiat(this);
            System.out.println("Dodano "+N.rysowanie()+" na "+N.GetY()+" "+N.GetX());
        }
    }

    private void wczytajGre(ArrayList<String> Gra) {
        List<Organizm> L = new LinkedList<>();
        int y = 0, x = 0;
        long ziarno = 0;
        int runda = 0;
        int posX, posY, wiek;
        for (int i = 0; i < Gra.size(); i++) {
            if (Gra.get(i).equals("MAPA")) {
                y = Integer.parseInt(Gra.get(i + 1));
                x = Integer.parseInt(Gra.get(i + 2));
            }
            if (Gra.get(i).equals("SEED")) {
                ziarno = Long.parseLong(Gra.get(i + 1));
            }
            if (Gra.get(i).equals("TURA")) {
                runda = Integer.parseInt(Gra.get(i + 1));
            }
            if (Gra.get(i).equals("W")) {
                posY = Integer.parseInt(Gra.get(i + 1));
                posX = Integer.parseInt(Gra.get(i + 2));
                wiek = Integer.parseInt(Gra.get(i + 3));
                L.add(new Wilk(posY, posX, wiek));
            }
            if (Gra.get(i).equals("O")) {
                posY = Integer.parseInt(Gra.get(i + 1));
                posX = Integer.parseInt(Gra.get(i + 2));
                wiek = Integer.parseInt(Gra.get(i + 3));
                L.add(new Owca(posY, posX, wiek));
            }
            if (Gra.get(i).equals("L")) {
                posY = Integer.parseInt(Gra.get(i + 1));
                posX = Integer.parseInt(Gra.get(i + 2));
                wiek = Integer.parseInt(Gra.get(i + 3));
                L.add(new Lis(posY, posX, wiek));
            }
            if (Gra.get(i).equals("A")) {
                posY = Integer.parseInt(Gra.get(i + 1));
                posX = Integer.parseInt(Gra.get(i + 2));
                wiek = Integer.parseInt(Gra.get(i + 3));
                L.add(new Antylopa(posY, posX, wiek));
            }
            if (Gra.get(i).equals("Z")) {
                posY = Integer.parseInt(Gra.get(i + 1));
                posX = Integer.parseInt(Gra.get(i + 2));
                wiek = Integer.parseInt(Gra.get(i + 3));
                L.add(new Zolw(posY, posX, wiek));
            }
            if (Gra.get(i).equals("C")) {
                posY = Integer.parseInt(Gra.get(i + 1));
                posX = Integer.parseInt(Gra.get(i + 2));
                wiek = Integer.parseInt(Gra.get(i + 3));
                czlowiek = new Czlowiek(posY, posX, wiek, this);
                L.add(czlowiek);
            }
            if (Gra.get(i).equals("$")) {
                posY = Integer.parseInt(Gra.get(i + 1));
                posX = Integer.parseInt(Gra.get(i + 2));
                wiek = Integer.parseInt(Gra.get(i + 3));
                L.add(new BarszczSosnowskiego(posY, posX, wiek));
            }
            if (Gra.get(i).equals("%")) {
                posY = Integer.parseInt(Gra.get(i + 1));
                posX = Integer.parseInt(Gra.get(i + 2));
                wiek = Integer.parseInt(Gra.get(i + 3));
                L.add(new WilczeJagody(posY, posX, wiek));
            }
            if (Gra.get(i).equals("#")) {
                posY = Integer.parseInt(Gra.get(i + 1));
                posX = Integer.parseInt(Gra.get(i + 2));
                wiek = Integer.parseInt(Gra.get(i + 3));
                L.add(new Trawa(posY, posX, wiek));
            }
            if (Gra.get(i).equals("*")) {
                posY = Integer.parseInt(Gra.get(i + 1));
                posX = Integer.parseInt(Gra.get(i + 2));
                wiek = Integer.parseInt(Gra.get(i + 3));
                L.add(new Mlecz(posY, posX, wiek));
            }
            if (Gra.get(i).equals("&")) {
                posY = Integer.parseInt(Gra.get(i + 1));
                posX = Integer.parseInt(Gra.get(i + 2));
                wiek = Integer.parseInt(Gra.get(i + 3));
                L.add(new Guarana(posY, posX, wiek));
            }
        }
        if (y != 0 && x != 0 && !L.isEmpty()) {
            dzieci.clear();
            dodajOrganizmy(L);
            tura = runda;
            seed = ziarno;
            rand.setSeed(seed);
            wymX = x;
            wymY = y;
            plansza = new char[wymY][wymX];
        }
    }

    private List<Organizm> dodajBazoweOrganizmy() {
        List<Organizm> L = new LinkedList<>();
        L.add(new Czlowiek(0, 0, this));
        L.add(new Owca(1, 0));
        L.add(new Owca(0, 1));
        L.add(new BarszczSosnowskiego(4, 0));
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

    private Color kolorowanieMapy(int y, int x){
        char punkt = plansza[y][x];
        return switch (punkt) {
            case 'W' -> new Color(204, 204, 204); //wilk
            case 'A' -> new Color(153, 102, 0);
            case 'L' -> Color.ORANGE;
            case 'O' -> Color.WHITE;
            case 'C' -> Color.PINK;
            case 'Z' -> Color.GRAY;
            case '&' -> Color.RED; //guarana
            case '#' -> new Color(0, 102, 0);
            case '%' -> new Color(102, 0, 153);
            case '$' -> new Color(0, 255, 51);
            case '*' -> Color.YELLOW;
            default -> null;
        };
    }

    public int GetCzlowiekDX(){
        return czlowiek_dx;
    }

    public int GetCzlowiekDY(){
        return czlowiek_dy;
    }

    ///////////////////////////////////////

    private void aktualizujMape(){
        char[][] plansza = GetPlansza();
        for(int y = 0; y < wymY; y++){
            for(int x = 0; x < wymX; x++){
                elemMapy[y][x].setText(""+plansza[y][x]);
                elemMapy[y][x].setBackground(kolorowanieMapy(y, x));
            }
        }
    }

    private void inicjujGuziki(){
        mapa.setLayout(new GridLayout(wymY+ 1, wymX));
        InitMapa();
        InitTuraGuzik();
        InitZapisGuzik();
        InitOdczytGuzik();
    }

    private void inicjujOkno(){
        splitpanel.setLeftComponent(mapa);
        splitpanel.setRightComponent(sterowanie);
        okno.setContentPane(splitpanel);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setTitle("Symulator swiata");
        okno.addKeyListener(new SluchaczKlawiatury());
        okno.pack();
        okno.setSize(1280, 700);
    }

    private void InitMapa(){
        elemMapy = new JButton[wymY][wymX];
        for(int y = 0; y < wymY; y++){
            for(int x = 0; x < wymX; x++){
                elemMapy[y][x] = new JButton();
                elemMapy[y][x].setFocusable(false);
                elemMapy[y][x].addActionListener(new SluchaczDodawaniaOrganizmu(y, x));
                mapa.add(elemMapy[y][x]);
            }
        }
    }

    private void InitOdczytGuzik(){
        JButton odczyt = new JButton();
        odczyt.setFocusable(false);
        odczyt.setText("Wczytaj");
        odczyt.addActionListener(new SluchaczOdczytu());
        sterowanie.add(odczyt);
    }

    private void InitZapisGuzik(){
        JButton zapis = new JButton();
        zapis.setFocusable(false);
        zapis.setText("Zapisz");
        zapis.addActionListener(new SluchaczZapisu());
        sterowanie.add(zapis);
    }

    private void InitTuraGuzik(){
        JButton nowaTura = new JButton();
        nowaTura.setFocusable(false);
        nowaTura.addActionListener(new SluchaczNowejTury());
        nowaTura.setText("Nowa Tura!");
        nowaTura.setBounds(new Rectangle(50, 10));
        sterowanie.add(nowaTura);
    }

    private class SluchaczNowejTury implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            nowaTura();
            aktualizujMape();
            okno.setTitle("Symulator swiata - Tura "+GetTura());
        }
    }

    private class SluchaczZapisu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            zapiszSwiat();
            okno.setTitle("Zapisano ture "+GetTura());
        }
    }

    private class SluchaczOdczytu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            wczytajSwiat();
            rysujSwiat();
            aktualizujMape();
            okno.setTitle("Wczytano ture "+GetTura());
        }
    }

    private class SluchaczDodawaniaOrganizmu implements ActionListener{
        private final int y, x;
        private final JButton[] nowyOrg;
        private final JFrame dodawanie = new JFrame();
        public SluchaczDodawaniaOrganizmu(int y, int x){
            this.y = y;
            this.x = x;
            JPanel panel_dodawania = new JPanel();
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
                panel_dodawania.add(nowyOrg[i]);
            }
            panel_dodawania.setLayout(new GridLayout(10, 1));
            dodawanie.pack();
            dodawanie.setSize(300, 400);
            dodawanie.add(panel_dodawania);
            dodawanie.setTitle("Dodaj na "+y+" "+x);
            dodawanie.setAlwaysOnTop(true);
            dodawanie.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(plansza[y][x] == ' ') {
                for (int i = 0; i < 10; i++) {
                    nowyOrg[i].addActionListener(new SluchaczDodawaniaKonkretnegoOrganizmu(i));
                }
                dodawanie.setVisible(true);
            }
        }
        private class SluchaczDodawaniaKonkretnegoOrganizmu implements ActionListener{
            private final int id;
            public SluchaczDodawaniaKonkretnegoOrganizmu(int id){
                this.id = id;
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajNowyOrganizmNa(id, y, x);
                rysujSwiat();
                aktualizujMape();
                dodawanie.setVisible(false);
            }
        }
    }

    private class SluchaczKlawiatury implements KeyListener{
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                czlowiek_dx = -1;
                czlowiek_dy = 0;
                System.out.println("LEWO");
            }
            else if (key == KeyEvent.VK_RIGHT) {
                czlowiek_dx = 1;
                czlowiek_dy = 0;
                System.out.println("PRAWO");
            }
            else if (key == KeyEvent.VK_UP) {
                czlowiek_dy = -1;
                czlowiek_dx = 0;
                System.out.println("GORA");
            }
            else if (key == KeyEvent.VK_DOWN) {
                czlowiek_dy = 1;
                czlowiek_dx = 0;
                System.out.println("DOL");
            }
            else if (key == KeyEvent.VK_U) {
                czlowiek_dx = KeyEvent.VK_U;
                czlowiek_dy = KeyEvent.VK_U;
                System.out.println("ULT");
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {}
        @Override
        public void keyTyped(KeyEvent e) {}
    }
}