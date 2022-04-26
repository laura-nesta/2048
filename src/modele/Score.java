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

    private static final String fileName = "2048.properties";

    private static final String highCell  = "Meilleure cellule";
    private static final String highScore = "Meilleur score";

    private Jeu jeu;

    public Score(Jeu _jeu){
        jeu = _jeu;
    }

    public void chargeScore() {
        Properties properties = new Properties();

        InputStream is = null;
        File file = new File(fileName);
        try {
            is = new FileInputStream(file);
            properties.load(is);
            jeu.setMaxScore(Integer.parseInt(
                    properties.getProperty(highScore)));
            jeu.setmaxCell(Integer.parseInt(
                    properties.getProperty(highCell)));
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveProperties() {
        Properties properties = new Properties();
        properties.setProperty(highScore,
                Integer.toString(jeu.getMaxScore()));
        properties.setProperty(highCell,
                Integer.toString(jeu.getMaxCell()));

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

}
