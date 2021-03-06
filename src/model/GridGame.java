package model;

import backtracking.*;
import generating.*;

import java.io.Serializable;
import java.util.List;

public interface GridGame extends Serializable {

    //interazione col gioco
    void addValue(int val, int x, int y);
    void removeValue(int x, int y);
    void clean();

    //modifica griglia
    void setDimension(int n);
    int getDimension();
    void setConstraint(Constraint c, int x, int y);
    Constraint getConstraint(int x, int y);
    void setCell(int value, int x, int y);
    CellIF getCell(int x, int y);
    void changeReferenceTable(CellIF[][] table);
    CellIF[][] getReferenceTable();

    //controlli
    boolean isLegal(int val, int x, int y);
    boolean isCompleted();
    MementoTable createMemento();
    void setMemento(MementoTable memento);

    //util
    void switchRow(int i, int j);
    void switchColumn(int i, int j);
    String constrString();
    List<Constraint> listOfConstraint();
    Generator getGenerator();
    Backtracking getBacktracking();
    
}
