package modele;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;

public class Jeu extends Observable {

    private Case[][] tabCases;
    HashMap<Case, Point> hm = new HashMap<>();
    private Score score;

    private Jeu jeu = this;

    private static Random rnd = new Random(4);
    private static Random rand = new Random();

    private boolean deplacementFait;
    private boolean jeuFini = false;


    public Jeu(int size) {
        tabCases = new Case[size][size];
        score = new Score(this);
        init();
    }

    /*-----------------------Fonctions utiles---------------------*/

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
                                tabCases[i][j] = new Case(2, jeu);
                                break;
                            case 2:
                                tabCases[i][j] = new Case(4, jeu);
                                break;
                        }
                    }
                }
            }


        }.start();

        setChanged();
        notifyObservers();


    }

    public int getSize() {
        return tabCases.length;
    }

    public Case getCase(int i, int j) {
        return tabCases[i][j];
    }

    public void setDeplacementFait(boolean fait){
        deplacementFait = fait;
    }

    public void supprimeCase(Point p){
        tabCases[p.x][p.y] = null;
    }

    public void setValCase(Case c, int val){
        tabCases[hm.get(c).x][hm.get(c).y].setValeur(val);
    }

    public void setPosCase(Case c, Point p){
        c.setCoordonnee(p);
        tabCases[p.x][p.y] = c;
    }

    public int getX(int ind){
        return (int)ind/getSize();
    }

    public int getY(int ind){
        return (int)ind%getSize();
    }

    public boolean caseIsNotNull(Case c){
        return hm.containsKey(c);
    }

    public boolean CaseNonBloque(Case c, Direction dir){
        if(this.jeu.estAuBord(c, dir)){
            return false;
        }
        else return voisinIsNull(c, dir);
    }

    public Point getCoordVoisin(Case c, Direction dir){
        Point p = hm.get(c);
        Point voi = null;
        if(dir == Direction.gauche)
            voi = new Point(p.x, p.y-1);
        if(dir == Direction.droite)
            voi = new Point(p.x, p.y+1);
        if(dir == Direction.haut)
            voi = new Point(p.x-1, p.y);
        if(dir == Direction.bas)
            voi = new Point(p.x+1, p.y);
        return voi;
    }

    public Case getvoisin(Case c, Direction dir){
        return tabCases[getCoordVoisin(c, dir).x][getCoordVoisin(c, dir).y];
    }

    public boolean voisinIsNull(Case c, Direction dir) {
        if(!estAuBord(c, dir))
            return !hm.containsKey(getvoisin(c, dir));
        return true;
    }

    public boolean estAuBord(Case c, Direction dir){
        if(dir == Direction.gauche)
            return hm.get(c).y ==0;
        //return c.getCoordonee().x==0;
        if(dir == Direction.droite)
            return hm.get(c).y==getSize()-1;
        //return c.getCoordonee().x==getSize()-1;
        if(dir == Direction.haut)
            return hm.get(c).x==0;
            //return c.getCoordonee().y==0;
        else
            return hm.get(c).x==getSize()-1;
        //return c.getCoordonee().y==getSize()-1;
    }

    /*----------------------------Actions------------------------*/

    public void init() {
        new Thread() {
            public void run() {
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
                    Point p = new Point(getX(ind), getY(ind));
                    if(rn == 0) {
                        tabCases[p.x][p.y] = new Case(4, jeu);//, new Point(getX(ind), getY(ind)), this);
                    }
                    else{
                        tabCases[p.x][p.y] = new Case(2, jeu);//, new Point(getX(ind), getY(ind)), this);
                    }
                    hm.put(tabCases[p.x][p.y], new Point(p.x, p.y));
                }
                /*
                for (Map.Entry mapentry : hm.entrySet()) {
                    System.out.println("cle: "+mapentry.getKey()
                            + " | valeur: " + mapentry.getValue());
                }
                 */
                score.setScoreCourant(0);
                score.setCellCourant(0);

                //score.reinitScore();
                System.out.println(score);
                score.loadFile();
            }
        }.start();

        setChanged();
        notifyObservers();
    }

    public void restart() {
        new Thread() {
            public void run() {
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
                    Point p = new Point(getX(ind), getY(ind));
                    if(rn == 0) {
                        tabCases[p.x][p.y] = new Case(4, jeu);//, new Point(getX(ind), getY(ind)), this);
                    }
                    else{
                        tabCases[p.x][p.y] = new Case(2, jeu);//, new Point(getX(ind), getY(ind)), this);
                    }
                    hm.put(tabCases[p.x][p.y], new Point(p.x, p.y));
                }
                /*
                for (Map.Entry mapentry : hm.entrySet()) {
                    System.out.println("cle: "+mapentry.getKey()
                            + " | valeur: " + mapentry.getValue());
                }
                 */
                score.setScoreCourant(0);
                score.setCellCourant(0);

            }
        }.start();

        setChanged();
        notifyObservers();
    }

    public boolean resteCaseVide(){
        for (int i=0; i<=getSize()-1;i++){
            for (int j=0; j<=getSize()-1; j++){
                if(tabCases[i][j] == null){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean mouvementPossible(){
        for(int i =0; i<getSize(); i++){
            for(int j =0; j<getSize(); j++){
                Case c = tabCases[i][j];
                if(!estAuBord(c, Direction.gauche))
                    if(c.getValeur() == getvoisin(c, Direction.gauche).getValeur())
                        return true;
                if(!estAuBord(c, Direction.droite))
                    if(c.getValeur() == getvoisin(c, Direction.droite).getValeur())
                        return true;
                if(!estAuBord(c, Direction.haut))
                    if(c.getValeur() == getvoisin(c, Direction.haut).getValeur())
                        return true;
                if(!estAuBord(c, Direction.bas))
                    if(c.getValeur() == getvoisin(c, Direction.bas).getValeur())
                        return true;
            }
        }
        return false;
    }

    public boolean grillePleine(){
        return !resteCaseVide();
    }

    public boolean isGameOver(){
        return (grillePleine() && !mouvementPossible());
    }

    public void finDePartie(){
        if(isGameOver()){
            System.out.println("Perdu");
            jeuFini = true;
            //System.exit(0);
        }
    }

    public void move(Direction dir){
        new Thread() {
            public void run() {

                initFusion();
                deplacement(dir);
                ajoutCoup();

                majScore();
                score.savefile();
                afficher();

                finDePartie();

                setChanged();
                notifyObservers();
            }
        }.start();

        setChanged();
        notifyObservers();
    }

    private void afficher()  {


        System.out.printf("\033[H\033[J"); // permet d'effacer la console (ne fonctionne pas toujours depuis la console de l'IDE)

        for (int i = 0; i < jeu.getSize(); i++) {
            for (int j = 0; j < jeu.getSize(); j++) {
                Case c = jeu.getCase(i, j);
                if (c != null) {
                    System.out.format("%5.5s", c.getValeur());
                } else {
                    System.out.format("%5.5s", "-");
                }

            }
            System.out.println();
        }
        System.out.println("");

    }

    public void ajoutCoup(){



        int ind; //position aléatoire
        do{
            ind = rand.nextInt(getSize()* getSize());
        }while(tabCases[getX(ind)][getY(ind)] != null);

        int rn; //aléatoire 2 ou 4
        rn = rand.nextInt(2);
        if(rn == 0)
            tabCases[getX(ind)][getY(ind)] = new Case(4, jeu);//, new Point(getX(ind), getY(ind)));
        else
            tabCases[getX(ind)][getY(ind)] = new Case(2, jeu);//, new Point(getX(ind), getY(ind)));
        hm.put(tabCases[getX(ind)][getY(ind)], new Point(getX(ind), getY(ind)));


    }

    public void initFusion(){
        for(int i=0; i<getSize(); i++){
            for(int j=0; j<getSize(); j++){
                if(caseIsNotNull(tabCases[i][j]))
                    tabCases[i][j].setFusion(false);
            }
        }
    }

    public void deplacement(Direction dir){
        boolean deplace = true;
        if(dir == Direction.gauche){
            for(int j=0; j<getSize(); j++){
                for(int i=0; i<getSize(); i++){
                    if (tabCases[i][j] == null)
                        deplace = false;
                    appDeplacement(tabCases[i][j], dir);
                    if (deplace && tabCases[i][j] == null) {
                        deplacementFait = true;
                    }
                    deplace = true;
                }
            }
        }
        if(dir == Direction.droite){
            for(int j=getSize()-1; j>=0; j--){
                //  maintenant vertical pour haut ou bas
                for(int i=0; i<=getSize()-1; i++){
                    // correspond au parcour "vertical de la map" on pars de 3 et on arrives a 0
                    if (tabCases[i][j] == null)
                        deplace = false;
                    appDeplacement(tabCases[i][j], dir);
                    if (deplace && tabCases[i][j] == null) {
                        deplacementFait = true;
                    }
                    deplace = true;
                }
            }
        }
        if(dir == Direction.haut){
            for(int i=0; i<getSize(); i++){
                for(int j=0; j<getSize(); j++){
                    if (tabCases[i][j] == null)
                        deplace = false;
                    appDeplacement(tabCases[i][j], dir);
                    if (deplace && tabCases[i][j] == null) {
                        deplacementFait = true;
                    }
                    deplace = true;
                }
            }
        }

        if(dir == Direction.bas){
            for (int i=getSize()-1;i>=0;i--){
                for(int j=0;j<=getSize()-1;j++){
                    if (tabCases[i][j] == null)
                        deplace = false;
                    appDeplacement(tabCases[i][j], dir);
                    if (deplace && tabCases[i][j] == null) {
                        deplacementFait = true;
                    }
                    deplace = true;
                }
            }
        }
    }

    public void appDeplacement(Case c, Direction dir){
        if(caseIsNotNull(c)){
            c.deplacement(dir);
        }

    }

    /*---------------Score-------------------------*/

    public void majScore(){
        for(int i=0; i<getSize(); i++){
            for(int j=0; j<getSize(); j++){
                if(caseIsNotNull(tabCases[i][j])){
                    int tmp = score.getCurrentScore()+tabCases[i][j].getValeur();
                    score.setScoreCourant(tmp);
                    score.setCellCourant(Math.max(tabCases[i][j].getValeur(), score.getCurrentCell()));
                }
            }
        }
        score.setMax();
        System.out.println(score);
    }

    public Score getScore(){
        return score;
    }


}
