package pl.edu.pg.eti.ksg.po.Rosliny;

import pl.edu.pg.eti.ksg.po.silnik.Organizm;
import pl.edu.pg.eti.ksg.po.silnik.Roslina;
import pl.edu.pg.eti.ksg.po.silnik.Swiat;

public class BarszczSosnowskiego extends Roslina {
    private final Organizm[] sasiad = new Organizm[8];
    public BarszczSosnowskiego(int posY, int posX) {
        super(10);
        x = posX;
        y = posY;
        szansaSiewu = Swiat.SZANSA_BARSZCZ;
    }
    public BarszczSosnowskiego(int posY, int posX, int wiek) {
        this(posY, posX);
        this.wiek = wiek;
    }
    public char rysowanie()  {
        return '$';
    }
    @Override
    public void akcja(){
        GetSasiedzi();
        for(int i = 0; i < 8; i++){
            if(sasiad[i] != null && !super.porownajGatunek(sasiad[i])){
                System.out.println(sasiad[i].rysowanie()+" ginie od barszczu");
                sasiad[i].smierc();
            }
        }

        if(wiek > 1)
            super.akcja();

    }
    @Override
    public void kolizja(Organizm atakujacy){
        System.out.println(atakujacy.rysowanie()+" zjazdlo barszcz i umarlo");
        atakujacy.smierc();
        smierc();
    }
    @Override
    public boolean porownajGatunek(Organizm drugi) {
        if (drugi instanceof BarszczSosnowskiego)
            return true;
        else
            return false;
    }
    @Override
    public Organizm stworzNowy(int nowyY, int nowyX) {
        return new BarszczSosnowskiego(nowyY, nowyX);
    }
    private void GetSasiedzi(){
        int x =0;
        for(int dy = -1; dy <= 1; dy++){
            for(int dx = -1; dx <= 1; dx++) {
                if(dy == 0 && dx ==0)
                    continue;
                sasiad[x] = swiat.pobierzWspolrzedne(y + dy, x + dx);
                x++;
            }
        }
    }
}
