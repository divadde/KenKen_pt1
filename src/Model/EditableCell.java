package Model;

public interface EditableCell {
    boolean setValue(int value);
    int getValue();
    Constraint getConstraint();
    void setConstraint(Constraint c);
}
