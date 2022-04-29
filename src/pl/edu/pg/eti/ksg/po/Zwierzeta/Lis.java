package pl.edu.pg.eti.ksg.po.Zwierzeta;

import pl.edu.pg.eti.ksg.po.silnik.Organizm;
import pl.edu.pg.eti.ksg.po.silnik.Zwierze;

public class Lis extends Zwierze {
    public Lis(int posY, int posX) {
        super(3, 7);
        x = posX;
        y = posY;
        prevX = x;
        prevY = y;
    }
    public Lis(int posY, int posX, int wiek) {
        this(posY, posX);
        this.wiek = wiek;
    }
    public char rysowanie()  {
        return 'L';
    }

    @Override
    public void wykonunajRuchNa(Organizm kolizyjny) {
        if (kolizyjny != null && kolizyjny != this && kolizyjny.GetSila() > sila) {
            System.out.println("\tLis nie wykonuje ruchu na " +kolizyjny.rysowanie());
            nextX = x;
            nextY = y;
        }
        else
            super.wykonunajRuchNa(kolizyjny);
    }
    @Override
    public boolean porownajGatunek(Organizm drugi) {
        if (drugi instanceof Lis)
            return true;
        else
            return false;
    }
    @Override
    public Organizm stworzNowy(int nowyY, int nowyX) {
        return new Lis(nowyY, nowyX);
    }
}
