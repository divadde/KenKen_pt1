package Model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public final class KenKen implements GridGame, Serializable {
    private int dimension;
    private Cell[][] table;

    private static final long serialVersionUID = 9177631848186263965L;

    public KenKen() {}

    /* NON USATO
    public Table(int dimension) {
        this.dimension = dimension;
        table = new Cell[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                table[i][j] = new Cell(i, j);
            }
        }
    }
    */


    //copia profonda
    @Override
    public CellIF[][] getTable(){
        Cell[][] ret = new Cell[dimension][dimension];
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++)
                ret[i][j] = new Cell(table[i][j]);
        }
        return ret;
    }

    @Override
    public CellIF[][] getReferenceTable(){
        return table;
    }
    @Override
    public void changeReferenceTable(CellIF[][] table){
        this.table=(Cell[][]) table;
    }

    @Override
    public void setTable(CellIF[][] table){
        clean();
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                addValue(table[i][j].getValue(),table[i][j].getX(),table[i][j].getY());
            }
        }
    }

    @Override //todo, forse ci sarà da aggiustare qualcosa per la parte grafica
    public boolean addValue(int val, int x, int y) {
        table[x][y].setValue(val);
        //System.out.println(this);
        return table[x][y].getState();
    }

    private boolean verifyNumber(int val){
        return val>=1 && val<=dimension;
    }

    @Override
    public void removeValue(int x, int y) {
        table[x][y].clean();
    }

    @Override
    public boolean isLegal(int val, int x, int y){
        boolean ret = addValue(val,x,y);
        removeValue(x,y);
        return ret;
    }

    @Override
    public boolean isCompleted(){
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++) {
                if (!table[i][j].getState())
                    return false;
            }
        }
        return true;
    }

    @Override
    public void clean() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                table[i][j].clean();
            }
        }
    }

    @Override
    public void setConstraint(Constraint c, int x, int y) {
        table[x][y].setConstraint(c);
    }

    @Override
    public Constraint getConstraint(int x, int y) {
        return table[x][y].getConstraint();
    }

    @Override
    public void setCell(int value, int x, int y) {
        table[x][y] = new Cell(x, y);
        table[x][y].setValue(value);
    }

    @Override
    public CellIF getCell(int x, int y) {
        return table[x][y];
    }

    @Override
    public void setDimension(int n) {
        this.dimension = n;
        table = new Cell[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                table[i][j] = new Cell(i, j);
            }
        }
    }

    @Override
    public int getDimension() {
        return dimension;
    }

    //todo forse sarà utile se vogliamo introdurre un oggetto Constraint (per esempio un oggetto che regola la presenza di ripetizioni all'interno di una riga)
    private List<Cell> verify(int val, int x, int y) {
        List<Cell> ret = new LinkedList<>();
        Cell[] row = getRow(x);
        Cell[] column = getColumn(y);
        for (int i = 0; i < dimension; i++) {
            if (i != y && val == table[x][i].getValue()) {
                ret.add(table[x][i]);
            }
            if (i != x && val == table[i][y].getValue()) {
                ret.add(table[i][y]);
            }
        }
        return ret;
    }

    private Cell[] getRow(int x) {
        return table[x];
    }

    private Cell[] getColumn(int x) {
        Cell[] ret = new Cell[dimension];
        for (int i = 0; i < dimension; i++)
            ret[i] = table[i][x];
        return ret;
    }

    //scambio solo i valori!
    @Override
    public void switchRow(int i, int j) {
        for (int n = 0; n < dimension; n++) {
            table[i][n].switchValue(table[j][n]);
        }
    }

    //scambio solo i valori!
    @Override
    public void switchColumn(int i, int j) {
        for (int n = 0; n < dimension; n++) {
            table[n][i].switchValue(table[n][j]);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimension; i++) {
            sb.append("\n");
            for (int j = 0; j < dimension; j++) {
                sb.append(table[i][j].toString() + "\s");
            }
        }
        return sb.toString();
    }

    public String constrString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimension; i++) {
            sb.append("\n");
            for (int j = 0; j < dimension; j++) {
                sb.append(table[i][j].getConstraint().toString() + "\s");
            }
        }
        return sb.toString();
    }

    public List<Constraint> listOfConstraint(){
        List<Constraint> list = new LinkedList<>();
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                if(!(list.contains(getConstraint(i,j))))
                    list.add(getConstraint(i,j));
            }
        }
        return list;
    }

    private class Cell implements CellIF, Serializable {
        private int value;
        private int x;
        private int y;
        private Constraint cage;
        private boolean stateKenKen;
        private boolean stateCage;
        private List<Cell> inContrast;

        private static final long serialVersionUID = 4177631348182261145L;

        public Cell(int x, int y) {
            inContrast=new LinkedList<>();
            this.x = x;
            this.y = y;
            value = 0;
            stateKenKen=false;
            stateCage=false;
        }

        public void switchValue(Cell c){
            int tmp=this.value;
            this.value=c.getValue();
            c.setValueNoControl(tmp);
        }
        private void setValueNoControl(int x){
            value=x;
        }

        //costruttore per copia, è sufficiente memorizzare posizione e valore //todo verifica correttezza
        public Cell (Cell c){
            this.x = c.getX();
            this.y = c.getY();
            this.value = c.getValue();
        }

        public void addInContrast(Cell c){
            inContrast.add(c);
        }
        public void removeInContrast(Cell c){
            inContrast.remove(c);
        }

        public boolean getState(){
            return stateKenKen && stateCage;
        }

        public void setKenKenState(boolean state){
            stateKenKen=state;
        }
        public void setCageState(boolean state){
            stateCage=state;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public void setX(int x) {
            this.x = x;
        }

        @Override
        public void setY(int y) {
            this.y = y;
        }

        @Override
        public void clean() {
            value = 0;
            stateKenKen=false;
            stateCage=false;
        }

        @Override
        public boolean setValue(int value) {
            this.value = value;
            List<Cell> ver = verify(value,x,y);
            for(Cell c : ver){
                if(!(inContrast.contains(c))){
                    c.setKenKenState(false);
                    c.addInContrast(this);
                    addInContrast(c);
                }
            }
            List<Cell> daEliminare = new LinkedList<Cell>();
            for(Cell c: inContrast){
                if(!(ver.contains(c))){
                    c.removeInContrast(this);
                    c.setKenKenState(c.inContrast.isEmpty());
                    daEliminare.add(c);
                }
            }
            inContrast.removeAll(daEliminare);
            if(!verifyNumber(value))
                this.value=0;
            stateKenKen=verifyNumber(value) && inContrast.isEmpty();
            stateCage=true;
            if (cage != null)
                stateCage=cage.verify();
            return stateKenKen && stateCage;
        }

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public boolean hasConstraint() {
            return cage != null;
        }

        @Override
        public void setConstraint(Constraint cage) {
            this.cage = cage;
            this.cage.addCell(this);
        }

        @Override
        public Constraint getConstraint() {
            return cage;
        }

        public String toString() {
            return "[" + value + "]";
        }

    }
}