package Model;

public interface EditableCell {
    boolean setValue(int value);
    int getValue();
    void setX(int x);
    int getX();
    int getY();
    void setY(int y);
    boolean hasConstraint();
    Constraint getConstraint();
    void setConstraint(Constraint c);
}
