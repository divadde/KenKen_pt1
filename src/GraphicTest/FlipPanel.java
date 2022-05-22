package GraphicTest;

import Model.GridGame;

import javax.swing.*;
import java.awt.*;

public class FlipPanel extends JPanel {
    private StartPanel sp;
    private SupportPanel supp;
    private GridGame gg;
    private CardLayout c;

    public FlipPanel(GridGame gg){
        this.gg=gg;
        c=new CardLayout();
        setLayout(c);
        sp=new StartPanel(this);
        supp=new SupportPanel(gg,this);
        add(sp,"start");
        add(supp,"game");
    }

}
