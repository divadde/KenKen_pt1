package GraphicTest;

import Model.CellIF;
import Model.GridGame;

public class Mediator {
    GridPanel gridPanel;
    GridGame gridGame;

    public Mediator(){};

    public void setGridPanel(GridPanel gridPanel) {
        this.gridPanel = gridPanel;
    }

    public void setGridGame(GridGame gridGame) {
        this.gridGame = gridGame;
    }

    public boolean notifyGridGame(int val, int x, int y){
        return gridGame.addValue(val,x,y);
    }

    public void notifyGridPanel(CellIF[][] table){
        gridPanel.aggiornaValori(table);
    }
}
