package Model;

import java.util.LinkedList;
import java.util.List;

public class Cage extends Constraint{

    private int id;
    private static int nextId=0;
    private Operation op;
    private List<CellIF> cells;
    private int result;

    private interface Operable {
        int doOp(List<Integer> l);
    }

    private enum Operation implements Operable {
        //Assunzione: parto dal presupposto che ricevo la lista con elementi ordinati dal più grande al più piccolo
        //todo modifica il fatto che da parte dell'utente si possa scegliere la precedenza
        ADDIZIONE{
            @Override
            public int doOp(List<Integer> l){
                int result=0;
                for(Integer i: l)
                    result+=i;
                return result;
            }
        },SOTTRAZIONE{
            @Override
            public int doOp(List<Integer> l){
                int result=0;
                boolean first=true;
                for(Integer i: l){
                    if(first) { result=i; first=false; }
                    else result-=i;
                }
                return result;
            }
        },MOLTIPLICAZIONE{
            @Override
            public int doOp(List<Integer> l){
                int result=0;
                boolean first=true;
                for(Integer i: l){
                    if(first) { result=i; first=false; }
                    else result*=i;
                }
                return result;
            }
        },DIVISIONE {
            @Override
            public int doOp(List<Integer> l) {
                int result = 0;
                boolean first = true;
                for (Integer i : l) {
                    if (first) {
                        result = i;
                        first = false;
                    } else result /= i;
                }
                return result;
            }
        };

        public Operation getRandomOp(){
            int choose = (int) (Math.random()*4);
            switch(choose){
                case 0: return Operation.ADDIZIONE;
                case 1: return Operation.SOTTRAZIONE;
                case 2: return Operation.MOLTIPLICAZIONE;
                default: return Operation.DIVISIONE;
            }
        }
        public Operation getCommutativeRandomOp(){
            int choose = (int) (Math.random()*2);
            switch(choose){
                case 0: return Operation.ADDIZIONE;
                default: return Operation.MOLTIPLICAZIONE;
            }
        }

    }

    public Cage(){
        cells=new LinkedList<CellIF>();
    }

    /*
    public Cage (Operation op, int result){
        this.op=op;
        this.result=result;
        cells=new LinkedList<CellIF>();
    }
    */

    @Override
    public void setValues(){
        setRandomOp();
        List<Integer> listOfInteger = getValues(cells);
        result=op.doOp(listOfInteger);
    }

    //todo, si potrebbe migliorare la scelta dell'operazione
    private void setRandomOp(){
        if(cells.size()==2) { //se le celle sono solo due, allora posso dare anche la sottrazione o la divisione
            if(cells.get(0).getValue()%cells.get(1).getValue()==0)
                op=Operation.ADDIZIONE.getRandomOp(); //brutto da vedere
            else
                op=Operation.SOTTRAZIONE;
        }
        else{
            op=Operation.ADDIZIONE.getCommutativeRandomOp();
        }
    }

    @Override
    public void addCell(CellIF ec){
        cells.add(ec);
    }

    @Override
    public Constraint clone() {
        Cage c = (Cage) super.clone();
        c.cells = new LinkedList<>();
        c.id=nextId++;
        return c;
    }

    @Override
    public boolean verify() {
        return !arePositive(getValues(cells)) || result == op.doOp(getValues(cells));
    }

    private boolean arePositive(List<Integer> l){
        for(Integer i: l){
            if(i<1) return false;
        }
        return true;
    }

    //Assunzione: i valori sono ordinati in maniera decrescente
    private List<Integer> getValues(List<CellIF> lec){
        List<Integer> ret = new LinkedList<>();
        for(CellIF ec: lec) ret.add(ec.getValue());
        ret.sort((Integer i1, Integer i2)->i2.compareTo(i1));
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
