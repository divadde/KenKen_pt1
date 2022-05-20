package Model;

public class Table implements GridGame, EditableGridGame {
    private static int dimension;
    private static Cell[][] table;

    public Table(int dimension){ //todo aggiusta
        this.dimension=dimension;
        table = new Cell[dimension][dimension];
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                table[i][j]=new Cell(i,j);
            }
        }
    }

    @Override
    public boolean addValue(int val, int x, int y){
        return (val>=1 && val<=dimension && table[x][y].setValue(val) && verify(val,x,y));
    }
    @Override
    public void removeValue(int x, int y) {
        table[x][y].setValue(0);
    }
    @Override
    public void clean() {
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                table[i][j].setValue(0);
            }
        }
    }

    @Override
    public int getDimension() {
        return dimension;
    }

    @Override
    public void setDimension(int n) {
        this.dimension=n;
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
    public EditableCell getCell(int x, int y){ return table[x][y]; }

    @Override
    public void setCell(int value, int x, int y) {
        table[x][y]=new Cell(x,y);
        table[x][y].setValue(value);
    }

    private boolean verify(int val, int x, int y){
        Cell[] riga = getRow(x);
        Cell[] colonna = getColumn(y);
        for(int i=0; i<dimension; i++) {
            if (i != y && val == table[x][i].getValue()) return false;
            if (i != x && val == table[i][y].getValue()) return false;
        }
        return true;
    }
    private Cell[] getRow(int x){
        return table[x];
    }
    private Cell[] getColumn(int x){
        Cell[] ret = new Cell[dimension];
        for(int i=0; i<dimension; i++)
            ret[i] = table[i][x];
        return ret;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<dimension; i++){
            sb.append("\n");
            for(int j=0; j<dimension; j++){
                sb.append(table[i][j].toString()+"\s");
            }
        }
        return sb.toString();
    }

    public String cageString(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<dimension; i++){
            sb.append("\n");
            for(int j=0; j<dimension; j++){
                sb.append(table[i][j].getConstraint().toString()+"\s");
            }
        }
        return sb.toString();
    }

    private class Cell implements EditableCell{
        private int value;
        private final int x;
        private final int y;
        private Constraint cage;

        public Cell(int x, int y){
            this.x=x;
            this.y=y;
            value=0;
        }

        public boolean setValue(int value) {
            this.value = value;
            return cage.verify();
        }

        public int getValue() {
            return value;
        }

        public void setConstraint(Constraint cage) {
            this.cage = cage;
            this.cage.addCell(this);
        }
        public Constraint getConstraint() {
            return cage;
        }

        public String toString(){
            return "["+value+"]";
        }

    }


    public static void main(String[] args){
        //prova
    }

}
