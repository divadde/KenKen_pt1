package GraphicTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar implements ActionListener {
    private MainPanel mp;
    private JMenu gioca, impostazioni, help;
    private JMenuItem nuovaPartita, caricaPartita;

    public Menu(MainPanel flip){
        this.mp=mp;
        gioca = new JMenu("Gioca");
        impostazioni = new JMenu("Impostazioni");
        help = new JMenu("Help");
        add(gioca);
        add(impostazioni);
        add(help);
        nuovaPartita=new JMenuItem("Nuova partita");
        caricaPartita=new JMenuItem("Carica partita");
        gioca.add(nuovaPartita);
        gioca.add(caricaPartita);
        nuovaPartita.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==nuovaPartita){
            CardLayout c = (CardLayout) mp.getLayout();
            c.show(mp,"game");
        }
    }
}
