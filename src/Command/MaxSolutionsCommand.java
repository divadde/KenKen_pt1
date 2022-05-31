package Command;

import Backtracking.Backtracking;
import Model.GridGame;

import javax.swing.*;

public class MaxSolutionsCommand implements Command{
    GridGame gg;
    Backtracking s;

    public MaxSolutionsCommand(GridGame gg){
        this.gg=gg;
        s=gg.getBacktracking();
    }

    @Override
    public void execute() {
        int numSol=10;
        while(true) {
            String mess = JOptionPane.showInputDialog("Imposta il numero massimo di soluzioni da calcolare. \n NB: default=10");
            if(mess==null) {
                break;
            }
            try {
                numSol=Integer.parseInt(mess);
                break;
            } catch(RuntimeException ex) {
                JOptionPane.showMessageDialog(null,"Inserire un intero");
            }
        }
        s.setMaxSol(numSol);
    }
}
