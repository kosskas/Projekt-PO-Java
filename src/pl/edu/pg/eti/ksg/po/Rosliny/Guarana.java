package pl.edu.pg.eti.ksg.po.Rosliny;

import pl.edu.pg.eti.ksg.po.Silnik.Organizm.Organizm;
import pl.edu.pg.eti.ksg.po.Silnik.Organizm.Roslina;
import pl.edu.pg.eti.ksg.po.Silnik.Aplikacja.Swiat;

public class Guarana extends Roslina {
    public Guarana(int posY, int posX) {
        super(0);
        x = posX;
        y = posY;
        szansaSiewu = Swiat.SZANSA_GUARANA;
    }
    public Guarana(int posY, int posX, int wiek) {
        this(posY, posX);
        this.wiek = wiek;
    }
    public char rysowanie()  {
        return '&';
    }

    @Override
    public void kolizja(Organizm atakujacy){
        atakujacy.SetSila(atakujacy.GetSila() + 3);
        System.out.println(atakujacy.rysowanie()+" zjadlo guarane i zwiekszylo sile do "+atakujacy.GetSila());
        smierc();
    }
    @Override
    public boolean porownajGatunek(Organizm drugi) {
        if (drugi instanceof Guarana)
            return true;
        else
            return false;
    }
    @Override
    public Organizm stworzNowy(int nowyY, int nowyX) {
        return new Guarana(nowyY, nowyX);
    }
}
