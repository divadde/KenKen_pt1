package Graphic.Command;

import Backtracking.Backtracking;
import Generating.Generator;
import Model.GridGame;
import Model.Settings;

public class NewGameCommand implements Command {
    Settings s;

    public NewGameCommand(Settings s){
        this.s=s;
    }

    @Override
    public void execute(GridGame gg) {
        gg.setDimension(s.getDimension());
        Backtracking b = gg.getBacktracking();
        b.setMaxSol(s.getMaxSol());
        System.out.println("Impostate "+s.getMaxSol()+"soluzioni");
        Generator g =gg.getGenerator();
        g.generate();
        System.out.println("Generato nuovo gioco");
    }
}
