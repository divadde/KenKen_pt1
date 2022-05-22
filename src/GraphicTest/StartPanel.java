package GraphicTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel implements ActionListener {
    private FlipPanel father;
    private JButton nuovaPartita, carica;

    public StartPanel(FlipPanel father){
        this.father=father;
        setSize(500,500);
        setLayout(null);
        add(nuovaPartita=new JButton("Nuova partita"));
        add(carica=new JButton("Carica"));
        nuovaPartita.setSize(150,50);
        carica.setSize(100,50);
        carica.setLocation(325,200);
        nuovaPartita.setLocation(300,100);
        nuovaPartita.addActionListener(this);
        carica.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==nuovaPartita){
            CardLayout c = (CardLayout) father.getLayout();
            c.show(father,"game");
        }
    }
}
