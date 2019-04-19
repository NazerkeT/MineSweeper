import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class  Position extends JButton implements ActionListener, MouseListener {
    private int row;
    private int col;
    private int neighborCount = 0;
    private boolean isBomb=false;
    private boolean isOpened=false;
    private boolean isFlagged=false;
    //Every cell triggers # of flags and safe moves in entire grid.
    //This could be some kinda logic fallacy.
    private int flagCount=0;
    private int safeMoveCount=0;
    private Position[][] parentCells;

    public boolean isBomb(){
        return isBomb;
    }
    public boolean isFlagged(){
        return isFlagged;
    }
    public void setFlagged(boolean flag){
        this.isFlagged=flag;
    }
    public void setBomb(boolean flag){
        isBomb=flag;
    }

    public void open(){
        this.isOpened=true;
        this.safeMoveCount++;
        if (this.neighborCount==0){
            //Reveal every nearby empty cells
            this.foodFill(this.parentCells, this.row, this.col);
        }
        //Add color, font
        this.setText(String.valueOf(neighborCount));
    }
    public void countBombs(Position [][] cells,int row,int col){
        this.row=row;
        this.col=col;
        int vertexCount = cells.length;
        int edgeCount = cells[0].length;
        int [] points = {-1,-1,-1,0,-1,1,0,-1,0,1,1,-1,1,0,1,1};
        int dx,dy,neigborRow,neigborCol;
        for(int i=0;i<points.length;i++){
            dx=points[i];
            dy=points[++i];
            neigborRow=row+dx;
            neigborCol=col+dy;
            if(neigborRow>=0 && neigborRow<vertexCount && neigborCol>=0 && neigborCol<edgeCount){
                System.out.println("I am Here ");
                System.out.println(cells[neigborRow][neigborCol].isBomb);
                //Check for existence of the bomb in nearby cells
                if(cells[neigborRow][neigborCol].isBomb){
                    this.neighborCount+=1;
                    System.out.println("I am counting ");
                    System.out.println(this.neighborCount);
                    this.setText(String.valueOf(neighborCount));
                }
            }
        }
        //For initial test
        this.parentCells=cells;
    }

    public void foodFill(Position [][] cells,int row,int col){
        this.row=row;
        this.col=col;
        int vertexCount = cells.length;
        int edgeCount = cells[0].length;
        int [] points = {-1,-1,-1,0,-1,1,0,-1,0,1,1,-1,1,0,1,1};
        int dx,dy,neigborRow,neigborCol;
        for(int i=0;i<points.length;i++){
            dx=points[i];
            dy=points[++i];
            neigborRow=row+dx;
            neigborCol=col+dy;
            if(neigborRow>=0 && neigborRow<vertexCount && neigborCol>=0 && neigborCol<edgeCount){
                //Check for existence of the bomb in nearby cells
                if(cells[neigborRow][neigborCol].neighborCount!=0){
                    this.isOpened=true;
                    //Add color, font
                    this.setText(String.valueOf(neighborCount));
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0){}
    @Override
    public void mouseClicked(MouseEvent e) {
        int totalBombs=20;
        System.out.println("Event has fired! ");
        if(e.getButton()== MouseEvent.BUTTON1){
            if (this.isBomb()) {
//              gameOver();
                System.out.println("Game over!");
            }
            this.open();
            if(this.safeMoveCount==totalBombs-this.flagCount){

            }
        }
        if(e.getButton()==MouseEvent.BUTTON3){
            if(this.isFlagged()==false){
                this.setFlagged(true);
                this.flagCount++;
                System.out.println("First stat!");
                ImageIcon icon = new ImageIcon("resources/flag.png");
                Image img = icon.getImage() ;
                Image newimg = img.getScaledInstance( 30, 30,  Image.SCALE_AREA_AVERAGING ) ;
                icon = new ImageIcon( newimg );
                this.setIcon(icon);

            }
            else if(this.isFlagged()==true){
//                System.out.println("I am here");
                this.setIcon(null);
                this.setFlagged(false);
                this.flagCount--;
                System.out.println("Second stat!");
            }


            System.out.println("Next move!");

        }
    }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
}