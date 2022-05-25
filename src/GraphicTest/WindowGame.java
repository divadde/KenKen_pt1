package GraphicTest;

import Generating.ConcreteGenerator;
import Generating.Generator;
import Model.GridGame;
import Model.Table;

import javax.swing.*;

public class WindowGame extends JFrame {

    public WindowGame(String title, GridGame gg){
        setTitle(title);
        MainPanel mp = new MainPanel(gg);
        Menu m = new Menu(mp);
        add(mp);
        setJMenuBar(m);
        setResizable(false); //todo se puoi rendila variabile
        //pack();
        setSize(800,570);
        setLocation(350,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //in realtà nel programma dovremmo dare la possibilità di salvare la partita
        setVisible(true);
    }
}
