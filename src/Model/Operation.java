package Model;

import java.util.List;

public enum Operation implements Operable {
    //parto dal presupposto che ricevo la lista con elementi ordinati dal più grande al più piccolo
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
            for(Integer i: l)
                result*=i;
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

}
