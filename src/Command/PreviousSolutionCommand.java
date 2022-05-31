package Command;

import Backtracking.Backtracking;
import Model.CellIF;
import Model.GridGame;

public class PreviousSolutionCommand implements Command {
    GridGame gg;
    Backtracking s;

    public PreviousSolutionCommand(GridGame gg){
        this.gg=gg;
        s=gg.getBacktracking();
    }

    @Override
    public void execute() {
        CellIF[][] currSol = (CellIF[][]) s.prevSol();
        if (currSol != null) {
            gg.setTable(currSol);
            System.out.println("PREV SOLUTION");
        }
    }
}
