package Backtracking;

import Model.CellIF;
import Model.GridGame;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

//SINGLETON
public final class Solver extends Backtracking<CellIF,Integer,CellIF[][]>{
    private static Solver INSTANCE=null;
    private int sol;
    private GridGame gg;
    private List<CellIF[][]> completeTables;
    private ListIterator<CellIF[][]> lit;
    private int maxSol;

    private Solver(GridGame gg){
        maxSol=10; //assegnate di default
        this.gg=gg;
        completeTables=new LinkedList<>();
        lit=completeTables.listIterator();
        choosingPoints=computeChoosingPoints();
        System.out.println(choosingPoints.size());
    }

    public static synchronized Solver getInstance(GridGame gg){
        if(INSTANCE==null){
            INSTANCE=new Solver(gg);
        }
        return INSTANCE;
    }

    @Override
    protected boolean foundSolution(CellIF cell) {
        return gg.isCompleted();
    }

    @Override
    protected boolean admissible(CellIF cell, Integer integer) {
        return gg.isLegal(integer, cell.getX(), cell.getY());
    }

    @Override
    protected void submit(CellIF cell, Integer integer) {
        gg.addValue(integer, cell.getX(), cell.getY());
        System.out.println("Aggiunto a "+cell.getX()+","+cell.getY()+" il valore "+integer);
    }

    @Override
    protected void remove(CellIF cellIF, Integer integer) {
        gg.removeValue(cellIF.getX(), cellIF.getY());
    }

    @Override
    protected void submitSolution(CellIF cell) {
        completeTables.add(gg.getTable());
        sol++;
        System.out.println("Soluzione "+sol+" trovata!");
    }

    @Override
    protected List<CellIF> computeChoosingPoints() {
        LinkedList<CellIF> celle = new LinkedList<>();
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++){
                celle.addLast(gg.getCell(i,j));
            }
        }
        return celle;
    }

    @Override
    protected Collection<Integer> admissibleChoices(CellIF cell) {
        LinkedList<Integer> ret = new LinkedList<>();
        for(int i=0; i<gg.getDimension(); i++){
            System.out.println("Cella incriminata: "+cell.getX()+","+cell.getY());
            if(admissible(cell,i+1)) {
                ret.addLast(i + 1);
            }
        }
        System.out.println("Scelte ammissibili per"+cell.getX()+","+cell.getY()+" :");
        System.out.println(ret);
        return ret;
    }

    @Override
    public void solve() {
        completeTables=new LinkedList<>();
        sol=0;
        gg.clean();
        choosingPoints=computeChoosingPoints();
        execute(choosingPoints.get(0));
        System.out.println("Soluzioni trovate: "+sol);
        /*
        for(CellIF[][] soluzione: completeTables){
            gg.setTable(soluzione);
            System.out.println(gg.toString());
        }
        */
        System.out.println(completeTables.size());
        lit=completeTables.listIterator();
    }


    @Override
    public CellIF[][] nextSol() {
        if(lit.hasNext())
            return lit.next();
        return null;
    }

    @Override
    public CellIF[][] prevSol() {
        if(lit.hasPrevious())
            return lit.previous();
        return null;
    }

    @Override
    protected boolean stop(CellIF c){
        return !(numSol()<maxSol);
    }

    @Override
    public int numSol(){
        return completeTables.size();
    }

    @Override
    public void setMaxSol(int maxSol){
        this.maxSol=maxSol;
        System.out.println("Il numero massimo di soluzioni è stato impostato a: "+maxSol);
    }


}
