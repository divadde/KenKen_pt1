package GraphicTest;

import Backtracking.Solver;
import Model.GridGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BacktrackingPanel extends JPanel implements ActionListener {
    private JButton solverButton;
    private JButton next, previous;
    private Solver<int[],Integer> solver;

    public BacktrackingPanel(GridGame gg){
        solver= new Solver(gg);
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
        next.setEnabled(false);
        previous.setEnabled(false);
        setSize(200,200);
        setLocation(600,400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==solverButton){
            solver.risolvi();
        }
    }

}
