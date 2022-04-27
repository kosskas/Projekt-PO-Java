package pl.edu.pg.eti.ksg.po;

import pl.edu.pg.eti.ksg.po.Zwierze.Lis;
import pl.edu.pg.eti.ksg.po.Zwierze.Owca;
import pl.edu.pg.eti.ksg.po.silnik.Organizm;
import pl.edu.pg.eti.ksg.po.silnik.Swiat;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Organizm> L = new LinkedList<>();
        L.add(new Owca(1, 1));
        L.add(new Lis(0, 0));
        Swiat S = new Swiat(5, 5, L);
        S.symuluj(17);
    }
}
