package Graphic.Command;

import Backtracking.Backtracking;
import Model.CellIF;
import Model.GridGame;

public class PreviousSolutionCommand implements Command {

    public PreviousSolutionCommand(){}

    @Override
    public void execute(GridGame gg) {
        Backtracking s = gg.getBacktracking();
        CellIF[][] currSol = (CellIF[][]) s.prevSol();
        if (currSol != null) {
            gg.setTable(currSol);
            System.out.println("PREV SOLUTION");
        }
    }
}
