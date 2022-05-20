package Model;

import java.util.Scanner;

public class Table implements GridGame, EditableGridGame {
    private static int dimension;
    private static Cell[][] table;

    public Table() {
    }

    public Table(int dimension) { //todo aggiusta
        this.dimension = dimension;
        table = new Cell[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                table[i][j] = new Cell(i, j);
            }
        }
    }

    @Override
    public boolean addValue(int val, int x, int y) {
        return (val >= 1 && val <= dimension && table[x][y].setValue(val) && verify(val, x, y));
    }

    @Override
    public void removeValue(int x, int y) {
        table[x][y].setValue(0);
    }

    @Override
    public void eraseNumbers() {
        clean();
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
    public int getDimension() {
        return dimension;
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
    public void setConstraint(Constraint c, int x, int y) {
        table[x][y].setConstraint(c);
    }

    @Override
    public Constraint getConstraint(int x, int y) {
        return table[x][y].getConstraint();
    }

    @Override
    public EditableCell getCell(int x, int y) {
        return table[x][y];
    }

    @Override
    public void setCell(int value, int x, int y) {
        table[x][y] = new Cell(x, y);
        table[x][y].setValue(value);
    }

    private boolean verify(int val, int x, int y) {
        Cell[] riga = getRow(x);
        Cell[] colonna = getColumn(y);
        for (int i = 0; i < dimension; i++) {
            if (i != y && val == table[x][i].getValue()) return false;
            if (i != x && val == table[i][y].getValue()) return false;
        }
        return true;
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
    public void switchRow(int i, int j) {
        for (int n = 0; n < dimension; n++) {
            int tmp = table[i][n].getValue();
            table[i][n].setValue(table[j][n].getValue());
            table[j][n].setValue(tmp);
        }
    }

    //scambio solo i valori!
    public void switchColumn(int i, int j) {
        for (int n = 0; n < dimension; n++) {
            int tmp = table[n][i].getValue();
            table[n][i].setValue(table[n][j].getValue());
            table[n][j].setValue(tmp);
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

    public String cageString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimension; i++) {
            sb.append("\n");
            for (int j = 0; j < dimension; j++) {
                sb.append(table[i][j].getConstraint().toString() + "\s");
            }
        }
        return sb.toString();
    }

    private class Cell implements EditableCell {
        private int value;
        private int x;
        private int y;
        private Constraint cage;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
            value = 0;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void clean() {
            value = 0;
        }

        public boolean setValue(int value) {
            this.value = value;
            if (cage != null)
                return cage.verify();
            return true;
        }

        public boolean hasConstraint() {
            return cage != null;
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

        public String toString() {
            return "[" + value + "]";
        }

    }


    public static void main(String[] args) {
        System.out.println("KenKen!\n");
        //occorre unificare le due interfacce
        Table t = new Table();
        EditableGridGame egg = t;
        GridGame gg = t;
        Creator c = new ConcreteCreator(egg, 4);
        c.genera();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(egg);
            System.out.println();
            System.out.println(egg.cageString());
            int value = dimension+1;
            while(value>dimension) {
                System.out.print("Valore: ");
                value = sc.nextInt();
            }
            int x = dimension+1;
            while(x>=dimension) {
                System.out.print("Nella x: ");
                x = sc.nextInt();
            }
            int y = dimension+1;
            while(y>=dimension) {
                System.out.print("Nella y: ");
                y = sc.nextInt();
            }
            System.out.println(t.addValue(value,x,y)+"\n");
        }
    }
}