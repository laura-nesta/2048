package vue_controleur;

import modele.Jeu;

import javax.swing.*;
import java.awt.*;

public class AffScore  extends JPanel{

    private Jeu jeu;
    private static JPanel panel;

    private JTextField MaxScore;
    private JTextField MaxCell;
    private JTextField currentScore;
    private JTextField currentCell;

    public AffScore(Jeu _jeu){
        jeu = _jeu;
        creerScore();
    }

    public void creerScore(){
        final Insets regularInsets   =
                new Insets(10, 10, 0, 10);
        final Insets spaceInsets =
                new Insets(10, 10, 10, 10);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        int gridy = 0;

        JLabel highScoreLabel = new JLabel("Meilleur Score:");
        addComponent(panel, highScoreLabel, 0, gridy, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        MaxScore = new JTextField(6);
        MaxScore.setEditable(false);
        MaxScore.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(panel, MaxScore, 1, gridy++, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        JLabel highCellLabel = new JLabel("Meilleure Cellule:");
        addComponent(panel, highCellLabel, 0, gridy, 1, 1,
                spaceInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        MaxCell = new JTextField(6);
        MaxCell.setEditable(false);
        MaxCell.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(panel, MaxCell, 1, gridy++, 1, 1,
                spaceInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        JLabel currentScoreLabel = new JLabel("Score:");
        addComponent(panel, currentScoreLabel, 0, gridy, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        currentScore = new JTextField(6);
        currentScore.setEditable(false);
        currentScore.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(panel, currentScore, 1, gridy++, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        JLabel currentCellLabel = new JLabel("Cellule max actuelle:");
        addComponent(panel, currentCellLabel, 0, gridy, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);

        currentCell = new JTextField(6);
        currentCell.setEditable(false);
        currentCell.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(panel, currentCell, 1, gridy++, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);
    }

    private void addComponent(Container container, Component component,
                              int gridx, int gridy, int gridwidth, int gridheight,
                              Insets insets, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy,
                gridwidth, gridheight, 1.0D, 1.0D, anchor, fill,
                insets, 0, 0);
        container.add(component, gbc);
    }
    public static JPanel getPanel() {
        return panel;
    }
}
