package pl.edu.pg.eti.ksg.po;

import pl.edu.pg.eti.ksg.po.Rosliny.*;
import pl.edu.pg.eti.ksg.po.Zwierzeta.*;
import pl.edu.pg.eti.ksg.po.silnik.Organizm;
import pl.edu.pg.eti.ksg.po.silnik.Swiat;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Organizm> L = new LinkedList<>();
        dodajOrganizmy(L);
        Swiat S = new Swiat(20, 20, L);
        S.symuluj(17);
    }
    public static void dodajOrganizmy(List<Organizm> L){
        L.add(new Owca(0, 0));
        L.add(new Owca(1, 0));
        L.add(new Owca(0, 1));
        L.add(new BarszczSosnowskiego(4, 0));
        L.add(new BarszczSosnowskiego(0, 4));
        L.add(new BarszczSosnowskiego(3, 4));
        L.add(new Lis(5, 4));
        L.add(new Lis(4, 3));
        L.add(new Zolw(7, 7));
        L.add(new Zolw(10, 9));
        L.add(new Owca(7, 8));
        L.add(new Trawa(7, 2));
        L.add(new Mlecz(1, 10));
        L.add(new Guarana(2, 7));
        L.add(new Antylopa(8, 3));
        L.add(new Antylopa(6, 7));
        L.add(new Antylopa(8, 7));
        L.add(new Antylopa(2, 2));
        L.add(new WilczeJagody(5, 7));
        L.add(new Wilk(3, 6));
        L.add(new Wilk(3, 5));
    }
}
