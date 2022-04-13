package modele;

import java.awt.*;
import java.util.Observable;
import java.util.Random;

public class Jeu extends Observable {

    private Case[][] tabCases;
    private static Random rnd = new Random(4);
    private static Random rand = new Random();

    public Jeu(int size) {
        tabCases = new Case[size][size];
        //rnd();
        init();
    }

    public int getSize() {
        return tabCases.length;
    }

    public Case getCase(int i, int j) {
        return tabCases[i][j];
    }

    public int getX(int ind){
        return (int)ind/ tabCases.length;
    }

    public int getY(int ind){
        return (int)ind%tabCases.length;
    }


    public void rnd() {
        new Thread() { // permet de libérer le processus graphique ou de la console
            public void run() {
                int r;

                for (int i = 0; i < tabCases.length; i++) {
                    for (int j = 0; j < tabCases.length; j++) {
                        r = rnd.nextInt(3);

                        switch (r) {
                            case 0:
                                tabCases[i][j] = null;
                                break;
                            case 1:
                                tabCases[i][j] = new Case(2, new Point(i,j));
                                break;
                            case 2:
                                tabCases[i][j] = new Case(4, new Point(i,j));
                                break;
                        }
                    }
                }
            }

        }.start();

        setChanged();
        notifyObservers();
    }

    public void init() {
        new Thread() { // permet de libérer le processus graphique ou de la console
            public void run() {
                //on vide la grille
                for(int i=0; i<getSize(); i++){
                    for(int j=0; j<getSize(); j++){
                        tabCases[i][j] = null;
                    }
                }

                //on remplie 2 cases aléatoires de la grille
                int rp1; //position aléatoire1
                int rp2; //position aléatoire2
                do{
                    rp1 = rand.nextInt(getSize()* getSize());
                    rp2 = rand.nextInt(getSize()* getSize());
                }while(rp1 == rp2);

                int rn; //aléatoire 2 ou 4 (la probabilité d'apparition de 2 est plus élevé)
                for(int i = 0; i<2; i++){
                    rn = rand.nextInt(5);
                    int ind;
                    if(i==0)
                        ind = rp1;
                    else
                        ind = rp2;
                    if(rn == 0)
                        tabCases[getX(ind)][getY(ind)] = new Case(4, new Point(getX(ind), getY(ind)));
                    else
                        tabCases[getX(ind)][getY(ind)] = new Case(2, new Point(getX(ind), getY(ind)));
                }
            }

        }.start();

        setChanged();
        notifyObservers();
    }

    public void ajoutCoup(){
        int ind; //position aléatoire
        do{
            ind = rand.nextInt(getSize()* getSize());
        }while(tabCases[getX(ind)][getY(ind)] != null);

        int rn; //aléatoire 2 ou 4
        rn = rand.nextInt(2);
        if(rn == 0)
            tabCases[getX(ind)][getY(ind)] = new Case(4, new Point(getX(ind), getY(ind)));
        else
            tabCases[getX(ind)][getY(ind)] = new Case(2, new Point(getX(ind), getY(ind)));
        for(int i=0; i<getSize(); i++){
            for(int j=0; j<getSize(); j++){
                System.out.println(tabCases[i][j]);
            }
        }

    }

    public void fusion(Direction dir){

    }

    public void move(Direction dir){
        new Thread(){
            public void run(){
                //deplacement(dir);
                //fusion(dir);
                //deplacement(dir);
                ajoutCoup();
            }
        }.start();

        setChanged();
        notifyObservers();
    }

}
