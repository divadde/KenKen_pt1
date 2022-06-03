package generating;

import model.GridGame;

//template method
public abstract class Generator {

    protected static GridGame gg;

    public void generate(){
        insertNumbers();
        shuffleNumbers();
        addConstraints();
        eraseNumbers();
    }

//    public abstract Generator factoryMethod(GridGame gg, int dimension);
    public abstract void insertNumbers();
    public abstract void shuffleNumbers();
    public abstract void addConstraints();
    public abstract void eraseNumbers();

}
