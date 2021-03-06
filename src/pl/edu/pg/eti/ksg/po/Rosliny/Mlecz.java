package pl.edu.pg.eti.ksg.po.Rosliny;

import pl.edu.pg.eti.ksg.po.Silnik.Organizm.Organizm;
import pl.edu.pg.eti.ksg.po.Silnik.Organizm.Roslina;
import pl.edu.pg.eti.ksg.po.Silnik.Swiat;

public class Mlecz extends Roslina {
    public Mlecz(int posY, int posX) {
        super(0);
        x = posX;
        y = posY;
        szansaSiewu = Swiat.SZANSA_MLECZ;
    }
    public Mlecz(int posY, int posX, int wiek) {
        this(posY, posX);
        this.wiek = wiek;
    }
    public char rysowanie()  {
        return '*';
    }
    @Override
    public void akcja(){
        for(int i = 0; i < 3; i++)
            rozmnazanie(this);
    }
    @Override
    public boolean porownajGatunek(Organizm drugi) {
        if (drugi instanceof Mlecz)
            return true;
        else
            return false;
    }
    @Override
    public Organizm stworzNowy(int nowyY, int nowyX) {
        return new Mlecz(nowyY, nowyX);
    }
}
