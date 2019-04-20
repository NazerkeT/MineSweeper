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
//    public boolean isFlagged(){
//        return isFlagged;
//    }
//    public void setFlagged(boolean flag){
//        this.isFlagged=flag;
//    }
    public void setOpened(boolean opened) {isOpened = opened;}
    public boolean isOpened(){return isOpened;}
    public void setRow(int row) {this.row = row;}
    public int getRow() {return row;}
    public void setCol(int col) {this.col = col;}
    public int getCol() {return col;}
    public void setNeighborCount(int neighborCount) {this.neighborCount = neighborCount;}
    public int getNeighborCount() {return neighborCount;}

    public static void callOpenFromGrid(Position cell){
        Grid.open(cell);
    }

    public static void CallIncremFlagCount(boolean status){
        if(status==true){Grid.IncremFlagCount(status);}
        else {Grid.IncremFlagCount(status);}
    }

    @Override
    public void actionPerformed(ActionEvent arg0){}
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Event has fired! ");
        if(!this.isOpened){
            if(e.getButton()== MouseEvent.BUTTON1){
                if (this.isBomb()) {
                    System.out.println("Game over!");
                    //Reset everything
                    //Disable screen
                    //Pop up window
                }
                else{
                    callOpenFromGrid(this);
                }
            }
            if(e.getButton()==MouseEvent.BUTTON3){
                //Remove this if statement afterwards
                if(!this.isFlagged){
                    this.isFlagged=true;
                    System.out.println("Is bomb?"+this.isBomb);
                    if(this.isBomb){CallIncremFlagCount(true);
                        System.out.println("Is bomb?"+this.isBomb);
                    }
                    ImageIcon icon = new ImageIcon("resources/flag.png");
                    Image img = icon.getImage() ;
                    Image newimg = img.getScaledInstance( 30, 30,  Image.SCALE_AREA_AVERAGING ) ;
                    icon = new ImageIcon( newimg );
                    this.setIcon(icon);
                }
                else if(this.isFlagged){
                    this.isFlagged=false;
                    this.setIcon(null);
                    if(this.isBomb){CallIncremFlagCount(false);}

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
