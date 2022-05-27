package GraphicTest;

import Command.Command;
import Command.NewGameCommand;
import Generating.Generator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar implements ActionListener {
    private GamePanel gp;
    private JMenu gioca, impostazioni, help;
    private JMenuItem nuovaPartita, caricaPartita;
    private Command ngc;
    private Mediator mediator;

    public Menu(){
        gioca = new JMenu("Gioca");
        impostazioni = new JMenu("Impostazioni");
        help = new JMenu("Help");
        add(gioca);
        add(impostazioni);
        add(help);
        nuovaPartita=new JMenuItem("Nuova partita");
        caricaPartita=new JMenuItem("Carica partita");
        gioca.add(nuovaPartita);
        gioca.add(caricaPartita);
        nuovaPartita.addActionListener(this);
    }

    public void setGamePanel(GamePanel gp){
        this.gp=gp;
        ngc = new NewGameCommand(gp.getGridGame());
    }

    public JMenuItem getNuovaPartita(){
        return nuovaPartita;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==nuovaPartita){
            ngc.execute();
            gp.actionPerformed(new ActionEvent(nuovaPartita,0,"nuovaPartita"));
            gp.repaint();
            gp.revalidate();
        }
    }
}
