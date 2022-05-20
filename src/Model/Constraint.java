package Model;

public abstract class Constraint implements Cloneable {
    abstract void addCell(EditableCell ec);
    abstract void setValues();
    @Override
    public Constraint clone() {
        try {
            return (Constraint) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
    abstract boolean verify();
}
