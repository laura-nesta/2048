package modele;

import java.util.Observable;
import java.util.Random;

public class Jeu extends Observable {

    private Case[][] tabCases;
    private static Random rnd = new Random(4);

    public Jeu(int size) {
        tabCases = new Case[size][size];
        init();
        //rnd();
    }

    public int getSize() {
        return tabCases.length;
    }

    public Case getCase(int i, int j) {
        return tabCases[i][j];
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
                                tabCases[i][j] = new Case(2);
                                break;
                            case 2:
                                tabCases[i][j] = new Case(4);
                                break;
                        }
                    }
                }
            }

        }.start();

        setChanged();
        notifyObservers();
    }

    public int getX(int ind){
        return (int)ind/ tabCases.length;
    }

    public int getY(int ind){
        return (int)ind%tabCases.length;
    }

    public void init(){
        new Thread(){
            public void run(){
                //on vide la grille
                for(int i=0; i<tabCases.length; i++){
                    for(int j=0; j<tabCases.length; j++){
                        tabCases[i][j] = null;
                    }
                }

                //on remplie 2 cases aléatoires de la grille
                int rp1; //position aléatoire1
                int rp2; //position aléatoire2
                Random rand = new Random();
                do{
                    rp1 = rand.nextInt(tabCases.length* tabCases.length);
                    rp2 = rand.nextInt(tabCases.length* tabCases.length);
                }while(rp1 == rp2);

                int rn; //aléatoire 2 ou 4 (la probabilité d'apparition de 2 est plus élevé)
                for(int i = 0; i<2; i++){
                    Random rnd = new Random();
                    rn = rnd.nextInt(5);
                    int ind;
                    if(i==0)
                        ind = rp1;
                    else
                        ind = rp2;
                    if(rn == 0)
                        tabCases[getX(ind)][getY(ind)] = new Case(4);
                    else
                        tabCases[getX(ind)][getY(ind)] = new Case(2);
                }
            }

        }.start();

        setChanged();
        notifyObservers();
    }

    public void deplacement(Direction dir){

    }

    public void fusion(Direction dir){

    }

    public void ajoutCoup(){
        new Thread(){
            public void run(){
                int ind; //position aléatoire
                Random rand = new Random();
                do{
                    ind = rand.nextInt(tabCases.length* tabCases.length);
                }while(tabCases[getX(ind)][getY(ind)] != null);

                int rn; //aléatoire 2 ou 4
                Random rnd = new Random();
                rn = rnd.nextInt(2);
                if(rn == 0)
                    tabCases[getX(ind)][getY(ind)] = new Case(4);
                else
                    tabCases[getX(ind)][getY(ind)] = new Case(2);
            }
        }.start();

        setChanged();
        notifyObservers();
    }

    public void moove(Direction dir){
        deplacement(dir);
        fusion(dir);
        deplacement(dir);
        ajoutCoup();
    }

}
