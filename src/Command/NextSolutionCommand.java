package Command;

import Backtracking.Backtracking;
import Model.CellIF;
import Model.GridGame;

public class NextSolutionCommand implements Command{
    GridGame gg;
    Backtracking s;

    public NextSolutionCommand(GridGame gg){
        this.gg=gg;
        s=gg.getBacktracking();
    }

    @Override
    public void execute() {
        CellIF[][] currSol = (CellIF[][]) s.nextSol();
        if(currSol!=null) {
            gg.setTable(currSol);
            System.out.println("NEXT SOLUTION");
        }
    }

}

