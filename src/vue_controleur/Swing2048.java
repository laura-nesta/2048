package vue_controleur;

import modele.Case;
import modele.Direction;
import modele.Jeu;
import modele.Score;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Swing2048 extends JFrame implements Observer{
    private static final int PIXEL_PER_SQUARE = 100;
    // tableau de cases : i, j -> case graphique
    private JLabel[][] tabC;
    private Jeu jeu;

    private HashMap<Integer, Color> colorMap = new HashMap<>();

    public Swing2048(Jeu _jeu) {
        jeu = _jeu;
        jeu.getScore().loadFile();
        colorMap.put(2, new Color(239, 224, 191, 255));
        colorMap.put(4, new Color(239, 239, 146, 255));
        colorMap.put(8, new Color(239, 200, 106, 255));
        colorMap.put(16, new Color(149, 232, 140,255));
        colorMap.put(32, new Color(57, 240, 164, 255));
        colorMap.put(64, new Color(121, 214, 242, 255));
        colorMap.put(128, new Color(114, 163, 237, 255));
        colorMap.put(256, new Color(106, 97, 237, 255));
        colorMap.put(512, new Color(179, 80, 237, 255));
        colorMap.put(1024, new Color(237, 80,193, 255));
        colorMap.put(2048, new Color(237, 97,97, 255));

        creaSwing();
    }

    public void creaSwing(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(jeu.getSize() * PIXEL_PER_SQUARE+300, jeu.getSize() * PIXEL_PER_SQUARE+150);
        //setSize(600,450);
        setLocation(500, 200);

        JPanel grillePanel = new JPanel();
        grillePanel.add(creaGrille());
        setContentPane(grillePanel);

        add(creaScore());
        add(creaBouton());
        add(creaMenu());
        ajouterEcouteurClavier();
        rafraichir();
    }

    public JPanel creaScore(){
        JPanel scorepanel = new JPanel();
        scorepanel.setPreferredSize(new Dimension(200,jeu.getSize() * PIXEL_PER_SQUARE));
        scorepanel.setBackground(new Color(176, 246, 214, 255));

        return scorepanel;
    }

    //crée le panel avec la grille
    public JPanel creaGrille(){
        tabC = new JLabel[jeu.getSize()][jeu.getSize()];
        JPanel contentPane = new JPanel(new GridLayout(jeu.getSize(), jeu.getSize()));
        contentPane.setPreferredSize(new Dimension(jeu.getSize() * PIXEL_PER_SQUARE, jeu.getSize() * PIXEL_PER_SQUARE));
        //contentPane.setPreferredSize(new Dimension(350,350));

        for (int i = 0; i < jeu.getSize(); i++) {
            for (int j = 0; j < jeu.getSize(); j++) {
                Border border = BorderFactory.createLineBorder(Color.darkGray, 3);
                tabC[i][j] = new JLabel();
                tabC[i][j].setBorder(border);
                tabC[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                /*if((jeu.getCase(i, j)) != null && (jeu.getCase(i, j)).getValeur() >= 16)
                    tabC[i][j].setForeground(Color.white);
                else*/
                tabC[i][j].setForeground(Color.darkGray);
                if ((jeu.getCase(i, j)) == null)
                    tabC[i][j].setBackground(Color.gray);
                else
                    tabC[i][j].setBackground(colorMap.get((jeu.getCase(i, j)).getValeur()));
                tabC[i][j].setOpaque(true);
                contentPane.add(tabC[i][j]);
            }
        }
        return contentPane;
    }

    public JPanel creaMenu(){
        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(jeu.getSize() * PIXEL_PER_SQUARE+200 , 25));
        menu.setBackground(Color.MAGENTA);

        return menu;
    }

    public JPanel creaBouton(){
        JPanel bouton = new JPanel();
        bouton.setPreferredSize(new Dimension(jeu.getSize() * PIXEL_PER_SQUARE+200 , 50));
        bouton.setBackground(Color.blue);

        JButton restart = new JButton("restart");
        //ActionListener start = new Jeu(4);
        restart.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //jeu.restart();
            }
        });

        JButton quitter = new JButton("quitter");
        quitter.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                exitProcedure();
            }
        });
        //bouton.add(restart);
        //bouton.add(quitter);
        return bouton;
    }

    public void exitProcedure() {
        jeu.getScore().setMax();
        jeu.getScore().savefile();
        System.exit(0);
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
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
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