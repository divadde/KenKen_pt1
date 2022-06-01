package Model;


public class Settings {
    private int dimension;
    private int maxSol;

    public Settings(int dimension, int maxSol){
        this.dimension=dimension;
        this.maxSol=maxSol;
    }

    public void setDimension(int dimension){
        this.dimension=dimension;
    }

    public void setMaxSol(int maxSol){
        this.maxSol=maxSol;
    }

    public int getDimension() {
        return dimension;
    }

    public int getMaxSol() {
        return maxSol;
    }
}
