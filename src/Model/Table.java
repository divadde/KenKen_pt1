package Model;

import java.util.Scanner;

public class Table implements GridGame {
    private static int dimension;
    private static Cell[][] table;

    public Table() {}

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

    @Override //todo, forse ci sarà da aggiustare qualcosa per la parte grafica
    public boolean addValue(int val, int x, int y) {
        return (val >= 1 && val <= dimension && table[x][y].setValue(val) && verify(val, x, y));
    }

    @Override
    public void removeValue(int x, int y) {
        table[x][y].clean();
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
    private boolean verify(int val, int x, int y) {
        Cell[] row = getRow(x);
        Cell[] column = getColumn(y);
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
    @Override
    public void switchRow(int i, int j) {
        for (int n = 0; n < dimension; n++) {
            int tmp = table[i][n].getValue();
            table[i][n].setValue(table[j][n].getValue());
            table[j][n].setValue(tmp);
        }
    }

    //scambio solo i valori!
    @Override
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

    private class Cell implements CellIF {
        private int value;
        private int x;
        private int y;
        private Constraint cage;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
            value = 0;
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
        }

        @Override
        public boolean setValue(int value) {
            this.value = value;
            if (cage != null)
                return cage.verify();
            return true;
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


    public static void main(String[] args) {
        System.out.println("KenKen!\n");
        GridGame gg = new Table();
        Generator c = new ConcreteGenerator(gg, 4);
        c.generate();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(gg);
            System.out.println();
            System.out.println(gg.constrString());
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
            System.out.println(gg.addValue(value,x,y)+"\n");
        }
    }
}