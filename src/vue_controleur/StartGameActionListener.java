package vue_controleur;

import modele.Jeu;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGameActionListener implements ActionListener{
    private Swing2048 frame;

    private Jeu jeu;

    public StartGameActionListener(Swing2048 frame,
                                   Jeu _jeu) {
        this.frame = frame;
        jeu = _jeu;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        jeu.init();
        frame.update(new Observable() );
    }
}
