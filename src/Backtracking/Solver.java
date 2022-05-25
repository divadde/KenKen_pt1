package Backtracking;

import GraphicTest.GridPanel;
import Model.CellIF;
import Model.GridGame;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import java.awt.event.ActionEvent;

public class Solver extends Backtracking<int[],Integer>{
    private GridGame gg;
    private GridPanel gp;

    public Solver(GridGame gg, GridPanel gp){
        this.gg=gg;
        this.gp=gp;
    }


    @Override
    protected boolean esisteSoluzione(int[] ints) {
        return gg.isCompleted();
    }

    @Override
    protected boolean assegnabile(int[] ints, Integer integer) {
        return gg.isLegal(integer, ints[0], ints[1]);
    }

    @Override
    protected void assegna(int[] ps, Integer integer) {
        gg.addValue(integer, ps[0], ps[1]);
    }

    @Override
    protected void deassegna(int[] ps, Integer integer) {
        gg.removeValue(ps[0], ps[1]);
    }

    @Override
    protected void scriviSoluzione(int[] ints) {
        System.out.println("Soluzione trovata!");
        for(int i=0; i< gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++){
                CellIF cell = gg.getCell(i,j);
                gp.getGameCell(i,j).setText(Integer.toString(cell.getValue()));
                gp.getGameCell(i,j).actionPerformed(new ActionEvent(new Object(),1,"boh"));
            }
        }
    }

    @Override
    protected List<int[]> puntiDiScelta() {
        LinkedList<int[]> coordinate = new LinkedList<>();
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++){
                int[] coord = new int[2];
                coord[0]=i; coord[1]=j;
                coordinate.addLast(coord);
            }
        }
        return coordinate;
    }

    @Override
    protected Collection<Integer> scelte(int[] p) {
        LinkedList<Integer> possScelte = new LinkedList<>();
        for(int i=0; i<gg.getDimension(); i++){
            possScelte.addLast(i+1);
        }
        return possScelte;
    }

    @Override
    public void risolvi() {
        tentativo(puntiDiScelta(),puntiDiScelta().get(0));
    }

}
