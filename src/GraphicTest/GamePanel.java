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
    private GridGame gg;
    private GridPanel gp;
    private Menu m;
    private JButton nuovaPartita;


    public GamePanel(GridGame gg, Menu m,Mediator mediator){
        this.m=m;
        setSize(800,570);
        setLayout(null);
        nuovaPartita=new JButton("nuova partita");
        nuovaPartita.setLocation(100,100);
        nuovaPartita.setSize(200,50);
        nuovaPartita.addActionListener(this);
        add(nuovaPartita);
        this.gg=gg;
        this.gp=new GridPanel(gg,mediator);
        add(gp);
        //todo capisci perché al primo colpo non rileva i constraint!
        /*Generator g = gg.getGenerator();
        g.generate();

         */
        /*
        new NewGameCommand(gg).execute();
        gp.configura();
        */

        add(new BacktrackingPanel(gg,gp));
        //setSize(800,800);
        //setLocation(200,200);
    }

    public GridGame getGridGame(){
        return gg;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==m.getNuovaPartita()) {
            nuovaPartita.setVisible(false);
            gp.configura();
        }
        if(e.getSource()==nuovaPartita){
            new NewGameCommand(gg).execute();
            nuovaPartita.setVisible(false);
            gp.configura();
        }
    }
}
