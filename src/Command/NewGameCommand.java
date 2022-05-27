package Command;

import Generating.Generator;
import Model.GridGame;

public class NewGameCommand implements Command{
    GridGame gg;
    Generator g;

    public NewGameCommand(GridGame gg){
        this.gg=gg;
        g=gg.getGenerator();
    }

    @Override
    public void execute() {
        g.generate();
        System.out.println("Generato nuovo gioco");
    }
}
