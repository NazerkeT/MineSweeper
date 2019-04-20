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

    public boolean isBomb(){return isBomb;}
    public void setBomb(boolean flag){
        isBomb=flag;
    }
    public boolean isFlagged(){
        return isFlagged;
    }
    public void setFlagged(boolean flag){
        this.isFlagged=flag;
    }
    public void setOpened(boolean opened) {isOpened = opened;}
    public boolean isOpened(){return isOpened;}
    public void setRow(int row) {this.row = row;}
    public int getRow() {return row;}
    public void setCol(int col) {this.col = col;}
    public int getCol() {return col;}
    public void setNeighborCount(int neighborCount) {this.neighborCount = neighborCount;}
    public int getNeighborCount() {return neighborCount;}

    public static void callOpenFromGrid(Position cell){
        open(cell);
    }

    public static void CallIncremFlagCount(boolean status){
        if(status==true){IncremFlagCount(true);}
        IncremFlagCount(false);
    }

    public static void main(String [] args){
        Position cell = new Position();
        callOpenFromGrid(cell);
    }
    @Override
    public void actionPerformed(ActionEvent arg0){}
    @Override
    public void mouseClicked(MouseEvent e) {
        Position cell = this;
        System.out.println("Event has fired! ");
        if(e.getButton()== MouseEvent.BUTTON1){
            if (this.isBomb()) {
//              gameOver();
                System.out.println("Game over!");
                //Disable screen
                //Pop up window
            }
            else{
//                callOpenFromGrid(cell);
            }
        }
        if(e.getButton()==MouseEvent.BUTTON3){
            if(!this.isOpened && !this.isBomb){
                if(this.isFlagged()==false){
                    this.setFlagged(true);
//                    grid.flagCount++;
                    //CallIncremFlagCount();
                    ImageIcon icon = new ImageIcon("resources/flag.png");
                    Image img = icon.getImage() ;
                    Image newimg = img.getScaledInstance( 30, 30,  Image.SCALE_AREA_AVERAGING ) ;
                    icon = new ImageIcon( newimg );
                    this.setIcon(icon);
                }
                else if(this.isFlagged()==true){
                    this.setIcon(null);
                    this.setFlagged(false);
//                    grid.flagCount--;
                }
            }
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

//Disable screen
//Pop up window
//Add timer
//Refresh button!
