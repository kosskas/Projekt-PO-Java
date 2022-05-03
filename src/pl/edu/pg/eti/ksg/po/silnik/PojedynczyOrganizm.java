package pl.edu.pg.eti.ksg.po.silnik;

public abstract class PojedynczyOrganizm implements Organizm{
    protected int sila;
    protected int inicjatywa;
    protected int wiek;
    protected Swiat swiat;
    protected int x;
    protected int y;
    protected boolean zyje;
    protected boolean wykonalRuch = false;

    public boolean CzyOdbilAtak(Organizm atakujacy){
        if(sila < atakujacy.GetSila())
            return false;
        else
            return true;
    }

    public void rozmnazanie(Organizm drugi){
        if (wiek > Swiat.WIEK_ROZMNAZANIA && drugi.GetWiek() > Swiat.WIEK_ROZMNAZANIA) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    Organizm dziecko = swiat.pobierzWspolrzedne(y + dy, x + dx);
                    if (dziecko == null && swiat.sprawdzPoprawnoscWspolrzednych(y + dy, x + dx)) {
                        dziecko = stworzNowy(y + dy, x + dx);
                        if (dziecko != null) {
                            swiat.dodajOrganizm(dziecko);
                        }
                        break;
                    }
                }
            }
        }
    }
    public void smierc(){
        zyje = false;
    }
    public void kolizja(Organizm atakujacy){
        if (this.CzyOdbilAtak(atakujacy)) {
            System.out.println(rysowanie()+" zabil "+atakujacy.rysowanie()+'\n');
            atakujacy.smierc();
        }
	else {
            System.out.println(atakujacy.rysowanie()+" zabil "+rysowanie()+'\n');
            this.smierc();
        }
    }
    public void nowaTura(){
        wiek++;
        wykonalRuch = false;
    }
    public void SetSwiat(Swiat S){
        swiat = S;
    }
    public void SetSila(int wartosc){
        sila = wartosc;
    }
    public boolean CzyZyje(){
        return zyje;
    }
    public int GetX(){
        return x;
    }
    public int GetY(){
        return y;
    }
    public int GetSila(){
        return sila;
    }
    public int GetInicjatywa(){
        return inicjatywa;
    }
    public int GetWiek(){
        return wiek;
    }
    @Override
    public int porownajOrganizmy(Organizm drugi){
        if(inicjatywa > drugi.GetInicjatywa())
            return 1;
        if(inicjatywa == drugi.GetInicjatywa()){
            if(wiek > drugi.GetWiek())
                return 1;
            else
                return 0;
        }
        return 0;
    }
}
