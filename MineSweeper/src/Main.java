import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class  Main extends JFrame implements MouseListener, ActionListener {
    private static JFrame frame = new JFrame("MineSweeperGame");
    private JButton reset = new JButton("Restart");
    private static Grid grid;
    private static int gridVertexCount = 9;
    private static int gridEdgeCount = 9;

    private static void reset(){
        frame.getContentPane().removeAll();
        frame.repaint();
        Grid.setFlagCount(0);
        Grid.setSafeMoveCount(0);
        new Main();
    }
    public static void keepRecords(boolean status){
        String text;
        if(status){
            text="Congrats! You Won!";
            new Dialog(frame,text);
        }
        else{
            text="Game Over!";
            new Dialog(frame,text);
        }
        reset();
    }

    public Main() { //constructor of class
        frame.setSize(500,400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(reset,BorderLayout.NORTH);
        reset.addMouseListener(this);
        grid = new Grid(frame,gridVertexCount,gridEdgeCount);
    }
    public static void main(String[] args) {
        new Main();// to start the game

    }

    @Override
    public void actionPerformed(ActionEvent arg0){}
    @Override
    public void mouseClicked(MouseEvent e) {reset();}
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

class Dialog extends JDialog{
    public Dialog(){
        super();
        JPanel panel=new JPanel();
        panel.add(new JLabel("Welcome to MineSweeper!"));
        this.getContentPane().add(panel);
    }

    public Dialog(JFrame frame, String label){
        super(frame,true);
        this.setSize(300,200);
        JPanel panel=new JPanel();
        panel.add(new JLabel(label));
        this.getContentPane().add(panel);
        this.setVisible(true);

    }
}
//Add Timer()