package Graphic.Command;

import Backtracking.Backtracking;
import Model.CellIF;
import Model.GridGame;
import Model.MementoTable;

public class PreviousSolutionCommand implements Command {

    public PreviousSolutionCommand(){}

    @Override
    public void execute(GridGame gg) {
        Backtracking s = gg.getBacktracking();
        MementoTable currSol = (MementoTable) s.prevSol();
        if (currSol != null) {
            gg.setMemento(currSol);
            System.out.println("PREV SOLUTION");
        }
    }
}
