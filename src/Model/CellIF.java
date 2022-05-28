package Model;

public interface CellIF {
    boolean setValue(int value);
    void setCageState(boolean state);
    boolean getState();
    int getValue();
    void setX(int x);
    int getX();
    int getY();
    void setY(int y);
    boolean hasConstraint();
    Constraint getConstraint();
    void setConstraint(Constraint c);
    void clean();
}
