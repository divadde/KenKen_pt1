package Backtracking;

import Model.CellIF;
import Model.GridGame;

import java.util.*;

public final class Solver extends Backtracking<CellIF,Integer>{
    private static Solver INSTANCE=null;
    private int sol;
    private GridGame gg;
    private List<CellIF[][]> tabelleComplete;
    private ListIterator<CellIF[][]> lit;
    private int maxSol;

    private Solver(GridGame gg){
        maxSol=10; //assegnate di default
        this.gg=gg;
        tabelleComplete=new LinkedList<>();
        lit=tabelleComplete.listIterator();
    }

    public static synchronized Solver getInstance(GridGame gg){
        if(INSTANCE==null){
            INSTANCE=new Solver(gg);
        }
        return INSTANCE;
    }

    @Override
    protected boolean esisteSoluzione(CellIF cell) {
        return gg.isCompleted();
    }

    @Override
    protected boolean assegnabile(CellIF cell, Integer integer) {
        return gg.isLegal(integer, cell.getX(), cell.getY());
    }

    @Override
    protected void assegna(CellIF cell, Integer integer) {
        gg.addValue(integer, cell.getX(), cell.getY());
        System.out.println("Aggiunto a "+cell.getX()+","+cell.getY()+" il valore "+integer);
    }

    @Override
    protected void deassegna(CellIF cellIF, Integer integer) {
        gg.removeValue(cellIF.getX(), cellIF.getY());
    }

    @Override
    protected void scriviSoluzione(CellIF cell) {
        tabelleComplete.add(gg.getTable());
        sol++;
        System.out.println("Soluzione "+sol+" trovata!");
    }

    @Override
    protected List<CellIF> puntiDiScelta() {
        LinkedList<CellIF> celle = new LinkedList<>();
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++){
                celle.addLast(gg.getCell(i,j));
            }
        }
        return celle;
    }

    @Override
    protected Collection<Integer> scelte(CellIF cell) {
        LinkedList<Integer> possScelte = new LinkedList<>();
        for(int i=0; i<gg.getDimension(); i++){
            possScelte.addLast(i+1);
        }
        return possScelte;
    }

    @Override
    public void risolvi() {
        tabelleComplete=new LinkedList<>();
        sol=0;
        gg.clean();
        //lit=tabelleComplete.listIterator();
        tentativo(puntiDiScelta(),puntiDiScelta().get(0));
        System.out.println("Soluzioni trovate: "+sol);
        for(CellIF[][] soluzione: tabelleComplete){
            gg.setTable(soluzione);
            System.out.println(gg.toString());
        }
        System.out.println(tabelleComplete.size());
        lit=tabelleComplete.listIterator();
    }


    @Override
    public CellIF[][] nextSol() {
        //lit=tabelleComplete.listIterator();
        if(lit.hasNext())
            return lit.next();
        return null;
    }

    @Override
    public CellIF[][] prevSol() {
        //lit=tabelleComplete.listIterator();
        if(lit.hasPrevious())
            return lit.previous();
        return null;
    }

    @Override
    protected boolean ultimaSoluzione(CellIF c){
        return !(numSol()<maxSol);
    }

    @Override
    public int numSol(){
        return tabelleComplete.size();
    }

    @Override
    public void setMaxSol(int maxSol){
        this.maxSol=maxSol;
        System.out.println("Il numero massimo di soluzioni è stato impostato a: "+maxSol);
    }


}
