package pl.edu.pg.eti.ksg.po.Rosliny;

import pl.edu.pg.eti.ksg.po.Silnik.Organizm.Organizm;
import pl.edu.pg.eti.ksg.po.Silnik.Organizm.Roslina;
import pl.edu.pg.eti.ksg.po.Silnik.Aplikacja.Swiat;

public class WilczeJagody extends Roslina {
    public WilczeJagody(int posY, int posX) {
        super(99);
        x = posX;
        y = posY;
        szansaSiewu = Swiat.SZANSA_JAGODY;
    }
    public WilczeJagody(int posY, int posX, int wiek) {
        this(posY, posX);
        this.wiek = wiek;
    }
    public char rysowanie()  {
        return '%';
    }
    @Override
    public void kolizja(Organizm atakujacy){
        System.out.println(atakujacy.rysowanie()+" zjadlo wilcze jagody i umiera");
        atakujacy.smierc();
        smierc();
    }

    @Override
    public boolean porownajGatunek(Organizm drugi) {
        if (drugi instanceof WilczeJagody)
            return true;
        else
            return false;
    }
    @Override
    public Organizm stworzNowy(int nowyY, int nowyX) {
        return new WilczeJagody(nowyY, nowyX);
    }
}
