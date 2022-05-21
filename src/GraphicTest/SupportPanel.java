package GraphicTest;

import Model.GridGame;

import javax.swing.*;

public class SupportPanel extends JPanel {

    public SupportPanel(GridGame gg){
        setLayout(null);
        add(new GamePanel(gg));
        //setSize(200,200);
        //setLocation(200,200);
    }
}
