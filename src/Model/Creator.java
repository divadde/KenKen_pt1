package Model;

//template method
public abstract class Creator {
    //todo
    public void genera(){
        inserisciNumeri();
        shuffleNumeri();
        aggiungiGabbie();
        cancellaNumeri();
    }

    public abstract void inserisciNumeri();
    public abstract void shuffleNumeri();
    public abstract void aggiungiGabbie();
    public abstract void cancellaNumeri();

}
