package Command;

import Backtracking.Backtracking;
import Model.GridGame;

public class SolveCommand implements Command {
    GridGame gg;
    Backtracking s;

    public SolveCommand(GridGame gg){
        this.gg=gg;
        s=gg.getBacktracking();
    }

    @Override
    public void execute() {
        s.solve();
        System.out.println("SolveCommand: soluzioni trovate");
    }

}
