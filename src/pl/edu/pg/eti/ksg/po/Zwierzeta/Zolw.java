package pl.edu.pg.eti.ksg.po.Zwierzeta;

import pl.edu.pg.eti.ksg.po.Silnik.Organizm.Organizm;
import pl.edu.pg.eti.ksg.po.Silnik.Aplikacja.Swiat;
import pl.edu.pg.eti.ksg.po.Silnik.Organizm.Zwierze;

public class Zolw extends Zwierze {
    public Zolw(int posY, int posX) {
        super(2, 1);
        x = posX;
        y = posY;
        prevX = x;
        prevY = y;
    }
    public Zolw(int posY, int posX, int wiek) {
        this(posY, posX);
        this.wiek = wiek;
    }
    public char rysowanie()  {
        return 'Z';
    }
    @Override
    public void nowaPozycja(){
        if(Swiat.rand.nextInt() % 100 <= 25){
            super.nowaPozycja();
        }
        else {
            nextX = x;
            nextY = y;
        }
    }
    @Override
    public void kolizja(Organizm atakujacy){
        if(atakujacy.GetSila() < 5){
            Zwierze atakujace = (Zwierze)atakujacy;
            atakujace.wycofajSie();
        }
        else{
            smierc();
        }
    }
    @Override
    public boolean porownajGatunek(Organizm drugi) {
        if (drugi instanceof Zolw)
            return true;
        else
            return false;
    }
    @Override
    public Organizm stworzNowy(int nowyY, int nowyX) {
        return new Zolw(nowyY, nowyX);
    }
}