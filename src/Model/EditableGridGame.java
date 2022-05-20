package Model;

public interface EditableGridGame {
    void setConstraint(Constraint c, int x, int y);
    Constraint getConstraint(int x, int y);
    EditableCell getCell(int x, int y);
    void setCell(int value, int x, int y);
    int getDimension();
    void setDimension(int n);
}
