package pl.edu.pg.eti.ksg.po.silnik;

import java.io.*;
import java.util.*;

public class Swiat {
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
    private List<Organizm> organizm;
    private List<Organizm> dzieci = new LinkedList<>();
    private int tura;
    private long seed;
    private boolean gra;

    public Swiat(int Y, int X, List<Organizm> L) {
        wymX = X;
        wymY = Y;
        plansza = new char[Y][X];
        dodajOrganizmy(L);
        seed = System.currentTimeMillis();
        tura = 0;
        gra = true;
        rand.setSeed(seed);

    }

    public Swiat(int Y, int X, List<Organizm> L, long ziarno, int runda) {
        this(Y, X, L);
        seed = ziarno;
        tura = runda;
    }

    public char[][] GetPlansza(){
        return plansza;
    }

    public char GetPos(int Y, int X){
        return plansza[Y][X];
    }

    private void wyczyscMape(){
        for(int y = 0; y < wymY; y++){
            for(int x = 0; x < wymX; x++){
                plansza[y][x] = '.';
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

    private void dodajOrganizmy(List<Organizm> L){
        organizm = L;
        for(Organizm N : L){
            N.SetSwiat(this);
        }
    }

    public void zapiszSwiat(){
        try {
            PrintWriter zapis = new PrintWriter("zapis.txt", "UTF-8");
            zapis.println("[stan]");
            zapis.println("MAPA"+" "+ GetY()+" "+GetX());
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
            String strLine, cmd[];
            while ((strLine = br.readLine()) != null)   {
                ///String[] tokens = strLine.split(" ");
                System.out.println(strLine);
            }
            in.close();
        }
        catch (Exception ex){}
    }

    public void rysujSwiat(){
        wyczyscMape();
        naniesOrganizmyNaMape();
        rysujMape();
    }

    public void rysujMape(){
        System.out.print("   ");
        int pom = -1;
        for (int x = 0; x < wymX; x++) {
            if (x % 10 == 0)
                pom++;
            System.out.print(pom+" ");
        }
        System.out.print("\n   ");
        for (int x = 0; x < wymX; x++) {
            System.out.print(x % 10+" ");
        }
        System.out.print('\n');
        pom = -1;
        for (int y = 0; y < wymY; y++) {
            if (y % 10 == 0)
                pom++;
            System.out.print(pom+""+y % 10+" ");
            for (int x = 0; x < wymX; x++) {
                System.out.print(plansza[y][x]+" ");
            }
            System.out.print('\n');
        }
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
        for(Organizm N : organizm){
            N.nowaTura();
            if(N.GetWiek() > 1 && N.CzyZyje())
                N.akcja();
        }

    }
    public void nowaTura(){
        rysujSwiat();
        wykonajTure();
    }

    public void symuluj(int liczbaRund){
        for (int i = tura; i < liczbaRund && gra; i++) {
            System.out.println("\tTura "+i);
            nowaTura();
        }
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
}
