package Graphic;

import Model.GridGame;
import Model.KenKen;

public final class Application {
    GridGame game;
    WindowGame windowGame;

    public Application(GridGame game){
        this.game=game;
    }

    public void start(){
        game.setDimension(6); //dimensione standard
        windowGame = new WindowGame("KenKen",game);
    }

    public static void main(String[] args){
        new Application(new KenKen()).start();
    }

}
