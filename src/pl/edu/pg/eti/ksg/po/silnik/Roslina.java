package pl.edu.pg.eti.ksg.po.silnik;

public abstract class Roslina extends PojedynczyOrganizm{
    protected int szansaSiewu;
    public Roslina(int s){
        sila = s;
        inicjatywa = 0;
        wiek = 0;
        zyje = true;
    }
    public void akcja(){
        if(Swiat.rand.nextInt() % 100 <=szansaSiewu)
            rozmnazanie(this);
    }
    @Override
    public boolean porownajGatunek(Organizm drugi){
        if(drugi instanceof Roslina)
            return true;
        return false;
    }
}
