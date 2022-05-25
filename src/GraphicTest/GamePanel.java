package GraphicTest;

import Model.GridGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    private GridGame gg;
    private MainPanel father;
    private GridPanel gp;
    private JButton esci;

    public GamePanel(GridGame gg, MainPanel father){
        setSize(800,570);
        setLayout(null);
        this.father=father;
        this.gg=gg;
        this.gp=new GridPanel(gg);
        add(gp);
        add(new BacktrackingPanel(gg,gp));
        esci=new JButton("Esci");
        add(esci);
        esci.setSize(100,50);
        esci.setLocation(600,50);
        esci.addActionListener(this);
        //setSize(800,800);
        //setLocation(200,200);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==esci){
            CardLayout c = (CardLayout) father.getLayout();
            c.show(father,"start");
        }
        if(e.getSource()==father.getStartPanel().getNuovaPartita()) {
            gp.configura();
        }
    }
}
