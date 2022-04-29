package pl.edu.pg.eti.ksg.po.silnik;

import java.sql.Time;
import java.text.Collator;
import java.util.*;

public class Swiat {
    public static final int WIEK_ROZMNAZANIA = 2;
    public static final Random rand = new Random();
    private char[][] plansza;
    private int wymX;
    private int wymY;
    private List<Organizm> organizm;
    private List<Organizm> dzieci = new LinkedList<>();
    private int tura;
    private int seed;
    private boolean gra;

    public Swiat(int Y, int X, List<Organizm> L) {
        wymX = X;
        wymY = Y;
        plansza = new char[Y][X];
        dodajOrganizmy(L);
        seed = 0;
        tura = 0;
        gra = true;
        rand.setSeed(seed);

    }

    public Swiat(int Y, int X, List<Organizm> L, int ziarno, int runda) {
        this(Y, X, L);
        seed = ziarno;
        tura = runda;
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
        System.out.println("[stan]");
        System.out.println("MAPA "+ wymY+""+wymX);
        System.out.print("SEED "+seed);
        System.out.print("TURA "+tura);
        System.out.println("[org]");
        for(Organizm N : organizm) {
            System.out.println(N.rysowanie()+" "+N.GetY()+" "+N.GetX()+" "+N.GetWiek()+"\n");
        }
        System.out.println("[org]");
        System.out.println("[stan]");
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

    public void wykonajTure(){
        tura++;
        zarzadzajOrganizmami();

        for(Organizm N : organizm){
            N.nowaTura();
            if(N.GetWiek() > 1 && N.CzyZyje())
                N.akcja();
        }

    }

    public void symuluj(int liczbaRund){
        for (int i = tura; i < liczbaRund && gra; i++) {
            System.out.println("\tTura "+i);
            rysujSwiat();
            wykonajTure();
        }
    }

    public Organizm pobierzWspolrzedne(int y, int x){
        for (Organizm N : organizm) {
            if (N.GetX() == x && N.GetY() == y && N.CzyZyje())
                return N;
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
        organizm.removeIf(N -> !N.CzyZyje());
        for (Organizm N :dzieci) {
            organizm.add(N);
        }
        dzieci.clear();
        organizm.sort((A, B) -> A.porownajOrganizmy(B));
    }
}
