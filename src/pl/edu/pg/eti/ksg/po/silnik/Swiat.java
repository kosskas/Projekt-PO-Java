package pl.edu.pg.eti.ksg.po.silnik;

import pl.edu.pg.eti.ksg.po.Rosliny.*;
import pl.edu.pg.eti.ksg.po.Zwierzeta.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class Swiat {
    //ruch czlowieka, listener wydarzen
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
    private boolean gra;

    //////////////Aplikacja/////////////
    private JFrame okno = new JFrame();
    private JButton nowaTura;
    private JButton zapis;
    private JButton odczyt;
    private JButton[][] elemMapy;
    private final JPanel mapa = new JPanel();
    private final JPanel sterowanie = new JPanel();
    private JTextArea tekst;
    private final JSplitPane splitpanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    ////////////////////////////////////////////
    public Swiat(int Y, int X) {
        wymX = X;
        wymY = Y;
        plansza = new char[Y][X];
        seed = System.currentTimeMillis();
        tura = 0;
        gra = true;
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
            PrintWriter zapis = new PrintWriter("zapis.txt", "UTF-8");
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

        }catch (IOException ex){}
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
        catch (Exception ex){}
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
        rysujSwiat();
        wykonajTure();
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
        if (x < wymX && x >= 0 && y < wymY && y >= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void dodajOrganizm(Organizm nowy){
        nowy.SetSwiat(this);
        dzieci.add(nowy);
    }

    private void zarzadzajOrganizmami(){
        organizm.addAll(dzieci);
        organizm.removeIf(N -> !N.CzyZyje());
        dzieci.clear();
        organizm.sort((A, B) -> A.porownajOrganizmy(B));
    }

    public void dodajNowyOrganizmNa(int id, int y, int x){
        Organizm N = null;
        switch (id){
            case 0:
                N = new Wilk(y, x);
                break;
            case 1:
                N = new Owca(y, x);
                break;
            case 2:
                N = new Zolw(y, x);
                break;
            case 3:
                N = new Lis(y, x);
                break;
            case 4:
                N = new Antylopa(y, x);
                break;
            case 5:
                N = new Trawa(y, x);
                break;
            case 6:
                N = new Mlecz(y, x);
                break;
            case 7:
                N = new Guarana(y, x);
                break;
            case 8:
                N = new WilczeJagody(y, x);
                break;
            case 9:
                N = new BarszczSosnowskiego(y, x);
                break;
            default:
                break;
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

    ///////////////////////////////////////

    public void test(){
        System.out.println("TEST");
    }

    private void aktualizujMape(){
        char[][] plansza = GetPlansza();
        for(int y = 0; y < wymY; y++){
            for(int x = 0; x < wymX; x++){
                elemMapy[y][x].setText(""+plansza[y][x]);
                //if(plansza[y][x] == ' ')

            }
        }
    }

    private void inicjujGuziki(){
        mapa.setLayout(new GridLayout(wymY+ 1, wymX));
        InitMapa();
        InitTura();
        InitZapis();
        InitOdczyt();
        // tekst.append("rtdetg");
        //tekst.setEditable(false);
        // sterowanie.add(tekst);
    }

    private void inicjujOkno(){
        splitpanel.setLeftComponent(mapa);
        splitpanel.setRightComponent(sterowanie);
        splitpanel.setRightComponent(sterowanie);
        okno.setContentPane(splitpanel);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.pack();
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
        nowaTura = new JButton();
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
            okno.setTitle("Tura "+GetTura());
        }
    }

    private class SluchaczZapisu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            zapiszSwiat();
        }
    }

    private class SluchaczOdczytu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            wczytajSwiat();
            rysujSwiat();
            aktualizujMape();
            okno.setTitle("Tura "+GetTura());
        }
    }

    private class SluchaczDodawaniaOrganizmu implements ActionListener{
        private final int y, x;
        private final JPanel test;
        private final JButton[] nowyOrg;
        private final JFrame dodawanie = new JFrame();
        public SluchaczDodawaniaOrganizmu(int y, int x){
            this.y = y;
            this.x = x;
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
                test.add(nowyOrg[i]);
            }
            dodawanie.pack();
            dodawanie.add(test);
            dodawanie.setTitle("Dodaj organizm");
            dodawanie.setAlwaysOnTop(true);
            dodawanie.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            for(int i = 0; i < 10; i++){
                test.add(nowyOrg[i]);
                nowyOrg[i].addActionListener(new SluchaczDodawaniaKonkretnegoOrganizmu(i));
            }
            test.setLayout(new GridLayout(10, 1));
            dodawanie.pack();
            dodawanie.add(test);
            dodawanie.setTitle("Dodaj organizm");
            dodawanie.setAlwaysOnTop(true);
            dodawanie.setVisible(true);
            dodawanie.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
}
