package vue_controleur;

import modele.Case;
import modele.Direction;
import modele.Jeu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Swing2048 extends JFrame implements Observer {
    private static final int PIXEL_PER_SQUARE = 60;
    // tableau de cases : i, j -> case graphique
    private JLabel[][] tabC;
    private Jeu jeu;

    private JFrame frame;


    public Swing2048(Jeu _jeu) {
        jeu = _jeu;

        frame = new JFrame();

        frame.setVisible(true);
        frame.setTitle("2048");
        frame.setLocation(500, 200);

        JLabel label = new JLabel("Je suis un JLabel", JLabel.CENTER);
        frame.add(label);

        JPanel panel = new JPanel();

        JButton btn1 = new JButton("Start");
        panel.add(btn1);
        btn1.addActionListener(e ->
        {
            setVisible(true);
        });

        JButton btn2 = new JButton("End");
        panel.add(btn2);
        btn2.addActionListener(e ->
        {
            setVisible(false);
        });

        /*JLabel label1 = new JLabel("Combien de cases vous voulez pour jouer", JLabel.CENTER);
        frame.add(label1);
        JButton btn3 = new JButton("Valider");
        panel.add(btn3);
        btn2.addActionListener(e ->
        {
            setVisible(true);
        }); */


        // Ajouter label et panel au frame
        frame.setLayout(new GridLayout(2, 1));
        frame.add(label);
        frame.add(panel);

        frame.pack();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(jeu.getSize() * PIXEL_PER_SQUARE, jeu.getSize() * PIXEL_PER_SQUARE);
        tabC = new JLabel[jeu.getSize()][jeu.getSize()];

        JPanel contentPane = new JPanel(new GridLayout(jeu.getSize(), jeu.getSize()));

        for (int i = 0; i < jeu.getSize(); i++) {
            for (int j = 0; j < jeu.getSize(); j++) {
                Border border = BorderFactory.createLineBorder(Color.darkGray, 5);
                tabC[i][j] = new JLabel();
                tabC[i][j].setBorder(border);
                tabC[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                tabC[i][j].setForeground(Color.red);
                //tabC[i][j].setForeground(Color.RGBtoHSB(int r=116,g=109, b=102));
                contentPane.add(tabC[i][j]);
            }
        }

        setContentPane(contentPane);
        ajouterEcouteurClavier();
        rafraichir(); // je ne sais pas, il faudrait pouvoir faire comme en prog concu et utiliser les notify
    //Pour le coup je seche sur le probleme de rafraichissement, appuyer sur r et puis voila xD
        
    }


    /**
     * Correspond à la fonctionnalité de Vue : affiche les données du modèle
     */
    private void rafraichir()  {

        SwingUtilities.invokeLater(new Runnable() { // demande au processus graphique de réaliser le traitement
            @Override
            public void run() {
                for (int i = 0; i < jeu.getSize(); i++) {
                    for (int j = 0; j < jeu.getSize(); j++) {
                        Case c = jeu.getCase(i, j);

                        if (c == null) {

                            tabC[i][j].setText("");

                        } else {
                            tabC[i][j].setText(c.getValeur() + "");
                        }
                    }
                }
            }
        });
    }

    /**
     * Correspond à la fonctionnalité de Contrôleur : écoute les évènements, et déclenche des traitements sur le modèle
     */



    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT -> jeu.move(Direction.gauche);
                    case KeyEvent.VK_RIGHT -> jeu.move(Direction.droite);
                    case KeyEvent.VK_DOWN -> jeu.move(Direction.bas);
                    case KeyEvent.VK_UP -> jeu.move(Direction.haut);
                    case KeyEvent.VK_R -> rafraichir();// je pense que c'est mieu de le garder pour l'instant
                }
            }
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        rafraichir();
    }
}