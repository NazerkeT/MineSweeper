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
    public static boolean isFirstClicked=true;

    public boolean isBomb(){return isBomb;}
    public void setBomb(boolean flag){
        isBomb=flag;
    }
    public void setOpened(boolean opened) {isOpened = opened;}
    public boolean isOpened(){return isOpened;}
    public void setRow(int row) {this.row = row;}
    public int getRow() {return row;}
    public void setCol(int col) {this.col = col;}
    public int getCol() {return col;}
    public void setNeighborCount(int neighborCount) {this.neighborCount = neighborCount;}
    public int getNeighborCount() {return neighborCount;}

    @Override
    public void actionPerformed(ActionEvent arg0){}
    @Override
    public void mouseClicked(MouseEvent e) {
        if(!this.isOpened){
            if(Main.getMode()=="single"){
                mouseActionOn(this,e,null);
            }
            else if(Main.getMode()=="double"){
                Player byDefault=Main.getPlayer1();
                mouseActionOn(this,e,byDefault);
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

    private static void mouseActionOn(Position cell,MouseEvent e,Player player){
            if(e.getButton()== MouseEvent.BUTTON1){
                if(!cell.isFlagged){
                    System.out.println("Event has fired! ");
                    if(player!=null){
                        boolean isFirstPlTurn=Main.getStatusOfFirst();
                        if(isFirstPlTurn){
                            player=Main.getPlayer1();
                            Main.setStatusOfFirst(false);
                        }
                        else if(!isFirstPlTurn){
                            player=Main.getPlayer2();
                            Main.setStatusOfFirst(true);
                        }
                    }
                    if (isFirstClicked && cell.isBomb()){
                        while(isFirstClicked && !cell.isBomb){
                            Main.reset(false);
                        }
                        isFirstClicked=false;
                        Grid.openSafeCell(cell,true,player);
                    }
                    else if (!isFirstClicked && cell.isBomb()) {
                        Grid.openSafeCell(cell,false,player);
                    }
                    else if(isFirstClicked){
                        isFirstClicked=false;
                        Grid.openSafeCell(cell,true,player);
                    }
                    else{
                        Grid.openSafeCell(cell,true,player);
                    }
                }
            }
            if(e.getButton()==MouseEvent.BUTTON3){
                if(player!=null){
                    boolean isFirstPlTurn=Main.getStatusOfFirst();
                    if(isFirstPlTurn){
                        player=Main.getPlayer1();
                        Main.setStatusOfFirst(false);
                    }
                    else if(!isFirstPlTurn){
                        player=Main.getPlayer2();
                        Main.setStatusOfFirst(true);
                    }
                }
                if(!cell.isFlagged){
                    cell.isFlagged=true;
                    if(cell.isBomb){
                        if(player==null){
                            Grid.IncremFlagCount(true);
                            System.out.println("flag++:  "+Grid.getFlagCount());
                        }
                        else{
                            player.setFlagCount(player.getFlagCount()+1);
                            System.out.println(player.getName()+", flag++:  "+player.getFlagCount());
                        }

                    }
                    ImageIcon icon = new ImageIcon("resources/flag.png");
                    Image img = icon.getImage() ;
                    Image newimg = img.getScaledInstance( 30, 30,  Image.SCALE_AREA_AVERAGING ) ;
                    icon = new ImageIcon( newimg );
                    cell.setIcon(icon);
                }
                else if(cell.isFlagged){
                    cell.isFlagged=false;
                    if(cell.isBomb){
                        if(player==null){
                            Grid.IncremFlagCount(false);
                            System.out.println("flag--:  "+Grid.getFlagCount());
                        }
                        else{
                            player.setFlagCount(player.getFlagCount()-1);
                            System.out.println(player.getName()+", flag--:  "+player.getFlagCount());
                        }
                    }
                    cell.setIcon(null);
                    //DELETE THIS LINE AFTERWARDS!
                    if(cell.isBomb){
                        ImageIcon icon = new ImageIcon("resources/bomb.png");
                        Image img = icon.getImage() ;
                        Image newimg = img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
                        icon = new ImageIcon( newimg );
                        cell.setIcon(icon);
                    }

                }
            }
    }
}

