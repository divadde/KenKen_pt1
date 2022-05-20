package Model;

import java.util.LinkedList;
import java.util.List;

public class Cage extends Constraint{

    private int id;
    private static int nextId=0;
    private Operation op;
    private List<EditableCell> cells;
    private int result;

    public Cage(){
        cells=new LinkedList<EditableCell>();
    }

    //è utile?
    public Cage (Operation op, int result){
        this.op=op;
        this.result=result;
        cells=new LinkedList<EditableCell>();
    }

    public int getId() {
        return id;
    }

    public void setValues(){
        setRandomOp();
        List<Integer> listOfInteger = getValues(cells);
        result=op.doOp(listOfInteger);
    }
    public void setResult(){
        this.result=result;
    }

    public void setRandomOp(){
        if(cells.size()==2) { //se le celle sono solo due, allora posso dare o la sottrazione o la divisione
            if(cells.get(0).getValue()%cells.get(1).getValue()==0)
                setOp(Operation.ADDIZIONE.getRandomOp()); //brutto da vedere
            else
                setOp(Operation.SOTTRAZIONE);
        }
        else{
            setOp(Operation.ADDIZIONE.getCommutativeRandomOp());
        }
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
        Cage c = (Cage) super.clone();
        c.cells = new LinkedList<>(); //attenzione a questo aspetto
        c.id=nextId++;
        return c;
    }

    @Override
    public boolean verify() {
        return result == op.doOp(getValues(cells));
    }
    private List<Integer> getValues(List<EditableCell> lec){
        List<Integer> ret = new LinkedList<>();
        for(EditableCell ec: lec) ret.add(ec.getValue());
        ret.sort((Integer i1, Integer i2)->i2.compareTo(i1)); //ora dovrebbe essere corretto.
        return ret;
    }

    public String toString(){
        if(op.equals(Operation.ADDIZIONE)){
            return "["+result+"+|"+id+"|]";
        }
        if(op.equals(Operation.SOTTRAZIONE)){
            return "["+result+"-|"+id+"|]";
        }
        if(op.equals(Operation.MOLTIPLICAZIONE)){
            return "["+result+"x|"+id+"|]";
        }
        return "["+result+"/|"+id+"|]";
    }
}
