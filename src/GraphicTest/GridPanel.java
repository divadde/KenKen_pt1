package GraphicTest;

import Model.CellIF;
import Model.Constraint;
import Model.GridGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class GridPanel extends JPanel {
    private GridGame gg;
    private List<Constraint> constr;
    private GameCell[][] grigliaGrafica;
    private Mediator mediator;

    public GridPanel(GridGame gg, Mediator mediator) {
        this.mediator=mediator;
        this.mediator.setGridPanel(this);
        this.gg=gg;
        setLayout(new GridLayout(gg.getDimension(),gg.getDimension(),1,1));
        setSize(400,400);
        setLocation(100,100);
    }

    public void configura(){
        if(grigliaGrafica!=null)
            rimuoviCelle();
        constr = gg.listOfConstraint();
        grigliaGrafica = new GameCell[gg.getDimension()][gg.getDimension()];
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++) {
                grigliaGrafica[i][j] = new GameCell(i,j);
                this.add(grigliaGrafica[i][j]);
                if(constr.contains(gg.getConstraint(i,j))){
                    grigliaGrafica[i][j].isDrawCell();
                    constr.remove(gg.getConstraint(i,j));
                }
            }
        }
    }

    public void aggiornaValori(CellIF[][] table){
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++){
                grigliaGrafica[i][j].setText(Integer.toString(table[i][j].getValue()));
            }
        }
        repaint();
        revalidate();
    }

    private void rimuoviCelle(){
        int len = grigliaGrafica[0].length;
        for(int i=0; i<len; i++){
            for(int j=0; j<len; j++) {
                this.remove(grigliaGrafica[i][j]);
            }
        }
    }

    public GameCell getGameCell(int i, int j){
        return grigliaGrafica[i][j];
    }


    //todo non va bene questa struttura di cella e tabella. Devo avere la possibilità di riferirmi solo alla tabella!
    public class GameCell extends JTextField implements ActionListener {
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
        public void setText(String m){
            super.setText(m);
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
                if(!(mediator.notifyGridGame(value,x,y)))
                    this.setBackground(new Color(220,80,80));
                else if(mediator.notifyGridGame(value,x,y) && this.getBackground().equals(new Color(220,80,80)))
                    this.setBackground(Color.WHITE);
                System.out.println("modificata la cella "+x+","+y);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    }

}
