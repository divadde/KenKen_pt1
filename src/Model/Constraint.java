package Model;

import java.util.Objects;

public abstract class Constraint implements Cloneable {
    protected int id;
    protected static int nextId=0;

    public abstract void addCell(CellIF ec);
    public abstract void setValues();
    @Override
    public Constraint clone() {
        try {
            id=nextId++;
            return (Constraint) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public abstract boolean verify();

    public int getId(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Constraint that = (Constraint) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
