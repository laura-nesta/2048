package vue_controleur;

import com.sun.tools.javac.Main;
import modele.Case;
import modele.Direction;
import modele.Jeu;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Swing2048 extends JFrame implements Observer{
    private static final int PIXEL_PER_SQUARE = 100;
    // tableau de cases : i, j -> case graphique
    private JLabel[][] tabC;
    private Jeu jeu;

    JFrame frame = this;

    private HashMap<Integer, Color> colorMap = new HashMap<>();

    JLabel MaxScore;
    JLabel MaxCell;
    JLabel currentScore;
    JLabel currentCell;

    JButton restart;
    JButton quitter;
    JButton reinitScore;

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

        ActionListener quitterListener =new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        };
        quitter = new JButton("quitter");
        quitter.setBackground(new Color(239, 209, 209));
        quitter.addActionListener(quitterListener);

        ActionListener restartListener =new ActionListener(){
            public void actionPerformed(ActionEvent e){
                jeu.init();
                rafraichir();
            }
        };

        restart = new JButton("restart");
        restart.addActionListener(restartListener);

        ActionListener reinitListener = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                jeu.getScore().reinitScore();
                rafraichir();
            }
        };
        reinitScore = new JButton("reinitialise Score");
        reinitScore.addActionListener(reinitListener);


        creaSwing();
        //jeu.addObserver(this);
    }

    public void creaSwing(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(jeu.getSize() * PIXEL_PER_SQUARE+300, jeu.getSize() * PIXEL_PER_SQUARE+150);
        //setSize(600,450);
        setLocation(500, 200);
        ajouterEcouteurClavier();
        JPanel grillePanel = new JPanel();
        grillePanel.add(creaGrille());
        setContentPane(grillePanel);

        add(creaScore());
        //add(creaBouton(this));
        add(creaNotic());
        add(creaMenu());

        rafraichir();
    }

    public JPanel creaScore(){
        JPanel scorepanel = new JPanel();
        scorepanel.setPreferredSize(new Dimension(200,jeu.getSize() * PIXEL_PER_SQUARE));
        scorepanel.setBackground(new Color(207, 231, 231, 255));

        final Insets regularInsets   =
                new Insets(10, 10, 0, 10);
        final Insets spaceInsets =
                new Insets(10, 10, 10, 10);

        scorepanel.setLayout(new GridBagLayout());
        int gridy = 0;

        JLabel highScoreLabel = new JLabel("Meilleur Score:");
        addComponent(scorepanel, highScoreLabel, 0, gridy, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        MaxScore = new JLabel("" + jeu.getScore().getMaxScore());
        MaxScore.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(scorepanel, MaxScore, 1, gridy++, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);


        JLabel highCellLabel = new JLabel("Meilleure Cellule:");
        addComponent(scorepanel, highCellLabel, 0, gridy, 1, 1,
                spaceInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        MaxCell = new JLabel("" + jeu.getScore().getMaxCell());
        MaxCell.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(scorepanel, MaxCell, 1, gridy++, 1, 1,
                spaceInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        JLabel currentScoreLabel = new JLabel("Score:");
        addComponent(scorepanel, currentScoreLabel, 0, gridy, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        currentScore = new JLabel("" + jeu.getScore().getCurrentScore());
        currentScore.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(scorepanel, currentScore, 1, gridy++, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        JLabel currentCellLabel = new JLabel("Cellule max actuelle:");
        addComponent(scorepanel, currentCellLabel, 0, gridy, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        currentCell = new JLabel("" + jeu.getScore().getCurrentCell());
        currentCell.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(scorepanel, currentCell, 1, gridy++, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);


        repaint();
        return scorepanel;
    }

    private void addComponent(Container container, Component component,
                              int gridx, int gridy, int gridwidth, int gridheight,
                              Insets insets, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy,
                gridwidth, gridheight, 1.0D, 1.0D, anchor, fill,
                insets, 0, 0);
        container.add(component, gbc);
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
        JPanel menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(jeu.getSize() * PIXEL_PER_SQUARE+200 , 25));
        //menuPanel.setBackground(Color.MAGENTA);
        JMenu menu;
        JMenuItem e1, e2, e3, e4, e5;
            JMenuBar menubar = new JMenuBar();
            // Créer le menu
            menu = new JMenu("Choisissez la taille de votre grille");
            // Créer les éléments du menu et sous menu
            e1 = new JMenuItem("4x4");
            e1.addActionListener(mnuListener(4));
            e2 = new JMenuItem("5x5");
            e2.addActionListener(mnuListener(5));
            e3 = new JMenuItem("6x6");
            e3.addActionListener(mnuListener(6));
            e4 = new JMenuItem("7x7");
            e4.addActionListener(mnuListener(7));
            e5 = new JMenuItem("8x8");
            e5.addActionListener(mnuListener(8));

            // Ajouter les éléments au menu
            menu.add(e1);
            menu.add(e2);
            menu.add(e3);
            menu.add(e4);
            menu.add(e5);
            // Ajouter le menu au barre de menu
            menubar.add(menu);
            menuPanel.add(menubar);
            // Ajouter la barre de menu au fram

        return menuPanel;
    }

    /*public void mnuListener(ActionEvent event) {
        JOptionPane.showMessageDialog( this, "Button clicked !" );
    }*/
    public ActionListener mnuListener(int i) {
        //exitProcedure();
        return null;
    }

    public JPanel creaNotic() {

        int gridy = 0;
        Insets regularInsets =
                new Insets(10, 10, 10, 10);

        JPanel notice = new JPanel();
        notice.setPreferredSize(new Dimension(jeu.getSize() * PIXEL_PER_SQUARE + 200, 50));

        JLabel reinitScore = new JLabel("Appuyer sur S pour réinitialiser les meilleurs scores");
        JLabel restartg = new JLabel("Appuyer sur R pour recommencer la partie");
        addComponent(notice, restartg, 0, gridy, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.VERTICAL);
        return notice;
    }

    public JPanel creaBouton(JFrame _frame){

        JPanel bouton = new JPanel();
        bouton.setPreferredSize(new Dimension(jeu.getSize() * PIXEL_PER_SQUARE+200 , 70));

        int gridy = 0;
        Insets regularInsets   =
                new Insets(10, 10, 0, 10);

        StartGameActionListener listener =
                new StartGameActionListener((Swing2048) frame, jeu);

        JButton startGameButton = new JButton("Restart Game");
        startGameButton.setBackground(new Color(221, 204, 238));
        startGameButton.addActionListener(listener);
        addComponent(bouton, startGameButton, 0, gridy++, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        ActionListener listener2 =new ActionListener(){
            public void actionPerformed(ActionEvent e){
                jeu.getScore().reinitScore();
                rafraichir();
            }
        };
        JButton reinitScore = new JButton("Reinitialiser les scores");
        reinitScore.setBackground(new Color(221, 204, 238));
        reinitScore.addActionListener(listener2);
        addComponent(bouton, reinitScore, 0, gridy++, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        ActionListener listener3 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        };
        JButton quitter = new JButton("Quitter");
        quitter.setBackground(new Color(239, 209, 209));
        quitter.addActionListener(listener3);
        addComponent(bouton, quitter, 0, gridy++, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);
        return bouton;
    }

    public void exitProcedure() {
        jeu.getScore().reinitScore();
        System.exit(0);
    }

    public JPanel gameOverPanel(){
        JPanel goPanel = new JPanel();
        JLabel go = new JLabel("Game Over!");
        goPanel.setPreferredSize(new Dimension(600,450));
        //goPanel.setBackground(new Color(106, 97, 237, 132));
        go.setSize(600, 150);
        go.setFont(new Font("Calibri", Font.PLAIN, 100));
        go.setVerticalTextPosition(SwingConstants.CENTER);
        goPanel.add(go);
        JPanel bp = new JPanel();
        bp.add(quitter);
        //bp.add(reinitScore);
        bp.setPreferredSize(new Dimension(600, 50));
        goPanel.add(bp);
        //goPanel.add(restart);
        return goPanel;
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
                currentCell.setText("" + jeu.getScore().getCurrentCell());
                currentScore.setText("" + jeu.getScore().getCurrentScore());
                MaxScore.setText("" + jeu.getScore().getMaxScore());
                MaxCell.setText("" + jeu.getScore().getMaxCell());
                if(jeu.getJeuFini())
                    setContentPane(gameOverPanel());
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
                    case KeyEvent.VK_S -> jeu.getScore().reinitScore();
                    case KeyEvent.VK_R -> jeu.init();
                }
            }
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        rafraichir();
        this.repaint();
    }


    public void update(Observable o) {
        rafraichir();
        this.repaint();
    }
}