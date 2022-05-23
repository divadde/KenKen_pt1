package Backtracking;

import Model.GridGame;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Solver<P,S> extends Backtracking<P,S>{
    private GridGame gg;

    public Solver(GridGame gg){
        this.gg=gg;
    }

    @Override
    protected boolean assegnabile(P p, S s) {
        int[] coord = (int[]) p;
        return gg.isLegal((Integer) s, coord[0], coord[1]);
    }

    @Override
    protected void assegna(P ps, S s) {
        int[] coord = (int[]) ps;
        gg.addValue((Integer) s, coord[0], coord[1]);
    }

    @Override
    protected void deassegna(P ps, S s) {
        int[] coord = (int[]) ps;
        gg.removeValue(coord[0], coord[1]);
    }

    @Override
    protected void scriviSoluzione(P p) {

    }

    @Override
    protected boolean esisteSoluzione(P p) {
        return gg.isCompleted();
    }

    @Override
    protected List<P> puntiDiScelta() {
        LinkedList<int[]> coordinate = new LinkedList<>();
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++){
                int[] coord = new int[2];
                coord[0]=i; coord[0]=j;
                coordinate.addLast(coord);
            }
        }
        return (List<P>) coordinate;
    }

    @Override
    protected Collection<S> scelte(P p) {
        LinkedList<Integer> possScelte = new LinkedList<>();
        for(int i=0; i<gg.getDimension(); i++){
            possScelte.addLast(i+1);
        }
        return (Collection<S>) possScelte;
    }

    @Override
    public void risolvi() {
        tentativo(puntiDiScelta(),puntiDiScelta().get(0));
    }

}
