package pl.edu.pg.eti.ksg.po.Rosliny;

import pl.edu.pg.eti.ksg.po.Silnik.Organizm;
import pl.edu.pg.eti.ksg.po.Silnik.Roslina;
import pl.edu.pg.eti.ksg.po.Silnik.Swiat;

public class Trawa extends Roslina {
    public Trawa(int posY, int posX) {
        super(0);
        x = posX;
        y = posY;
        szansaSiewu = Swiat.SZANSA_TRAWA;
    }
    public Trawa(int posY, int posX, int wiek) {
        this(posY, posX);
        this.wiek = wiek;
    }
    public char rysowanie()  {
        return '#';
    }
    @Override
    public boolean porownajGatunek(Organizm drugi) {
        if (drugi instanceof Trawa)
            return true;
        else
            return false;
    }
    @Override
    public Organizm stworzNowy(int nowyY, int nowyX) {
        return new Trawa(nowyY, nowyX);
    }
}
