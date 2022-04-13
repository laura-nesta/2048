package modele;

import java.awt.*;

public class Case{
    private int valeur;
    private Jeu jeu;

    public Case(int _valeur, Jeu _jeu) {
        valeur = _valeur;
        jeu = _jeu;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int val){
        valeur = val;
    }

    public Point getCoordonee(){
        return this.jeu.hm.get(this);
    }

    public void setCoordonnee(Point p){
        this.jeu.hm.put(this, p);
    }

    @Override
    public String toString(){
        return ""+valeur;
    }

    public void VoisinIsNull(Direction dir){

    }
/*
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

    public Point GetCoordVoisoin(Direction dir, int size){
        Point voi;
        if(dir == Direction.gauche){
            if(!estAuBord(dir, size)){
                voi = new Point(coordonnee.x-1, coordonnee.y);
            }
        }
        if(dir == Direction.droite){
            if(!estAuBord(dir, size)){
                voi = new Point(coordonnee.x+1, coordonnee.y);
            }
        }
        if(dir == Direction.haut){
            if(!estAuBord(dir, size)){
                voi = new Point(coordonnee.x, coordonnee.y-1);
            }
        }
        else{
            if(!estAuBord(dir, size)){
                voi = new Point(coordonnee.x, coordonnee.y+1);
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
    */

}
