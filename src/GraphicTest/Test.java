package GraphicTest;

import Model.ConcreteGenerator;
import Model.Generator;
import Model.GridGame;
import Model.Table;

import javax.swing.*;

public class Test {

    public static void main(String[] args){

        JFrame f = new JFrame("KenKen");
        GridGame gg = new Table();
        Generator g = new ConcreteGenerator(gg,6);
        g.generate();
        FlipPanel flip = new FlipPanel(gg);
        Menu menuGioco = new Menu(flip);
        f.add(flip);
        f.setJMenuBar(menuGioco);
        f.setResizable(false); //todo se puoi rendila variabile
        //f.pack();
        f.setSize(800,570);
        f.setLocation(350,100);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //in realtà nel programma dovremmo dare la possibilità di salvare la partita
        f.setVisible(true);

    }

}
