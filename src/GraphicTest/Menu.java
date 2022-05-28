package GraphicTest;

import Command.Command;
import Command.NewGameCommand;
import Generating.Generator;
import Model.GridGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar implements ActionListener {
    private GamePanel gp; //il gamepanel è l'observer
    private JMenu gioca, impostazioni, help, impostazioniGeneratore;
    private JMenuItem nuovaPartita, caricaPartita, impostaDimensione;

    public Menu(){
        gioca = new JMenu("Gioca");
        impostazioni = new JMenu("Impostazioni");
        help = new JMenu("Help");
        impostazioniGeneratore= new JMenu("Impostazioni generatore");
        add(gioca);
        add(impostazioni);
        add(help);
        nuovaPartita=new JMenuItem("Nuova partita");
        caricaPartita=new JMenuItem("Carica partita");
        impostaDimensione=new JMenuItem("Imposta dimensione");
        gioca.add(nuovaPartita);
        gioca.add(caricaPartita);
        impostazioni.add(impostaDimensione);
        impostazioni.add(impostazioniGeneratore);
    }

    //attach observer
    public void setGamePanel(GamePanel gp){
        this.gp=gp;
        nuovaPartita.addActionListener(gp);
    }

    public JMenuItem getNuovaPartita(){
        return nuovaPartita;
    }
    public JMenuItem getCaricaPartita(){ return caricaPartita; }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

}
