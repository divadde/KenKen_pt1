package Generating;

import Model.*;

import java.util.LinkedList;
import java.util.List;

public final class ConcreteGenerator extends Generator {

    private Constraint constraint = new Cage(); //aggiungi possibilità di aggiungere una lista di constraints prototipi todo
    //todo aggiungi parametri
    private int maxGrandezza;
    private int minGrandezza;


    public ConcreteGenerator(GridGame egg){
        this.gg=egg;
    }

    /*
    @Override
    public Generator factoryMethod(GridGame gg, int dimension) {
        return new ConcreteGenerator(gg,dimension);
    }
     */

    @Override
    public void insertNumbers() {
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++){
                gg.setCell(((i+j)%gg.getDimension())+1,i,j);
            }
        }
    }

    @Override
    public void shuffleNumbers() { //shuffle pari alla dimensione
        for(int i=0; i<gg.getDimension(); i++) {
            int x = (int) (Math.random() * gg.getDimension());
            int y = (int) (Math.random() * gg.getDimension());
            if (x != y) gg.switchRow(x, y);
            int z = (int) (Math.random() * gg.getDimension());
            int k = (int) (Math.random() * gg.getDimension());
            if (z != k) gg.switchColumn(z, k);
        }
        System.out.println(gg);
    }

    /*
    @Override
    //V1 - problemi? no...
    public void addConstraints() {
        //LO AGGIUNGO QUI MA POI VA RIMOSSO (va creata una lista di prototipi)! è SOLO UNA PROVA todo:
        //constraint = new Cage();
        while(true){
            CellIF nextCell = nextPoint();
            if(nextCell==null) break; //abbiamo finito
            else {
                Constraint c = constraint.clone();
                gg.setConstraint(c,nextCell.getX(),nextCell.getY());
                //scegli numero randomico di passi
                int n = chooseCageDimension();
                for(int i=0; i<n; i++){ //todo se non si può raggiungere la dimensione prestabilita?
                    CellIF lastCell=null;
                    if(nextCell!=null) lastCell=nextCell;
                    else break;
                    //scegli direzioni randomiche
                    Direction d = Direction.DESTRA.chooseRandomly();
                    nextCell = d.doOp(lastCell);
                    if(nextCell!=null) gg.setConstraint(c,nextCell.getX(),nextCell.getY());
                    else{ //fornisco altri 3 tentativi di spostarsi
                        int j=0;
                        while(nextCell!=null || j>=3){
                            d = Direction.DESTRA.chooseRandomly();
                            nextCell = d.doOp(lastCell);
                        }
                    }
                }
                //setta i valori del constraint
                c.setValues();
            }
        }
    }

     */


    /*
    //V2 - problemi, forse no
    @Override
    public void addConstraints() {
        //LO AGGIUNGO QUI MA POI VA RIMOSSO (va creata una lista di prototipi)! è SOLO UNA PROVA todo:
        //constraint = new Cage();
        while(true){
            CellIF nextCell = nextPoint();
            if(nextCell==null) break; //abbiamo finito
            else {
                Constraint c = constraint.clone();
                gg.setConstraint(c,nextCell.getX(),nextCell.getY());
                //scegli numero randomico di passi
                int n = chooseCageDimension();
                for(int i=0; i<n; i++){ //todo se non si può raggiungere la dimensione prestabilita?
                    CellIF lastCell=null;
                    if(nextCell!=null) lastCell=nextCell;
                    else break;
                    //scegli direzioni randomiche
                    Direction d = Direction.DESTRA.chooseRandomly();
                    nextCell = d.doOp(lastCell);
                    if(nextCell!=null) gg.setConstraint(c,nextCell.getX(),nextCell.getY());
                    else{
                        List<Direction> l = addDirections();
                        while(nextCell==null){
                            l.remove(d);
                            if(l.isEmpty()) break;
                            d = l.get(chooseRandomic(l.size()));
                            nextCell = d.doOp(lastCell);
                            if(nextCell!=null) {
                                gg.setConstraint(c,nextCell.getX(),nextCell.getY());
                                break;
                            }
                        }
                    }
                }
                //setta i valori del constraint
                c.setValues();
            }
        }
    }
     */



    @Override
    //V3 - problemi
    public void addConstraints() {
        //LO AGGIUNGO QUI MA POI VA RIMOSSO (va creata una lista di prototipi)! è SOLO UNA PROVA todo:
        //constraint = new Cage();
        while(true){
            CellIF nextCell = nextPoint();
            if(nextCell==null) break; //abbiamo finito
            else {
                Constraint c = constraint.clone();
                gg.setConstraint(c,nextCell.getX(),nextCell.getY());
                //scegli numero randomico di passi
                int n = chooseCageDimension();
                for(int i=0; i<n; i++){ //todo se non si può raggiungere la dimensione prestabilita?
                    CellIF lastCell=null;
                    if(nextCell!=null) lastCell=nextCell;
                    else break;
                    //scegli direzioni randomiche
                    Direction d = Direction.DESTRA.chooseRandomly();
                    nextCell = d.doOp(lastCell);
                    if(nextCell!=null) gg.setConstraint(c,nextCell.getX(),nextCell.getY());
                    else if(i==0){ //in caso di cella singola
                        List<Direction> l = addDirections();
                        while(nextCell==null){
                            l.remove(d);
                            if(l.isEmpty()) { //cella singola ingabbiata, mi faccio inglobare da altri constraint
                                Constraint vicino = searchNear(lastCell).getConstraint();
                                gg.setConstraint(vicino,lastCell.getX(), lastCell.getY());
                                vicino.setValues();
                                break;
                            }
                            d = l.get(chooseRandomic(l.size()));
                            nextCell = d.doOp(lastCell);
                            if(nextCell!=null) {
                                gg.setConstraint(c,nextCell.getX(),nextCell.getY());
                            }
                        }
                    }
                }
                //setta i valori del constraint
                c.setValues();
            }
        }
        System.out.println(gg.constrString());
    }


    private int chooseCageDimension(){
        if (gg.getDimension()<=4) {
            return (int) (Math.random() * gg.getDimension());
        }
        return 2+(int) (Math.random() * 1);
    }

    private CellIF searchNear(CellIF c){
        CellIF ret=null;
        if(c.getY()>0) //CONTROLLO NORD
            ret=gg.getCell(c.getX(),c.getY()-1);
        if(c.getY()<gg.getDimension()-1) //CONTROLLO SUD
            ret=gg.getCell(c.getX(),c.getY()+1);
        if(c.getX()<gg.getDimension()-1) //CONTROLLO EST
            ret=gg.getCell(c.getX()+1,c.getY());
        if(c.getX()>0) //CONTROLLO OVEST
            ret=gg.getCell(c.getX()-1,c.getY());
        return ret;
    }

    private int chooseRandomic(int n){
        return (int) Math.random()*n;
    }
    private List<Direction> addDirections() {
        List<Direction> l = new LinkedList<>();
        l.add(Direction.DESTRA); l.add(Direction.GIU); l.add(Direction.SINISTRA);
        return l;
    }

    private interface Directionable { //todo è necessaria?
        CellIF doOp(CellIF ec);
    }
    private enum Direction implements Directionable{
        DESTRA {
            public CellIF doOp(CellIF currPoint) {
                int nextY = currPoint.getY()+1;
                if(nextY<gg.getDimension() && !(gg.getCell(currPoint.getX(),nextY).hasConstraint()))
                    return gg.getCell(currPoint.getX(),nextY);
                return null;
            }
        }, SINISTRA {
            public CellIF doOp(CellIF currPoint) {
                int nextY = currPoint.getY()-1;
                if(nextY>=0 && !(gg.getCell(currPoint.getX(),nextY).hasConstraint()))
                    return gg.getCell(currPoint.getX(),nextY);
                return null;
            }
        }, GIU {
            public CellIF doOp(CellIF currPoint) {
                int nextX = currPoint.getX()+1;
                if(nextX<gg.getDimension() && !(gg.getCell(nextX,currPoint.getY()).hasConstraint()))
                    return gg.getCell(nextX,currPoint.getY());
                return null;
            }
        };

        public Direction chooseRandomly() {
            int choose = (int) (Math.random() * 3);
            switch (choose) {
                case 0: return Direction.DESTRA;
                case 1: return Direction.SINISTRA;
                default: return Direction.GIU;
            }
        }
    }

    private CellIF nextPoint(){
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++){
                if (!(gg.getCell(i,j).hasConstraint()))
                    return gg.getCell(i,j);
            }
        }
        return null;
    }

    @Override
    public void eraseNumbers() {
        gg.clean();
    }

    public class Builder{
        //todo fai il Builder per gestire i parametri del generatore
    }

}
