import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class  Grid implements ActionListener {
    Container grid = new Container();
    private Position[][] cells;
    private ArrayList<int []> options=new ArrayList<>();
    private int totalNumOfBombs=20;
    private void buildGraph(int vertexCount,int edgeCount){
        int k=0;
        for (int i=0;i<vertexCount;i++){
            for (int j=0;j<edgeCount;j++){
                //Build a options array, which will save in it, all cell pos combinations
                // with the purpose of using it random bomb distribution
                options.add(new int[]{i,j});
                k++;
            }
        }

        for (int i=0; i<totalNumOfBombs;i++){
            Random rand = new Random();
            int index = rand.nextInt(options.size());
//            System.out.println("Random Index "+index);

            int [] choice = new int[2];
            choice[0]=options.get(index)[0];
            choice[1]=options.get(index)[1];

            cells[choice[0]][choice[1]].setBomb(true);
//            System.out.println("Is bomb? "+cells[choice[0]][choice[1]].isBomb());

            ImageIcon icon = new ImageIcon("resources/bomb.png");
            Image img = icon.getImage() ;
            Image newimg = img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
            icon = new ImageIcon( newimg );
            cells[choice[0]][choice[1]].setIcon(icon);

            options.remove(index);
        }
        for (int i=0; i<vertexCount;i++){
            for(int j=0;j<edgeCount;j++){
                //In the same loop, number of nearby bombs calculated to each cell!
                cells[i][j].countBombs(this.cells,i,j);
            }
        }

    }
    public Grid(JFrame frame, int vertexCount, int edgeCount) { //constructor of class
        cells = new Position[vertexCount][edgeCount];

        grid.setLayout(new GridLayout(vertexCount,edgeCount));
        for (int a=0;a<cells.length;a++){
            for (int b=0;b<cells.length;b++){
                cells[a][b]=new Position();
                cells[a][b].addActionListener(this);
                cells[a][b].addMouseListener(cells[a][b]);
                grid.add(cells[a][b]);
            }
        }
        buildGraph(vertexCount,edgeCount);
//        buildGraph(vertexCount, edgeCount);
        frame.add(grid,BorderLayout.CENTER);
        frame.setVisible(true);
        //Dimension dimension = getToolkit().getScreenSize();
        //setBounds(dimension.width / 2 - 250, dimension.height / 2 - 200, 500, 400);
    }
    @Override
    public void actionPerformed(ActionEvent arg0){}

}
