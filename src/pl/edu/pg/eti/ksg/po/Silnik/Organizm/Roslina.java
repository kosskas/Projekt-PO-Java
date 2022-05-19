package pl.edu.pg.eti.ksg.po.Silnik.Organizm;

import pl.edu.pg.eti.ksg.po.Silnik.Aplikacja.Swiat;

public abstract class Roslina extends PojedynczyOrganizm {
    protected int szansaSiewu;
    public Roslina(int s){
        sila = s;
        inicjatywa = 0;
        wiek = 0;
        zyje = true;
    }
    public void akcja(){
        if(!wykonalRuch) {
            if (Math.abs(Swiat.rand.nextInt()) % 100 <= szansaSiewu)
                rozmnazanie(this);
            wykonalRuch = true;
        }
    }
    @Override
    public boolean porownajGatunek(Organizm drugi){
        if(drugi instanceof Roslina)
            return true;
        return false;
    }
}
