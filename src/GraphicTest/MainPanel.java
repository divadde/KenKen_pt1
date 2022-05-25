package GraphicTest;

import Model.GridGame;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private GridGame gg;
    private StartPanel sp;
    private GamePanel gp;

    public MainPanel (GridGame gg){
        this.gg=gg;
        setLayout(new CardLayout());
        this.sp=new StartPanel(this);
        this.gp=new GamePanel(gg,this);
        add(sp,"start");
        add(gp,"game");
    }

    public GridGame getGridGame(){
        return gg;
    }
    public StartPanel getStartPanel() { return sp; }
    public GamePanel getGamePanel() { return gp; }
}
