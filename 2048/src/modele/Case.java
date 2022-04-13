package modele;

import java.awt.*;

public class Case{
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

    public int getIndice(int x, int y){

    }

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

    public void VoisinIsNull(Direction dir){

    }

    public boolean estAuBord(Direction dir, int size){
        if(dir == Direction.gauche){
            return coordonnee.x == 0;
        }
        if(dir == Direction.droite){
            return coordonnee.x == size-1;
        }
        if(dir ==Direction.bas){
            return coordonnee.y == size-1;
        }
        else
            return coordonnee.y == 0;
    }

    public boolean voisinIsNull(Direction dir){
        if(dir == Direction.gauche){
            return
        }
        if(dir == Direction.droite){
            return coordonnee.x == size-1;
        }
        if(dir ==Direction.bas){
            return coordonnee.y == size-1;
        }
        else
            return coordonnee.y == 0;
    }

    public Case GetVoisoin(Direction dir, int size){
        Case voi;
        if(dir == Direction.gauche){
            if(!estAuBord(dir, size)){
                voi = new Case(getValeur(new Point(coordonnee.x-1, coordonnee.y), coordonnee.x-1, )
            }
        }
        return voi;
    }

    public void fusion(Direction dir, int size){
        if(dir == Direction.gauche){

        }
        if(dir == Direction.droite){

        }
        if(dir ==Direction.bas){

        }
        else

    }
}
