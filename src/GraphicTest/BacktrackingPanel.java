package GraphicTest;

import Backtracking.Solver;
import Command.*;
import Model.GridGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BacktrackingPanel extends JPanel implements ActionListener {
    private GamePanel gp;
    private JButton solverButton;
    private JButton next, previous;
    private Command mostraSoluzione, prossimaSoluzione, precSoluzione;


    public BacktrackingPanel(GamePanel gp, GridGame gg){
        this.gp=gp;
        setLayout(null);
        solverButton=new JButton("Mostra soluzioni");
        solverButton.setSize(140,30);
        solverButton.setLocation(0,0);
        next=new JButton("->");
        next.setSize(60,30);
        next.setLocation(80,40);
        previous=new JButton("<-");
        previous.setSize(60,30);
        previous.setLocation(0,40);
        add(solverButton); add(next); add(previous);
        solverButton.addActionListener(this);
        prossimaSoluzione = new NextSolutionCommand(gg);
        precSoluzione = new PreviousSolutionCommand(gg);
        next.addActionListener(this);
        previous.addActionListener(this);
        setSize(200,200);
        setLocation(600,400);
        mostraSoluzione = new SolveCommand(gg);
        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==gp.getNuovaPartita() || e.getActionCommand()=="caricaPartita"){
            setVisible(true);
            next.setEnabled(false);
            previous.setEnabled(false);
        }
        if(e.getSource()==solverButton){
            next.setEnabled(true);
            previous.setEnabled(true);
            mostraSoluzione.execute();
            prossimaSoluzione.execute();
        }
        if(e.getSource()==next){
            prossimaSoluzione.execute();
            gp.actionPerformed(new ActionEvent(this.next,0,"next"));
        }
        if(e.getSource()==previous){
            precSoluzione.execute();
            gp.actionPerformed(new ActionEvent(this.previous,0,"previous"));
        }
    }

}
