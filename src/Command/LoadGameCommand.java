package Command;

import Model.GridGame;

import javax.swing.*;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadGameCommand implements Command{
    GridGame gg;

    public LoadGameCommand(GridGame gg){
        this.gg=gg;
    }

    @Override
    public void execute() {
        String nomeFile = null;
        String absolutePath = null;
        JFileChooser jfc = new JFileChooser();
        int val = jfc.showOpenDialog(null);
        if(val==JFileChooser.APPROVE_OPTION) {
            absolutePath = jfc.getSelectedFile().getAbsolutePath();
            nomeFile = jfc.getSelectedFile().getName();
            JOptionPane.showMessageDialog(null,"Hai scelto il file "+nomeFile);
        }
        else if(val==JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null,"Annullata scelta del file");
        }
        try {
            GridGame read = null;
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(absolutePath));
            while(true) {
                try {
                    read = (GridGame) oos.readObject();
                    System.out.println(read.getDimension());
                    gg.setDimension(read.getDimension());
                    gg.changeReferenceTable(read.getReferenceTable());
                    System.out.println(read);
                    System.out.println(read.constrString());
                }catch(EOFException e1){
                    System.out.println("lettura file");
                    break;
                }
            }
            oos.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        } catch (ClassCastException e3) {
            e3.printStackTrace();
        }
    }

}
