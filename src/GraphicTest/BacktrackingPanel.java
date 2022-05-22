package GraphicTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BacktrackingPanel extends JPanel implements ActionListener {
    private JButton solver;
    private JButton next, previous;

    public BacktrackingPanel(){
        setLayout(null);
        solver=new JButton("Mostra soluzioni");
        solver.setSize(140,30);
        solver.setLocation(0,0);
        next=new JButton("->");
        next.setSize(60,30);
        next.setLocation(80,40);
        previous=new JButton("<-");
        previous.setSize(60,30);
        previous.setLocation(0,40);
        add(solver); add(next); add(previous);
        next.setEnabled(false);
        previous.setEnabled(false);
        setSize(200,200);
        setLocation(600,400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
