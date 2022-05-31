package Command;

import Model.GridGame;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveGameCommand implements Command{
    GridGame g;

    public SaveGameCommand(GridGame g){
        this.g=g;
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
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(absolutePath));
            oos.writeObject(g);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
