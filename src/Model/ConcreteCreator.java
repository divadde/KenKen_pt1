package Model;

import java.util.List;

public class ConcreteCreator extends Creator{

    private Constraint constraint; //aggiungi possibilità di aggiungere una lista di constraints todo
    private int maxGrandezza;
    private int minGrandezza;


    public ConcreteCreator(EditableGridGame egg, int dimension){
        this.egg=egg;
        this.dimension=dimension;
        egg.setDimension(dimension);
    }

    @Override
    public void inserisciNumeri() {
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                egg.setCell(((i+j)%dimension)+1,i,j);
            }
        }
    }

    @Override
    public void shuffleNumeri() { //shuffle pari alla dimensione
        for(int i=0; i<dimension; i++) {
            int x = (int) (Math.random() * dimension);
            int y = (int) (Math.random() * dimension);
            if (x != y) egg.switchRow(x, y);
            int z = (int) (Math.random() * dimension);
            int k = (int) (Math.random() * dimension);
            if (z != k) egg.switchColumn(z, k);
        }
    }

    @Override //todo trova il bug
    public void aggiungiGabbie() {
        //LO AGGIUNGO QUI MA POI VA RIMOSSO! è SOLO UNA PROVA todo:
        constraint = new Cage();
        while(true){
            EditableCell nextCell = nextPoint();
            if(nextCell==null) break; //abbiamo finito
            else {
                Constraint c = constraint.clone();
                egg.setConstraint(c,nextCell.getX(),nextCell.getY());
                //scegli numero randomico di passi
                int n = chooseCageDimension();
                for(int i=0; i<n; i++){ //todo se non si può raggiungere la dimensione prestabilita?
                    EditableCell lastCell=null;
                    if(nextCell!=null) lastCell=nextCell;
                    else break;
                    //scegli direzioni randomiche
                    Direction d = Direction.DESTRA.chooseRandomly();
                    nextCell = d.doOp(lastCell);
                    if(nextCell!=null) egg.setConstraint(c,nextCell.getX(),nextCell.getY());
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

    private int chooseCageDimension(){
        if (dimension<=4)
            return (int) (Math.random()*dimension);
        return (int) (Math.random()*4);
    }

    private interface Directionable {
        Direction chooseRandomly();
        EditableCell doOp(EditableCell ec);
    }
    private enum Direction implements Directionable{
        DESTRA {
            public EditableCell doOp(EditableCell currPoint) {
                int nextY = currPoint.getY()+1;
                if(nextY<dimension && !(egg.getCell(currPoint.getX(),nextY).hasConstraint()))
                    return egg.getCell(currPoint.getX(),nextY);
                return null;
            }
        }, SINISTRA {
            public EditableCell doOp(EditableCell currPoint) {
                int nextY = currPoint.getY()-1;
                if(nextY>=0 && !(egg.getCell(currPoint.getX(),nextY).hasConstraint()))
                    return egg.getCell(currPoint.getX(),nextY);
                return null;
            }
        }, GIU {
            public EditableCell doOp(EditableCell currPoint) {
                int nextX = currPoint.getX()+1;
                if(nextX<dimension && !(egg.getCell(nextX,currPoint.getY()).hasConstraint()))
                    return egg.getCell(nextX,currPoint.getY());
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

    private EditableCell nextPoint(){
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                if (!(egg.getCell(i,j).hasConstraint()))
                    return egg.getCell(i,j);
            }
        }
        return null;
    }

    @Override
    public void cancellaNumeri() {
        egg.eraseNumbers();
    }

    public class Builder{
        //todo
    }

}
