package pl.edu.pg.eti.ksg.po.Zwierzeta;

import pl.edu.pg.eti.ksg.po.Silnik.Organizm;
import pl.edu.pg.eti.ksg.po.Silnik.Swiat;
import pl.edu.pg.eti.ksg.po.Silnik.Zwierze;

public class Antylopa extends Zwierze {
    public Antylopa(int posY, int posX) {
        super(4, 4);
        x = posX;
        y = posY;
        prevX = x;
        prevY = y;
    }

    public Antylopa(int posY, int posX, int wiek) {
        this(posY, posX);
        this.wiek = wiek;
    }

    public char rysowanie() {
        return 'A';
    }

    @Override
    public void nowaPozycja() {
        int dx = 1;
        int dy = 1;
        dx = Math.abs(Swiat.rand.nextInt()) % 5 -2;
        dy = Math.abs(Swiat.rand.nextInt()) % 5 -2;
        if (swiat.sprawdzPoprawnoscWspolrzednych(y+ dy, x + dx)) {
            nextX = x + dx;
            nextY = y + dy;
            prevX = x;
            prevY = y;
        }
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        if(Math.abs(Swiat.rand.nextInt()) % 100 <= 50){
            nowaPozycja();
            Organizm kolizyjny = swiat.pobierzWspolrzedne(nextY, nextX);
            if(kolizyjny != null && kolizyjny != this)
                super.kolizja(atakujacy);
            else{
                x = nextX;
                y = nextY;
            }
        }
        else {
            super.kolizja(atakujacy);
        }
    }

    @Override
    public boolean porownajGatunek(Organizm drugi) {
        if (drugi instanceof Antylopa)
            return true;
        else
            return false;
    }

    @Override
    public Organizm stworzNowy(int nowyY, int nowyX) {
        return new Antylopa(nowyY, nowyX);
    }
}
