package pl.edu.pg.eti.ksg.po.silnik;

import pl.edu.pg.eti.ksg.po.Rosliny.*;
import pl.edu.pg.eti.ksg.po.Zwierzeta.*;

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
    private Czlowiek czlowiek = null;
    private List<Organizm> organizm;
    private List<Organizm> dzieci = new LinkedList<>();
    private int tura;
    private long seed;
    private boolean gra;
    private Symulacja sym;

    public Swiat(int Y, int X, Symulacja S) {
        wymX = X;
        wymY = Y;
        plansza = new char[Y][X];
        seed = System.currentTimeMillis();
        tura = 0;
        gra = true;
        rand.setSeed(seed);
        sym = S;

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
    public Symulacja GetSymulacja(){
        return sym;
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

    private void wczytajGre(ArrayList<String> Gra){
        List<Organizm> L = new LinkedList<>();
        int y = 0, x = 0;
        long ziarno = 0;
        int runda = 0;
        int posX, posY, wiek;
        for(int i = 0; i < Gra.size(); i++){

            if(Gra.get(i).equals("MAPA")){
                y = Integer.parseInt(Gra.get(i+1));
                x = Integer.parseInt(Gra.get(i+2));
            }
            if(Gra.get(i).equals("SEED")){
                ziarno = Long.parseLong(Gra.get(i+1));
            }
            if(Gra.get(i).equals("TURA")){
                runda = Integer.parseInt(Gra.get(i+1));
            }
            if(Gra.get(i).equals("W")){
                posY = Integer.parseInt(Gra.get(i+1));
                posX = Integer.parseInt(Gra.get(i+2));
                wiek = Integer.parseInt(Gra.get(i+3));
                L.add(new Wilk(posY, posX, wiek));
            }
            if(Gra.get(i).equals("O")){
                posY = Integer.parseInt(Gra.get(i+1));
                posX = Integer.parseInt(Gra.get(i+2));
                wiek = Integer.parseInt(Gra.get(i+3));
                L.add(new Owca(posY, posX, wiek));
            }
            if(Gra.get(i).equals("L")){
                posY = Integer.parseInt(Gra.get(i+1));
                posX = Integer.parseInt(Gra.get(i+2));
                wiek = Integer.parseInt(Gra.get(i+3));
                L.add(new Lis(posY, posX, wiek));
            }
            if(Gra.get(i).equals("A")){
                posY = Integer.parseInt(Gra.get(i+1));
                posX = Integer.parseInt(Gra.get(i+2));
                wiek = Integer.parseInt(Gra.get(i+3));
                L.add(new Antylopa(posY, posX, wiek));
            }
            if(Gra.get(i).equals("Z")){
                posY = Integer.parseInt(Gra.get(i+1));
                posX = Integer.parseInt(Gra.get(i+2));
                wiek = Integer.parseInt(Gra.get(i+3));
                L.add(new Zolw(posY, posX, wiek));
            }
            if(Gra.get(i).equals("C")){
                posY = Integer.parseInt(Gra.get(i+1));
                posX = Integer.parseInt(Gra.get(i+2));
                wiek = Integer.parseInt(Gra.get(i+3));
                czlowiek = new Czlowiek(posY, posX, wiek, this);
                L.add(czlowiek);
            }
            if(Gra.get(i).equals("$")){
                posY = Integer.parseInt(Gra.get(i+1));
                posX = Integer.parseInt(Gra.get(i+2));
                wiek = Integer.parseInt(Gra.get(i+3));
                L.add(new BarszczSosnowskiego(posY, posX, wiek));
            }
            if(Gra.get(i).equals("%")){
                posY = Integer.parseInt(Gra.get(i+1));
                posX = Integer.parseInt(Gra.get(i+2));
                wiek = Integer.parseInt(Gra.get(i+3));
                L.add(new WilczeJagody(posY, posX, wiek));
            }
            if(Gra.get(i).equals("#")){
                posY = Integer.parseInt(Gra.get(i+1));
                posX = Integer.parseInt(Gra.get(i+2));
                wiek = Integer.parseInt(Gra.get(i+3));
                L.add(new Trawa(posY, posX, wiek));
            }
            if(Gra.get(i).equals("*")){
                posY = Integer.parseInt(Gra.get(i+1));
                posX = Integer.parseInt(Gra.get(i+2));
                wiek = Integer.parseInt(Gra.get(i+3));
                L.add(new Mlecz(posY, posX, wiek));
            }
            if(Gra.get(i).equals("&")){
                posY = Integer.parseInt(Gra.get(i+1));
                posX = Integer.parseInt(Gra.get(i+2));
                wiek = Integer.parseInt(Gra.get(i+3));
                L.add(new Guarana(posY, posX, wiek));
            }
        }
        if(y != 0 && x != 0 && !L.isEmpty()){
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
}
