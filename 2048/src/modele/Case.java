package modele;

import java.awt.*;

public class Case {
    private int valeur;
    private Point coordonnee;

    public Case(int _valeur, Point _coordonnee) {
        valeur = _valeur;
        coordonnee = _coordonnee;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int val){
        valeur = val;
    }

    public Point getCoordonnee(){return coordonnee;}

    public void setCoordonnee(Point p){
        coordonnee = p;
    }

    public void setCoordonne(int x, int y){
        setCoordonnee(new Point(x,y));
    }
    @Override
    public String toString(){
        return ""+valeur;
    }
/*
    public Point CoordVoisoin(Direction dir, int size){
        if(dir == Direction.gauche){
            if(coordonnee.x%size == 0){
                Point voi = null;
            }
            else{
                Point voi = new Point(coordonnee.x-1, coordonnee.y);
            }
        }
        return voi;
    }*/
}
