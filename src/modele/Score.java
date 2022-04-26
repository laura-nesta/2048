package modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Score {

    private Jeu jeu;

    private static final String fileName =
            "game2048.properties";

    private static final String highCell  = "highCell";
    private static final String highScore = "highScore";

    private int MaxScore;
    private int MaxCell;
    private int ScoreCourant;
    private int CellCourant;

    public Score(Jeu _jeu){
        jeu = _jeu;

        MaxCell = 0;
        MaxScore = 0;
        ScoreCourant = 0;
        CellCourant = 0;
    }

    public void loadFile() {
        Properties properties = new Properties();

        InputStream is = null;
        File file = new File(fileName);
        try {
            is = new FileInputStream(file);
            properties.load(is);
            setMaxScore(Integer.parseInt(
                    properties.getProperty(highScore)));
            setMaxCell(Integer.parseInt(
                    properties.getProperty(highCell)));
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savefile() {
        Properties properties = new Properties();
        properties.setProperty(highScore,
                Integer.toString(getMaxScore()));
        properties.setProperty(highCell,
                Integer.toString(getMaxCell()));

        OutputStream os = null;
        File file = new File(fileName);

        try {
            os = new FileOutputStream(file);
            properties.store(os, "2048 High Score");
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void reinitScore(){
        Properties properties = new Properties();
        properties.setProperty(highScore,
                Integer.toString(0));
        properties.setProperty(highCell,
                Integer.toString(0));

        OutputStream os = null;
        File file = new File(fileName);

        try {
            os = new FileOutputStream(file);
            properties.store(os, "2048 High Score");
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "Meilleure cellule: "+ MaxCell
                +"\n Meilleure score: "+ MaxScore
                +"\n Score courrant: "+ ScoreCourant
                +"\n Cellule courante: "+ CellCourant;
    }

    public void setMax() {
        MaxScore = Math.max(ScoreCourant, MaxScore);
        MaxCell = Math.max(CellCourant, MaxCell);
    }

    public void setMaxScore(int ms){
        MaxScore = ms;
    }

    public void setMaxCell(int mc){
        MaxCell = mc;
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
