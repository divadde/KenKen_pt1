package Graphic;

import Graphic.Command.LoadGameCommand;
import Graphic.Command.NewGameCommand;
import Graphic.Command.SaveGameCommand;
import Graphic.Mediator.Mediator;
import Graphic.Mediator.Request;
import Graphic.Mediator.Subject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar implements ActionListener, Subject {
    private JMenu gioca, help;
    private JMenuItem nuovaPartita, salvaPartita, caricaPartita;
    private GameSettings menuSett;
    private Mediator mediator;

    public Menu(Mediator mediator){
        setMediator(mediator);

        menuSett=new GameSettings(mediator);
        gioca = new JMenu("Gioca");
        help = new JMenu("Help");

        add(gioca);

        add(help);
        nuovaPartita=new JMenuItem("Nuova partita");
        salvaPartita=new JMenuItem("Salva partita");
        caricaPartita=new JMenuItem("Carica partita");

        nuovaPartita.addActionListener(this);
        salvaPartita.addActionListener(this);
        caricaPartita.addActionListener(this);

        gioca.add(nuovaPartita);
        gioca.add(salvaPartita);
        gioca.add(caricaPartita);

        salvaPartita.setEnabled(false);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator=mediator;
        mediator.addSubject(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==nuovaPartita){
            mediator.notify(new Request(Request.Tipo.NEWGAME,null));
        }
        else if(e.getSource()==salvaPartita){
            mediator.notify(new Request(Request.Tipo.SAVEGAME,new SaveGameCommand()));
        }
        else if(e.getSource()==caricaPartita){
            mediator.notify(new Request(Request.Tipo.LOADGAME,new LoadGameCommand()));
        }
        else if(e.getSource()==help){
            mediator.notify(new Request(Request.Tipo.HELP,null));
        }
    }

    @Override
    public void handleRequest(Request request) {
        if(request.getTipo()==Request.Tipo.NEWGAME){
            menuSett.setVisible(true);
        }
        else if(request.getTipo()==Request.Tipo.READY){
            menuSett.setVisible(false);
            salvaPartita.setEnabled(true);
        }
        else if(request.getTipo()==Request.Tipo.SAVEGAME){
            //todo
            //nothing to do
        }
        else if(request.getTipo()==Request.Tipo.LOADGAME){
            //todo
            //nothing to do
        }
        else if(request.getTipo()==Request.Tipo.HELP){
            //todo
            //mostrare un pannellino con le informazioni
        }
    }

}
