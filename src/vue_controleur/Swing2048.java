package vue_controleur;

import modele.Case;
import modele.Direction;
import modele.Jeu;
import modele.Score;

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

    private Grille grille;

    private AffScore AffScore;
    private Score score;

    private HashMap<Integer, Color> colorMap = new HashMap<>();
    //colorMap.put(2, new Color(Color.black.getRGB()));




    public Swing2048(Jeu _jeu) {
        jeu = _jeu;
        score = new Score(jeu);
        score.chargeScore();
        colorMap.put(2, new Color(238, 228, 218, 255));
        colorMap.put(4, new Color(237, 224, 200, 255));
        colorMap.put(8, new Color(242, 177, 121, 255));
        colorMap.put(16, new Color(243, 149, 94,255));
        colorMap.put(32, new Color(240, 123, 57, 255));
        colorMap.put(64, new Color(242, 177, 121, 255));
        colorMap.put(128, new Color(237, 207, 114, 255));
        colorMap.put(256, new Color(237,200, 97, 255));
        colorMap.put(512, new Color(237, 200,80, 255));
        colorMap.put(1024, new Color(237, 200,60, 255));
        colorMap.put(2048, new Color(237, 200,40, 255));

        creaSwing();
    }

    public void creaSwing(){

        constructSwing();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(jeu.getSize() * PIXEL_PER_SQUARE, jeu.getSize() * PIXEL_PER_SQUARE);
        tabC = new JLabel[jeu.getSize()][jeu.getSize()];




        JPanel contentPane = new JPanel(new GridLayout(jeu.getSize(), jeu.getSize()));

        for (int i = 0; i < jeu.getSize(); i++) {
            for (int j = 0; j < jeu.getSize(); j++) {
                Border border = BorderFactory.createLineBorder(Color.darkGray, 3);
                tabC[i][j] = new JLabel();
                tabC[i][j].setBorder(border);
                tabC[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                tabC[i][j].setForeground(Color.gray);
                if ((jeu.getCase(i, j)) == null)
                    tabC[i][j].setBackground(Color.gray);
                else
                    tabC[i][j].setBackground(colorMap.get((jeu.getCase(i, j)).getValeur()));
                tabC[i][j].setOpaque(true);
                contentPane.add(tabC[i][j]);
            }
        }
        setContentPane(contentPane);
        ajouterEcouteurClavier();
        rafraichir();
    }

    public void constructSwing(){
        frame = new JFrame();
        AffScore = new AffScore(jeu);

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

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        //mainPanel.add(gridPanel);
        mainPanel.add(createSidePanel());

        frame.add(mainPanel);
        //frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);



        // Ajouter label et panel au frame
        frame.setLayout(new GridLayout(2, 1));
        frame.add(label);
        frame.add(panel);

        frame.pack();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        createSidePanel();
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel,
                BoxLayout.PAGE_AXIS));
        sidePanel.add(vue_controleur.AffScore.getPanel());
        sidePanel.add(Box.createVerticalStrut(30));
        sidePanel.add(vue_controleur.AffScore.getPanel());
        return sidePanel;
    }

    /*
    public void updateScorePanel() {
        score.updatePartControl();
    }*/



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
                            tabC[i][j].setBackground(Color.gray);

                        }
                        else {
                            tabC[i][j].setText(c.getValeur() + "");

                            tabC[i][j].setBackground(colorMap.get(c.getValeur()));
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
                }
            }
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        rafraichir();
    }
}