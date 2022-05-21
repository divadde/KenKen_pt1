package Model;

public class ConcreteGenerator extends Generator {

    private Constraint constraint = new Cage(); //aggiungi possibilità di aggiungere una lista di constraints todo
    //todo aggiungi parametri
    private int maxGrandezza;
    private int minGrandezza;


    public ConcreteGenerator(GridGame egg, int dimension){
        this.gg=egg;
        this.dimension=dimension;
        egg.setDimension(dimension);
    }

    @Override
    public void insertNumbers() {
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                gg.setCell(((i+j)%dimension)+1,i,j);
            }
        }
    }

    @Override
    public void shuffleNumbers() { //shuffle pari alla dimensione
        for(int i=0; i<dimension; i++) {
            int x = (int) (Math.random() * dimension);
            int y = (int) (Math.random() * dimension);
            if (x != y) gg.switchRow(x, y);
            int z = (int) (Math.random() * dimension);
            int k = (int) (Math.random() * dimension);
            if (z != k) gg.switchColumn(z, k);
        }
    }

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

    private interface Directionable { //todo è necessaria?
        CellIF doOp(CellIF ec);
    }
    private enum Direction implements Directionable{
        DESTRA {
            public CellIF doOp(CellIF currPoint) {
                int nextY = currPoint.getY()+1;
                if(nextY<dimension && !(gg.getCell(currPoint.getX(),nextY).hasConstraint()))
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
                if(nextX<dimension && !(gg.getCell(nextX,currPoint.getY()).hasConstraint()))
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
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
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
