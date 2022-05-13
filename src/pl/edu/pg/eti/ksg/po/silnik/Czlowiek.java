package pl.edu.pg.eti.ksg.po.silnik;

import java.awt.event.KeyEvent;

public class Czlowiek extends Zwierze{
    private int czasTrwania;
    private boolean czyAktywnaUmj;
    private int czasOczewiw;
    private boolean czyMoze ;
    public Czlowiek(int posY, int posX, Swiat wsk) {
        super(5, 4);
        x = posX;
        y = posY;
        prevX = x;
        prevY = y;
        czasTrwania = 0;
        czyAktywnaUmj = false;
        czasOczewiw = 0;
        czyMoze = true;
        swiat = wsk;
    }
    public Czlowiek(int posY, int posX, int wiek, Swiat wsk) {
        this(posY, posX, wsk);
        this.wiek = wiek;
    }

    @Override
    public void nowaPozycja(){
        if(swiat.GetCzlowiekDX() == KeyEvent.VK_U && czyMoze) {
            System.out.println("Czlowiek pije magiczny eliksir");
            wypijMagicznyEliksir();
            return;
        }
        int dy = swiat.GetCzlowiekDY(), dx= swiat.GetCzlowiekDX();
        if (swiat.sprawdzPoprawnoscWspolrzednych(x + dx, y + dy)) {
            nextX = x + dx;
            nextY = y + dy;
            prevX = x;
            prevY = y;
        }
    }

    public void wypijMagicznyEliksir() {
        sila = 10;
        czyAktywnaUmj = true;
        czasTrwania = 5;
        czyMoze = false;
        czasOczewiw = 5;
    }
    @Override
    public void nowaTura(){
        if (czyAktywnaUmj) {
            czasTrwania--;
            sila--;
            if (czasTrwania == 0){
               System.out.println("Zdolnosc przestala dzialac");
                czyAktywnaUmj = false;
            }
        }
        else if (!czyMoze) {
            czasOczewiw--;
            if (czasOczewiw == 0) {
                System.out.println("Zdolnosc specjalna gotowa do uzycia");
                czyMoze = true;
            }
        }
        super.nowaTura();
    }

    public char rysowanie()  {
        return 'C';
    }
    @Override
    public boolean porownajGatunek(Organizm drugi) {
        if (drugi instanceof Czlowiek)
            return true;
        else
            return false;
    }
    @Override
    public Organizm stworzNowy(int nowyY, int nowyX) {
        return null;
    }
}
