package Graphic.Command;

import Backtracking.Backtracking;
import Model.CellIF;
import Model.GridGame;

public class NextSolutionCommand implements Command {

    public NextSolutionCommand(){
    }

    @Override
    public void execute(GridGame gg) {
        Backtracking s = gg.getBacktracking();
        CellIF[][] currSol = (CellIF[][]) s.nextSol();
        if(currSol!=null) {
            gg.setTable(currSol);
            System.out.println("NEXT SOLUTION");
        }
    }

}

