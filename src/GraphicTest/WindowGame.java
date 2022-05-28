package GraphicTest;

import Generating.ConcreteGenerator;
import Generating.Generator;
import Model.GridGame;

import javax.swing.*;

public class WindowGame extends JFrame {

    public WindowGame(String title, GridGame gg){
        setTitle(title);
        Menu m = new Menu();
        GamePanel gp = new GamePanel(gg,m);
        m.setGamePanel(gp);
        add(gp);
        setJMenuBar(m);


        setResizable(false); //todo se puoi rendila variabile
        //pack();
        setSize(800,570);
        setLocation(350,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //in realtà nel programma dovremmo dare la possibilità di salvare la partita
        setVisible(true);
    }

}
