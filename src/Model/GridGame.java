package Model;

import Backtracking.Backtracking;
import Backtracking.Solver;
import Generating.ConcreteGenerator;
import Generating.Generator;
import GraphicTest.Mediator;

import java.util.List;

public interface GridGame {

    boolean addValue(int val, int x, int y);
    boolean isLegal(int val, int x, int y);
    boolean isCompleted();
    void removeValue(int x, int y);
    void clean();

    void setConstraint(Constraint c, int x, int y);
    Constraint getConstraint(int x, int y);
    void setCell(int value, int x, int y);
    CellIF getCell(int x, int y);
    CellIF[][] getTable();
    void setTable(CellIF[][] table);
    void setDimension(int n);
    int getDimension();
    void switchRow(int i, int j);
    void switchColumn(int i, int j);
    String constrString();
    List<Constraint> listOfConstraint();

    default Generator getGenerator(){
        return new ConcreteGenerator(this,getDimension());
    }

    default Backtracking getBacktracking() {
        return Solver.getInstance(this);
    }

    void setMediator(Mediator mediator);
    Mediator getMediator();
    
}
