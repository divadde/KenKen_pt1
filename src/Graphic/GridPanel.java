package Graphic;

import Graphic.Mediator.Mediator;
import Graphic.Mediator.Request;
import Graphic.Mediator.Subject;
import Model.Constraint;
import Model.GridGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class GridPanel extends JPanel implements Subject {
    private Mediator mediator;
    private GridGame gg; //riferimento alla parte logica
    private List<Constraint> constr;
    private GameCell[][] grigliaGrafica;
    private boolean suggerimentiEnabled;

    public GridPanel(GamePanel gp, GridGame gg, Mediator mediator) {
        setMediator(mediator);

        this.gg=gg;
        setLayout(new GridLayout(gg.getDimension(),gg.getDimension(),1,1));
        setSize(400,400);
        setLocation(100,50);
        suggerimentiEnabled=false;
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
        if(suggerimentiEnabled)
            coloraCelle();
    }

    public void aggiornaValori(){
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++){
                int value = gg.getTable()[i][j].getValue();
                if(value!=0)
                    grigliaGrafica[i][j].setText(Integer.toString(value));
            }
        }
        if(suggerimentiEnabled)
            coloraCelle();
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

    private void coloraCelle(){
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++) {
                if(gg.getCell(i,j).getValue()==0)
                    grigliaGrafica[i][j].setBackground(new Color(255,255,255));
                else {
                    if (gg.getCell(i,j).getState())
                        grigliaGrafica[i][j].setBackground(new Color(80, 220, 80));
                    else
                        grigliaGrafica[i][j].setBackground(new Color(220, 80, 80));
                }
            }
        }
    }

    private void eliminaColori(){
        for(int i=0; i<gg.getDimension(); i++) {
            for (int j = 0; j < gg.getDimension(); j++) {
                grigliaGrafica[i][j].setBackground(new Color(255,255,255));
            }
        }
    }

    @Override
    public void handleRequest(Request request) {
        if(request.getTipo()==Request.Tipo.READY){
            request.getCommand().execute(gg);
            setLayout(new GridLayout(gg.getDimension(),gg.getDimension(),1,1));
            configura();
            repaint();
            revalidate();
        }
        else if(request.getTipo()==Request.Tipo.SAVEGAME){
            request.getCommand().execute(gg);
        }
        else if(request.getTipo()==Request.Tipo.LOADGAME){
            request.getCommand().execute(gg);
            setLayout(new GridLayout(gg.getDimension(), gg.getDimension(), 1, 1));
            configura();
            aggiornaValori();
        }
        else if(request.getTipo()==Request.Tipo.SHOWSOLUTION){
            request.getCommand().execute(gg);
            aggiornaValori();
        }
        else if(request.getTipo()==Request.Tipo.NEXTSOLUTION){
            request.getCommand().execute(gg);
            aggiornaValori();
        }
        else if(request.getTipo()==Request.Tipo.PREVIOUSSOLUTION){
            request.getCommand().execute(gg);
            aggiornaValori();
        }
        else if(request.getTipo()==Request.Tipo.SHOWSUGGESTINGS){
            suggerimentiEnabled = !suggerimentiEnabled;
            if(suggerimentiEnabled)
                coloraCelle();
            else
                eliminaColori();
        }
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator=mediator;
        mediator.addSubject(this);
    }


    //todo non va bene questa struttura di cella e tabella. Devo avere la possibilità di riferirmi solo alla tabella!
    private class GameCell extends JTextField implements KeyListener {
        private int x, y;
        private Constraint c;
        private boolean drawCell=false;

        public GameCell(int x, int y){
            this.c=gg.getConstraint(x,y);
            this.x=x;
            this.y=y;
            addKeyListener(this);
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
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) {
            String text = getText();
            try{
                if(text==null) {
                    gg.removeValue(x,y);
                }
                else {
                    int value = Integer.parseInt(text);
                    gg.addValue(value, x, y);
                }
                if(suggerimentiEnabled)
                    coloraCelle();
                System.out.println("modificata la cella " + x + "," + y);
                System.out.println(gg.getCell(x, y).getState());
            } catch (NumberFormatException ex) {
                gg.removeValue(x,y);
                setText(null);
                System.out.println("modificata la cella " + x + "," + y);
                System.out.println(gg.getCell(x, y).getState());
                this.setBackground(Color.WHITE);
            }
        }
    }

}
