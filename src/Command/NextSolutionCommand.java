package Command;

import Backtracking.Backtracking;
import Model.CellIF;
import Model.GridGame;

import java.util.Iterator;
import java.util.ListIterator;

public class NextSolutionCommand implements Command{
    GridGame gg;
    Backtracking s;

    public NextSolutionCommand(GridGame gg){
        this.gg=gg;
        s=gg.getBacktracking();
    }

    @Override
    public void execute() {
        CellIF[][] currSol = s.nextSol();
        if(currSol!=null) {
            gg.setTable(currSol);
            //gg.getMediator().notifyGridPanel(gg.getTable());
            System.out.println("NEXT SOLUTION");
        }
    }

}

