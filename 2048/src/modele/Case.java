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
*/
    public void deplacement(Direction dir, int size){
        while(this.jeu.voisinIsNull(this, dir) && this.jeu.voisinExiste(this, dir)){
            this.jeu.hm.replace(this, this.jeu.getCoordVoisin(this, dir));
            this.jeu.setPosCase(this, this.jeu.getCoordVoisin(this, dir));
        }
        if(!(this.jeu.voisinIsNull(this, dir)) && this.jeu.voisinExiste(this, dir)){
            if(this.jeu.getCase(this.jeu.getCoordVoisin(this, dir).x,this.jeu.getCoordVoisin(this, dir).y).valeur == valeur){
                 this.jeu.setValCase(this.jeu.getvoisin(this, dir), valeur*2);
                 this.jeu.supprimeCase(this);
            }
        }
    }
}
