package pl.edu.pg.eti.ksg.po;
import pl.edu.pg.eti.ksg.po.silnik.Swiat;
import pl.edu.pg.eti.ksg.po.silnik.Symulacja;

public class Main {
    public static void main(String[] args) {
        Symulacja S = new Symulacja(20, 20);
        //Symulacja S = new Symulacja(10, 10);
        S.symuluj();
    }
}

