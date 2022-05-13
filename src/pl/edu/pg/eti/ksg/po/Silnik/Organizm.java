package pl.edu.pg.eti.ksg.po.Silnik;

public interface Organizm {
    boolean CzyOdbilAtak(Organizm atakujacy);
    void rozmnazanie(Organizm atakujacy);
    Organizm stworzNowy(int nowyY, int nowyX);
    void akcja();
    void smierc();
    char rysowanie();
    void kolizja(Organizm atakujacy);
    void nowaTura();
    void SetSwiat(Swiat S);
    void SetSila(int wartosc);
    boolean CzyZyje();
    boolean porownajGatunek(Organizm drugi);
    int GetX();
    int GetY();
    int GetSila();
    int GetInicjatywa();
    int GetWiek();
    int porownajOrganizmy(Organizm drugi);
}
