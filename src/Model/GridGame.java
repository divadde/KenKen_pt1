package Model;

public interface GridGame {

    boolean addValue(int val, int x, int y);

    void removeValue(int x, int y);

    void clean();
}
