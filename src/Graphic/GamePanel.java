package Graphic;

import Backtracking.Backtracking;
import Graphic.Command.LoadGameCommand;
import Graphic.Command.NewGameCommand;
import Graphic.Mediator.Mediator;
import Graphic.Mediator.Request;
import Graphic.Mediator.Subject;
import Model.GridGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener, Subject {
    private JButton nuovaPartita, caricaPartita, suggerimenti;
    private Mediator mediator;


    public GamePanel(GridGame gg, Mediator mediator){
        setMediator(mediator);

        GridPanel gp=new GridPanel(this,gg,mediator);
        BacktrackingPanel backtrackingPanel = new BacktrackingPanel(mediator);

        nuovaPartita=new JButton("Nuova partita");
        nuovaPartita.setLocation(300,150);
        nuovaPartita.setSize(150,40);
        nuovaPartita.addActionListener(this);
        caricaPartita=new JButton("Carica partita");
        caricaPartita.setLocation(300,250);
        caricaPartita.setSize(150,40);
        suggerimenti=new JButton("Mostra vincoli");
        suggerimenti.setLocation(600,100);
        suggerimenti.setSize(150,40);
        suggerimenti.addActionListener(this);
        add(suggerimenti);
        add(caricaPartita);
        add(nuovaPartita);
        add(gp);
        add(backtrackingPanel);

        suggerimenti.setVisible(false);

        setSize(800,570);
        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==nuovaPartita) {
            mediator.notify(new Request(Request.Tipo.NEWGAME,null));
        }
        else if(e.getSource()==caricaPartita) {
            mediator.notify(new Request(Request.Tipo.LOADGAME,new LoadGameCommand()));
        }
        else if(e.getSource()==suggerimenti){
            mediator.notify(new Request(Request.Tipo.SHOWSUGGESTINGS,null));
        }
    }

    @Override
    public void handleRequest(Request request) {
        if(request.getTipo()==Request.Tipo.READY){
            suggerimenti.setVisible(true);
            nuovaPartita.setVisible(false);
            caricaPartita.setVisible(false);
        }
        else  if(request.getTipo()==Request.Tipo.LOADGAME){
            //todo
            suggerimenti.setVisible(true);
            nuovaPartita.setVisible(false);
            caricaPartita.setVisible(false);
        }
        else if(request.getTipo()==Request.Tipo.SAVEGAME){
            //todo
        }
        else if(request.getTipo()==Request.Tipo.SHOWSUGGESTINGS){
            //todo
        }
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator=mediator;
        mediator.addSubject(this);
    }
}
