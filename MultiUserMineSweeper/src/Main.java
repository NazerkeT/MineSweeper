import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class  Main extends JFrame implements MouseListener, ActionListener {
    private static JFrame frame = new JFrame("MineSweeperGame");
    public static JFrame welcomeFrame;
    private static Player Player1;
    private static Player Player2;
    private static String mode;
    private static boolean statusOfFirst=true;
    private static JLabel player1Info = new JLabel();
    private static JLabel player2Info = new JLabel();

    public static void setPlayer1(Player player1) {Player1 = player1;}
    public static Player getPlayer1() {return Player1;}
    public static void setPlayer2(Player player2) {Player2 = player2;}
    public static Player getPlayer2() {return Player2;}
    public static void setMode(String mode) {Main.mode = mode;}
    public static String getMode() {return mode;}
    public static void setStatusOfFirst(boolean statusOfFirst) {Main.statusOfFirst = statusOfFirst;}
    public static boolean getStatusOfFirst() {return statusOfFirst;}

    public static JLabel getPlayer1Info() {return player1Info;}
    public static void setPlayer1Info(String playerInfo) {Main.player1Info.setText(playerInfo);}
    public static JLabel getPlayer2Info() {return player1Info;}
    public static void setPlayer2Info(String playerInfo) {Main.player2Info.setText(playerInfo);}

    public static void reset(boolean isFirstExecuted){
        if(!isFirstExecuted){Position.isFirstClicked=false;}
        else{
            Position.isFirstClicked=true;
        }
        frame.getContentPane().removeAll();
        frame.repaint();
        Grid.setFlagCount(0);
        Grid.setSafeMoveCount(0);
        Grid.setSafeCellsLeft(61);
        statusOfFirst=true;
        getPlayer1().setScore(0);
        getPlayer2().setScore(0);
//        new Main();
        new Main(true);
    }
    public static void keepRecords(boolean winStatus, Player player){
        String text;
        if(player==null){
            if(winStatus){
                text="Congrats! You won!";
                new Dialog(frame,text);
            }
            else{
                text="Game Over!";
                new Dialog(frame,text);
            }
        }
        else{
            text=player.getName()+", Congrats! You Won! \n";
//            +Player1.getName()+": "+Player1.getScore()+"\n"+Player2.getName()+": "+Player2.getScore()
            new Dialog(frame,text);
        }
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.dispose();
        Grid.setFlagCount(0);
        Grid.setSafeMoveCount(0);
        Grid.setSafeCellsLeft(61);
        statusOfFirst=true;
        if(mode=="double"){
            Player1=null;
            Player2=null;
        }

        Main game = new Main();
        game.WelcomeFrame();
    }
    private void WelcomeFrame(){
        welcomeFrame = new JFrame();
        welcomeFrame.setSize(400,400);
        welcomeFrame.setVisible(true);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome to MineSweeper Game!\n");
        JLabel labelForImage = new JLabel();

        ImageIcon icon = new ImageIcon("resources/bomb2.png");
        Image img = icon.getImage() ;
        Image newimg = img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
        icon = new ImageIcon( newimg );

        labelForImage.setIcon(icon);

        JButton singleMode = new JButton("singleMode");
        singleMode.addActionListener(this);
        JButton doubleMode = new JButton("doubleMode");
        doubleMode.addActionListener(this);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        panel1.add(label);
        panel1.add(labelForImage);
        panel2.add(singleMode);
        panel2.add(doubleMode);

        container.add(panel1);
        container.add(panel2);

        welcomeFrame.add(container);
        welcomeFrame.setVisible(true);
        welcomeFrame.setResizable(false);

    }
    private Main(){}
    public Main(boolean startGame) { //constructor of class
        frame.setSize(600,450);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton reset = new JButton("Restart");
        if(mode=="double"){
            player1Info.setText(Player1.getName()+": "+Player1.getScore());
            player2Info.setText(Player2.getName()+": "+Player2.getScore());
            frame.add(player1Info,BorderLayout.NORTH);
            frame.add(player2Info,BorderLayout.SOUTH);
        }
        Grid grid;
        int gridVertexCount = 9;
        int gridEdgeCount = 9;
        if(startGame){
            frame.add(reset,BorderLayout.WEST);

            reset.addMouseListener(this);
            grid = new Grid(frame,gridVertexCount,gridEdgeCount);
        }
    }
    public static void main(String[] args) {
        Main game = new Main();
        game.WelcomeFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object obj = e.getSource();
        JButton newObj = (JButton)obj;

        if(newObj.getText()=="singleMode"){
            mode="single";
            new Main(true);

        }
        else if(newObj.getText()=="doubleMode"){
            mode="double";
            new Dialog(frame, "Start a game",true);
        }
        this.dispose();


    }
    @Override
    public void mouseClicked(MouseEvent e) {reset(true);}
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

class Dialog extends JDialog implements ActionListener{
    private JTextField field1;
    private JTextField field2;

    public Dialog(JFrame frame, String label){
        super(frame,true);
        this.setSize(300,200);
        JPanel panel=new JPanel();
        panel.add(new JLabel(label));
        this.getContentPane().add(panel);
        this.setVisible(true);

    }
    public Dialog(JFrame frame, String label, boolean doubleMode){
        super(frame,true);
        if(doubleMode){
            this.setSize(300,200);
            JPanel panel=new JPanel();
            panel.add(new JLabel(label));
            this.getContentPane().add(panel);
            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            field1 = new JTextField(16);
            field2 = new JTextField(16);

            JButton start = new JButton("start");

            container.add(field1);
            container.add(field2);
            container.add(start);
            container.setVisible(true);
            start.addActionListener(this);
            this.getContentPane().add(container);
            this.setVisible(true);
        }

    }
    @Override
    public void actionPerformed(ActionEvent e){
        Object obj = e.getSource();
        JButton newObj = (JButton)obj;

        if(newObj.getText()=="start"){
            String name1 = field1.getText();
            String name2 = field2.getText();
            Player player1 = new Player(name1);

            Main.setPlayer1(player1);
            Player player2 = new Player(name2);
            Main.setPlayer2(player2);
            Main.setMode("double");
            this.dispose();
            Main.welcomeFrame.dispose();
            new Main(true);
            System.out.println(name1+"  "+name2);
        }
    }
}

//Bug with bombs
//Frames are not closed at all!
//Add Timer()
//Equal grid size is allowed, but not others

//Feature
//What is done:
// 1)two modes are available at the same time
// 2)User cant lose from the first choice!(how on real games)NOT YET
// Maybe: Timer
// Maybe: Levels
//Design

//Read bout how bfs works