package Model;

public abstract class Constraint implements Cloneable {
    abstract void addCell(EditableCell ec);
    public abstract Constraint clone(); //verifica correttezza sul metodo clone
    abstract boolean verify();
}
