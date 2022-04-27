package pl.edu.pg.eti.ksg.po.silnik;

import java.util.Random;

public abstract class Zwierze extends PojedynczyOrganizm{
    protected int nextX;
    protected int nextY;
    protected int prevX;
    protected int prevY;

    public Zwierze(int s, int i){
        sila = s;
        inicjatywa = i;
        wiek = 0;
        zyje = true;
        prevY = y;
        prevX = x;
        nextY = y;
        nextX = x;
    }
    public void akcja(){
        nowaPozycja();
        Organizm kolizyjny = swiat.pobierzWspolrzedne(nextY, nextX);
        wykonunajRuchNa(kolizyjny);
        x = nextX;
        y = nextY;
    }
    public void nowaPozycja(){
        int dx = 1;
        int dy = 1;
        dx = Swiat.rand.nextInt() % 3 -1;
        dy = Swiat.rand.nextInt() % 3 -1;;
        if (swiat.sprawdzPoprawnoscWspolrzednych(y + dy, x + dx)) {
            nextX = x + dx;
            nextY = y + dy;
            prevX = x;
            prevY = y;
        }
        System.out.println(rysowanie()+" idzie na "+nextY+" "+nextX);
    }
    public void wycofajSie(){
        nextX = prevX;
        nextY = prevY;
    }
    public void wykonunajRuchNa(Organizm kolizyjny){
        if (kolizyjny != null && kolizyjny != this) {
            if (porownajGatunek(kolizyjny)) {
                System.out.println("wykonunajRuchNa");
                rozmnazanie(kolizyjny);
                nextX = x;
                nextY = y;
            }
            else {
                System.out.println("wykonunajRuchNa");
                kolizyjny.kolizja(this);
            }
        }
    }

    public boolean porownajGatunek(Organizm drugi){
        if(drugi instanceof Zwierze)
            return true;
        return false;
    }
}
