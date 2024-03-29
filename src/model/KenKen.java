package model;

import backtracking.*;
import generating.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public final class KenKen implements GridGame, Serializable {
    private static KenKen INSTANCE = null;
    private static int dimension;
    private static Cell[][] table;

    private static final long serialVersionUID = 9177631848186263965L;

    private KenKen() {}

    public static synchronized KenKen getInstance(){
        if(INSTANCE==null)
            INSTANCE=new KenKen();
        return INSTANCE;
    }

    //OK
    public MementoTable createMemento(){
        return new MementoTable(getTable());
    }
    public void setMemento(MementoTable memento) {
        setTable(memento.getTable());
    }

    //copia profonda, usato dal memento per memorizzare le soluzioni.
    private CellIF[][] getTable(){
        Cell[][] ret = new Cell[dimension][dimension];
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++)
                ret[i][j] = new Cell(table[i][j]);
        }
        return ret;
    }
    //usato per impostare lo stato dal memento
    private void setTable(CellIF[][] table){
        clean();
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                addValue(table[i][j].getValue(),table[i][j].getX(),table[i][j].getY());
            }
        }
    }


    //usato per memorizzare l'informazione (Serializzazione)
    @Override
    public CellIF[][] getReferenceTable(){
        return table;
    }
    //usato per cambiare riferimento alla tabella (Serializzazione)
    @Override
    public void changeReferenceTable(CellIF[][] table){
        this.table=(Cell[][]) table;
    }


    @Override
    public void addValue(int val, int x, int y) {
        table[x][y].setValue(val);
        //System.out.println(this);
    }
    @Override
    public void removeValue(int x, int y) {
        table[x][y].clean();
    }
    @Override
    public boolean isLegal(int val, int x, int y){
        addValue(val,x,y);
        boolean ret = table[x][y].getState();
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

    //usato dalla cella per verificare correttezza inserimento
    private boolean verifyNumber(int val){
        return val>=1 && val<=dimension;
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

    @Override
    public Generator getGenerator() {
        return KenKenGenerator.getInstance(this);
    }

    @Override
    public Backtracking getBacktracking() {
        return KenKenSolver.getInstance(this);
    }


    private class Cell implements CellIF, Serializable {
        private int value;
        private int x;
        private int y;
        private Constraint cage;
        private boolean stateKenKen; //state rules
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

        public Cell (Cell c){
            this.x = c.getX();
            this.y = c.getY();
            this.value = c.getValue();
        }

        public void switchValue(Cell c){
            int tmp=this.value;
            this.value=c.getValue();
            c.setValueNoControl(tmp);
        }
        //per distinguersi dall'aggiunta dei valori con controllo, metodo usato in fase di generazione
        private void setValueNoControl(int x){
            value=x;
        }

        private void addInContrast(Cell c){
            inContrast.add(c);
        }
        private void removeInContrast(Cell c){inContrast.remove(c); }

        @Override
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
            List<Cell> daEliminare = new LinkedList<Cell>();
            for(Cell c: inContrast){
                c.removeInContrast(this);
                daEliminare.add(c);
            }
            for(Cell c: daEliminare) {
                c.setKenKenState(c.inContrast.isEmpty());
            }
            inContrast.removeAll(daEliminare);
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