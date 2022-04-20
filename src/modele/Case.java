package modele;

import java.awt.*;

public class Case {
    private int valeur;
    private Jeu jeu;
    private boolean fusion;


    public Case(int _valeur, Jeu _jeu) {
        valeur = _valeur;
        jeu = _jeu;
    }

    @Override
    public String toString(){
        return ""+valeur;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int val){
        valeur = val;
    }

    public boolean getFusion(){
        return fusion;
    }

    public void setFusion(boolean f){
        fusion = f;
    }

    public Point getCoordonee(){
        return this.jeu.hm.get(this);
    }

    public void setCoordonnee(Point p){
        this.jeu.hm.replace(this, p);
    }

    public void deplacement(Direction dir){
        System.out.println("ok3");
        //while(!(this.jeu.estAuBord(this, dir) || this.jeu.caseIsNotNull(this.jeu.getvoisin(this, dir)))){
        while(this.jeu.CaseNonBloque(this, dir)){
                System.out.println("boucle");
                this.jeu.supprimeCase(this.getCoordonee());
                this.jeu.setPosCase(this, this.jeu.getCoordVoisin(this, dir));

                System.out.println("pas au bord");
        }
        System.out.println("ok4");
        if(!this.jeu.estAuBord(this, dir) && !this.jeu.voisinIsNull(this, dir) && !fusion){
            if(this.jeu.getvoisin(this,dir).valeur == valeur){
                this.jeu.setValCase(this.jeu.getvoisin(this, dir), valeur*2);
                this.jeu.supprimeCase(this.jeu.hm.get(this));
                fusion = true;
            }
        }
    }

}
