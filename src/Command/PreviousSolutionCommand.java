package Command;

import Backtracking.Backtracking;
import Model.CellIF;
import Model.GridGame;

import java.util.Iterator;
import java.util.ListIterator;

public class PreviousSolutionCommand implements Command {
    GridGame gg;
    Backtracking s;

    public PreviousSolutionCommand(GridGame gg){
        this.gg=gg;
        s=gg.getBacktracking();
    }

    @Override
    public void execute() {
        CellIF[][] currSol = s.prevSol();
        if (currSol != null) {
            gg.setTable(currSol);
            gg.getMediator().notifyGridPanel(gg.getTable());
            System.out.println("PREV SOLUTION");
        }
    }
}
