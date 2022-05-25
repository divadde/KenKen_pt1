package GraphicTest;

import Model.Table;
import javax.swing.*;

public class Test {

    public static void main(String[] args){

        Table t = new Table();
        t.setDimension(6);
        JFrame f = new WindowGame("KenKen",t);

    }

}
