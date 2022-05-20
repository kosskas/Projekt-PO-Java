package pl.edu.pg.eti.ksg.po.Silnik.Organizm;

import pl.edu.pg.eti.ksg.po.Silnik.Swiat;

public abstract class Zwierze extends PojedynczyOrganizm {
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
        if(!wykonalRuch) {
            System.out.println(rysowanie() + " wykonuje ruch");
            nowaPozycja();
            Organizm kolizyjny = swiat.pobierzWspolrzedne(nextY, nextX);
            wykonunajRuchNa(kolizyjny);
            x = nextX;
            y = nextY;
            wykonalRuch = true;
        }
    }
    public void nowaPozycja(){
        int dx = -1;
        int dy = -1;
        dx = Math.abs(Swiat.rand.nextInt()) % 3 -1;
        dy = Math.abs(Swiat.rand.nextInt()) % 3 -1;;
        if(swiat.isCzyHex()){
            while ((dy == 1 && dx == 1) || (dy == 1 && dx == -1)){
                dx = Math.abs(Swiat.rand.nextInt()) % 3 -1;
                dy = Math.abs(Swiat.rand.nextInt()) % 3 -1;;
            }
        }
        if (swiat.sprawdzPoprawnoscWspolrzednych(y + dy, x + dx)) {
            nextX = x + dx;
            nextY = y + dy;
            prevX = x;
            prevY = y;
        }
    }
    public void wycofajSie(){
        nextX = prevX;
        nextY = prevY;
    }
    public void wykonunajRuchNa(Organizm kolizyjny){
        if (kolizyjny != null && kolizyjny != this) {
            if (porownajGatunek(kolizyjny)) {
                rozmnazanie(kolizyjny);
                nextX = x;
                nextY = y;
            }
            else {
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
