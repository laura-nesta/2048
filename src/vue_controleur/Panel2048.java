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
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Panel2048 {
    private Swing2048 swing;
    private Jeu jeu;

    private JPanel panel;

    public Panel2048(Swing2048 _swing, Jeu _jeu){
        swing = _swing;
        jeu = _jeu;
        creaPanel();
    }

    public void creaPanel(){
        /*StartGameActionListener listener =
                new StartGameActionListener(swing, jeu);*/

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        int gridy = 0;

        JButton startGameButton = new JButton("Start Game");
        /*startGameButton.addActionListener(listener);
         addComponent(panel, startGameButton, 0, gridy++, 1, 1,
                regularInsets, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL);*/
    }
}
