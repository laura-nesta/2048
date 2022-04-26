package modele;

import java.awt.*;

public class Case {
    private int valeur;
    private Jeu jeu;
    private boolean fusion;
    private boolean deplacement_fait;


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

    public boolean getDeplacementFait(){
        return deplacement_fait;
    }

    public void deplacement(Direction dir){
        deplacement_fait = false;
        while(this.jeu.CaseNonBloque(this, dir)){
            this.jeu.supprimeCase(this.getCoordonee());
            this.jeu.setPosCase(this, this.jeu.getCoordVoisin(this, dir));
            this.jeu.setDeplacementFait(true);
        }
        if(!this.jeu.estAuBord(this, dir) && !this.jeu.voisinIsNull(this, dir) && !fusion && !(this.jeu.getvoisin(this,dir).fusion) ){
            if(this.jeu.getvoisin(this,dir).valeur == valeur){
                this.jeu.setValCase(this.jeu.getvoisin(this, dir), valeur*2);
                this.jeu.getvoisin(this, dir).setFusion(true);
                this.jeu.supprimeCase(this.jeu.hm.get(this));
                this.jeu.setDeplacementFait(true);
                if (valeur == 2048){
                    System.out.println("Gagne, 2048 a ete atteint");
                    System.exit(0);
                }
            }
        }
    }

}
