package pl.edu.pg.eti.ksg.po.Zwierzeta;

import pl.edu.pg.eti.ksg.po.silnik.Organizm;
import pl.edu.pg.eti.ksg.po.silnik.Zwierze;

public class Wilk extends Zwierze {
    public Wilk(int posY, int posX) {
        super(9, 5);
        x = posX;
        y = posY;
        prevX = x;
        prevY = y;
    }
    public Wilk(int posY, int posX, int wiek) {
        this(posY, posX);
        this.wiek = wiek;
    }
    public char rysowanie()  {
        return 'W';
    }
    @Override
    public boolean porownajGatunek(Organizm drugi) {
        if (drugi instanceof Wilk)
            return true;
        else
            return false;
    }
    @Override
    public Organizm stworzNowy(int nowyY, int nowyX) {
        return new Wilk(nowyY, nowyX);
    }
}
