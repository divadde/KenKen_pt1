package GraphicTest;

import Command.Command;
import Command.NewGameCommand;
import Generating.Generator;
import Model.GridGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    private Menu menu; //Il menu è il subject da osservare
    private JButton nuovaPartita; //altro subject da osservare
    private JButton caricaPartita; //altro subject da osservare
    private GridPanel gp; //figlio
    private BacktrackingPanel backtrackingPanel; //figlio


    public GamePanel(GridGame gg, Menu menu){
        this.menu=menu;
        gp=new GridPanel(this,gg);
        backtrackingPanel = new BacktrackingPanel(this,gg);

        nuovaPartita=new JButton("Nuova partita");
        nuovaPartita.setLocation(300,150);
        nuovaPartita.setSize(150,40);
        nuovaPartita.addActionListener(this);
        caricaPartita=new JButton("Carica partita");
        caricaPartita.setLocation(300,250);
        caricaPartita.setSize(150,40);
        //caricaPartita.addActionListener(this);
        add(caricaPartita);
        add(nuovaPartita);
        add(gp);
        add(backtrackingPanel);
        backtrackingPanel.setVisible(false);

        setSize(800,570);
        setLayout(null);
        //setLocation(200,200);
    }

    public JButton getNuovaPartita(){
        return nuovaPartita;
    }
    public JButton getCaricaPartita(){
        return caricaPartita;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==menu.getNuovaPartita() || e.getSource()==nuovaPartita) {
            nuovaPartita.setVisible(false);
            caricaPartita.setVisible(false);
            //backtrackingPanel.setVisible(true);
            //demanda al gridpanel
            gp.actionPerformed(new ActionEvent(this.nuovaPartita,0,"nuovaPartita"));
            backtrackingPanel.actionPerformed(new ActionEvent(this.nuovaPartita,0,"nuovaPartita"));
        }
        if(e.getSource()==menu.getCaricaPartita() || e.getSource()==caricaPartita) {
            nuovaPartita.setVisible(false);
            caricaPartita.setVisible(false);
            backtrackingPanel.setVisible(true);
            /*
            //demanda al gridpanel
            gp.actionPerformed(new ActionEvent(this.nuovaPartita,0,"nuovaPartita"));
            backtrackingPanel.actionPerformed(new ActionEvent(this.nuovaPartita,0,"nuovaPartita"));
             */
        }
        if(e.getActionCommand().equals("next")){
            gp.actionPerformed(e);
        }
        if(e.getActionCommand().equals("previous")){
            gp.actionPerformed(e);
        }
    }
}
