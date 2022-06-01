package Graphic;

import Graphic.Command.NewGameCommand;
import Graphic.Mediator.Mediator;
import Graphic.Mediator.Request;
import Graphic.Mediator.Subject;
import Model.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameSettings extends JFrame implements ActionListener, Subject {
    private Mediator mediator;
    private JTextField dimTextField, maxSolTextField;
    private JButton ok;
    private Settings settings;

    public GameSettings(Mediator mediator) {
        setMediator(mediator);

        //impostazioni di default
        settings = new Settings(6,10);

        setTitle("Impostazioni di gioco");
        setResizable(false);
        setSize(300, 250);
        setLocation(500, 300);

        Container c = getContentPane();
        c.setLayout(null);

        JLabel l1 = new JLabel("Inserisci dimensione del KenKen: ");
        l1.setSize(300, 30);
        l1.setLocation(30, 30);
        JLabel l2 = new JLabel("Numero massimo di soluzioni: ");
        l2.setSize(300, 30);
        l2.setLocation(30, 100);

        c.add(l1);
        c.add(l2);

        dimTextField = new JTextField();
        dimTextField.setText(Integer.toString(settings.getDimension()));
        dimTextField.setSize(40, 30);
        dimTextField.setLocation(30, 60);
        dimTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    settings.setDimension(Integer.parseInt(dimTextField.getText()));
                } catch(NumberFormatException exc){
                    settings.setDimension(6);
                }
            }
        });
        maxSolTextField = new JTextField();
        maxSolTextField.setText(Integer.toString(settings.getMaxSol()));
        maxSolTextField.setSize(40, 30);
        maxSolTextField.setLocation(30, 130);
        maxSolTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    settings.setMaxSol(Integer.parseInt(maxSolTextField.getText()));
                } catch(NumberFormatException exc){
                    settings.setMaxSol(10);
                }
            }
        });

        c.add(dimTextField);
        c.add(maxSolTextField);

        ok = new JButton("Ok");
        ok.setSize(50, 30);
        ok.setLocation(120, 170);
        ok.addActionListener(this);

        c.add(ok);


        addWindowListener( new WindowAdapter() {
                            public void windowClosing(WindowEvent e){
                                mediator.notify(new Request(Request.Tipo.READY,new NewGameCommand(settings)));
                                } } );

    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator=mediator;
        mediator.addSubject(this);
    }

    //todo aggiungi controlli
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ok) {
            mediator.notify(new Request(Request.Tipo.READY,new NewGameCommand(settings)));
        }
    }

    @Override
    public void handleRequest(Request request) {
        if(request.getTipo()==Request.Tipo.NEWGAME){
            this.setVisible(true);
        }
    }

}
