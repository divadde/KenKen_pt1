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
    private JMenuItem nuovaPartita, salvaPartita, caricaPartita, impostaDimensione;
    private JOptionPane selezioneGrandezza;

    public Menu(){
        gioca = new JMenu("Gioca");
        impostazioni = new JMenu("Impostazioni");
        help = new JMenu("Help");
        impostazioniGeneratore= new JMenu("Impostazioni generatore");
        add(gioca);
        add(impostazioni);
        add(help);
        nuovaPartita=new JMenuItem("Nuova partita");
        salvaPartita=new JMenuItem("Salva partita");
        caricaPartita=new JMenuItem("Carica partita");
        impostaDimensione=new JMenuItem("Imposta dimensione");
        gioca.add(nuovaPartita);
        gioca.add(salvaPartita);
        gioca.add(caricaPartita);
        impostazioni.add(impostaDimensione);
        impostazioni.add(impostazioniGeneratore);
        salvaPartita.setEnabled(false);
    }

    //attach observer
    public void setGamePanel(GamePanel gp){
        this.gp=gp;
        nuovaPartita.addActionListener(gp);
        salvaPartita.addActionListener(gp);
        caricaPartita.addActionListener(gp);
        impostaDimensione.addActionListener(gp);
    }

    public JMenuItem getNuovaPartita(){
        return nuovaPartita;
    }
    public JMenuItem getSalvaPartita(){
        return salvaPartita;
    }
    public JMenuItem getCaricaPartita(){ return caricaPartita; }
    public JMenuItem getImpostaDimensione(){ return impostaDimensione; }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

}
