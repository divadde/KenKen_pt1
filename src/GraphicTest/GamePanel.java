package GraphicTest;

import Model.CellIF;
import Model.Constraint;
import Model.GridGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GamePanel extends JPanel {
    private GridGame gg;
    private List<Color> colours;
    private List<Constraint> constr;
    private HashMap<Constraint,Color> constrColour;

    public GamePanel(GridGame gg){
        this.gg=gg;
        //colours = getColours();
        constr = gg.listOfConstraint();
        //constrColour = new HashMap<>();
        setLayout(new GridLayout(gg.getDimension(),gg.getDimension(),1,1));
        setSize(400,400);
        setLocation(100,100);
        int n=0;
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++) {
                GameCell gc = new GameCell(i,j);
                this.add(gc);
                /*
                if (!(constrColour.containsKey(gg.getConstraint(i,j)))) {
                    constrColour.put(gg.getConstraint(i, j), colours.get(n));
                    n=(n+1)%10;
                }
                gc.setBackground(constrColour.get(gg.getConstraint(i,j)));
                *///non funziona todo
                if(constr.contains(gg.getConstraint(i,j))){
                    gc.isDrawCell();
                    constr.remove(gg.getConstraint(i,j));
                }
            }
        }
    }

    /*
    private List<Color> getColours(){
        Color c1 = new Color(220,80,80);
        Color c2 = new Color(80,80,220);
        Color c3 = new Color(80,220,80);
        Color c4 = new Color(220,220,80);
        Color c5 = new Color(80,220,220);
        Color c6 = new Color(220,80,220);
        Color c7 = new Color(220,220,220);
        Color c8 = new Color(220,140,60);
        Color c9 = new Color(140,140,140);
        Color c10 = new Color(70,170,100);
        List<Color> list = new LinkedList<>();
        list.add(c1); list.add(c2); list.add(c3); list.add(c4); list.add(c5);
        list.add(c6); list.add(c7); list.add(c8); list.add(c9); list.add(c10);
        return list;
    }
    */

    //todo non va bene questa struttura di cella e tabella. Devo avere la possibilità di riferirmi solo alla tabella!
    private class GameCell extends JTextField implements ActionListener {
        private int x, y;
        private Constraint c;
        private boolean drawCell=false;

        public GameCell(int x, int y){
            this.c=gg.getConstraint(x,y);
            this.x=x;
            this.y=y;
            addActionListener(this);
            setHorizontalAlignment(JTextField.CENTER);
            drawBoarder(x,y);
            this.setFont(new Font("Ariel",1,20));
        }

        public void isDrawCell(){
            drawCell=true;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(drawCell) {
                Font font = new Font("Arial", Font.PLAIN, 16);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setFont(font);
                g2d.drawString(c.toString(), 7, 20);
            }
        }

        private void drawBoarder(int x, int y){
            int myId = gg.getConstraint(x,y).getId();
            int left = 1;
            int top = 1;
            int bot = 1;
            int right = 1;
            if(x-1<0 || myId!=gg.getConstraint(x-1,y).getId())
                top+=5;
            if(x+1>=gg.getDimension() || myId!=gg.getConstraint(x+1,y).getId())
                bot+=5;
            if(y-1<0 || myId!=gg.getConstraint(x,y-1).getId())
                left+=5;
            if(y+1>=gg.getDimension() || myId!=gg.getConstraint(x,y+1).getId())
                right+=5;
            this.setBorder(BorderFactory.createMatteBorder(top,left,bot,right, Color.BLACK));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = getText();
            try{
                int value = Integer.parseInt(text);
                if(!(gg.addValue(value,x,y)))
                    this.setBackground(new Color(220,80,80));
                else if(gg.addValue(value,x,y) && this.getBackground().equals(new Color(220,80,80)))
                    this.setBackground(Color.WHITE);
                System.out.println("modificata la cella "+x+","+y);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    }

}
