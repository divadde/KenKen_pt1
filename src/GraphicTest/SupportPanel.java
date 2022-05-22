package GraphicTest;

import Model.GridGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupportPanel extends JPanel implements ActionListener {
    private FlipPanel father;
    private JButton esci;

    public SupportPanel(GridGame gg,FlipPanel father){
        this.father=father;
        setLayout(null);
        add(new GamePanel(gg));
        add(new BacktrackingPanel());
        esci=new JButton("Esci");
        add(esci);
        esci.setSize(100,50);
        esci.setLocation(600,50);
        esci.addActionListener(this);
        //setSize(200,200);
        //setLocation(200,200);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==esci){
            CardLayout c = (CardLayout) father.getLayout();
            c.show(father,"start");
        }
    }
}
