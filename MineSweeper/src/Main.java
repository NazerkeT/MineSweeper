import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class  Main extends JFrame implements MouseListener, ActionListener {
    JFrame frame = new JFrame("MineSweeperGame");
    JButton reset = new JButton("Restart");
    private int vertexCount = 9;
    private int edgeCount = 9;
    private Grid grid;
    private boolean gameOver(){
        return false;
    }
    public Main() { //constructor of class
        frame.setSize(500,400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(reset,BorderLayout.NORTH);
        reset.addActionListener(this);
        addMouseListener(this);
        grid = new Grid(frame,vertexCount,edgeCount);
        //Dimension dimension = getToolkit().getScreenSize();
        //setBounds(dimension.width / 2 - 250, dimension.height / 2 - 200, 500, 400);
    }
    public static void main(String[] args) {
        new Main();// to start the game

    }
    @Override
    public void actionPerformed(ActionEvent arg0){}
    @Override
    public void mouseClicked(MouseEvent e) {
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