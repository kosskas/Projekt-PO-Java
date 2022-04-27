package pl.edu.pg.eti.ksg.po.Zwierze;

import pl.edu.pg.eti.ksg.po.silnik.Organizm;
import pl.edu.pg.eti.ksg.po.silnik.PojedynczyOrganizm;
import pl.edu.pg.eti.ksg.po.silnik.Zwierze;

public class Owca extends Zwierze {
    public Owca(int posY, int posX){
        super(4, 4);
        x = posX;
        y = posY;
    }
    public Owca(int posY, int posX, int wiek){
        this(posY, posX);
        this.wiek = wiek;
    }

    public char rysowanie(){
        return 'O';
    }

    @Override
    public boolean porownajGatunek(Organizm drugi){
        if(drugi instanceof Owca)
            return true;
        else
            return false;
    }
    @Override
    public Organizm stworzNowy(int Y, int X){
        return new Owca(Y, X);
    }

}
