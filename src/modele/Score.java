package modele;

public class Score {

    private Jeu jeu;

    private int MaxScore;
    private int MaxCell;
    private int ScoreCourant;
    private int CellCourant;

    public Score(Jeu _jeu){
        jeu = _jeu;

        MaxCell = 2;
        MaxScore = 0;
        ScoreCourant = 0;
        CellCourant = 0;
    }

    @Override
    public String toString(){
        return "Meilleure cellule: "+ MaxCell
                +"\n Meilleure score: "+ MaxScore
                +"\n Score courrant: "+ ScoreCourant
                +"\n Cellule courante: "+ CellCourant;
    }

    public void setMaxScore() {
        MaxScore = Math.max(ScoreCourant, MaxScore);
        MaxCell = Math.max(CellCourant, MaxCell);
    }

    public void setScoreCourant(int _ScoreCourant) {
        ScoreCourant = _ScoreCourant;
    }

    public void setCellCourant(int _CourrantCell) {
        CellCourant = _CourrantCell;
    }

    public int getMaxScore() {
        return MaxScore;
    }

    public int getMaxCell() {
        return MaxCell;
    }

    public int getCurrentScore() {
        return ScoreCourant;
    }

    public int getCurrentCell() {
        return CellCourant;
    }
}
