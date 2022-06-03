package Graphic.Command;

import Backtracking.Backtracking;
import Model.CellIF;
import Model.GridGame;
import Model.MementoTable;

public class NextSolutionCommand implements Command {

    public NextSolutionCommand(){
    }

    @Override
    public void execute(GridGame gg) {
        Backtracking s = gg.getBacktracking();
        MementoTable currSol = (MementoTable) s.nextSol();
        if(currSol!=null) {
            gg.setMemento(currSol);
            System.out.println("NEXT SOLUTION");
        }
    }

}

