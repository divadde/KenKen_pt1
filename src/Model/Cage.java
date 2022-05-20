package Model;

import java.util.LinkedList;
import java.util.List;

public class Cage extends Constraint{

    private Operation op;
    private List<EditableCell> cells;
    private int result;

    public Cage (Operation op, int result){
        this.op=op;
        this.result=result;
        cells=new LinkedList<EditableCell>();
    }

    public void setOp(Operation op){
        this.op=op;
    }
    public Operation getOp(){
        return op;
    }
    @Override
    public void addCell(EditableCell ec){
        cells.add(ec);
    }

    @Override
    public Constraint clone() {
        return null;
    }

    @Override
    public boolean verify() {
        return result == op.doOp(getValues(cells));
    }
    private List<Integer> getValues(List<EditableCell> lec){
        List<Integer> ret = new LinkedList<>();
        for(EditableCell ec: lec) ret.add(ec.getValue());
        ret.sort((Integer i1, Integer i2)->i1.compareTo(i2)); //todo verifica correttezza
        return ret;
    }

    public String toString(){
        if(op.equals(Operation.ADDIZIONE)){
            return "["+result+"+]";
        }
        if(op.equals(Operation.SOTTRAZIONE)){
            return "["+result+"-]";
        }
        if(op.equals(Operation.MOLTIPLICAZIONE)){
            return "["+result+"x]";
        }
        return "["+result+"/]";
    }
}
