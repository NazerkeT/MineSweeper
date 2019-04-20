import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class  Grid implements ActionListener {
    Container grid = new Container();
    public static Position[][] cells;
    private ArrayList<int []> options=new ArrayList<>();
    private static final int totalNumOfBombs=20;
    public static int flagCount=0;
    public static int safeMoveCount=0;

    public static void IncremFlagCout(boolean status){
        if(status==true){flagCount++;}
        flagCount--;
    }

    public static void open(Position cell){
        cell.setOpened(true);
        safeMoveCount++;
        if (cell.getNeighborCount()==0){
            //Reveal every nearby empty cells
            foodFill(cells, cell.getRow(), cell.getCol());
        }
        //Add color, font
        cell.setText(String.valueOf(cell.getNeighborCount()));
        if(safeMoveCount==totalNumOfBombs-flagCount){
            System.out.println("You won!");
            //Disable screen
            //Pop up window
        }
    }

    public static void foodFill(Position [][] cells,int row,int col){
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
                if(!cells[neigborRow][neigborCol].isOpened()){
                    cells[neigborRow][neigborCol].setOpened(true);
                    open(cells[neigborRow][neigborCol]);
                    //Add color, font
                }

            }
        }
    }

    public void countBombs(Position [][] cells, int row, int col){
        Position cell = cells[row][col];
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
                if(!cells[row][col].isBomb()){
                    if(cells[neigborRow][neigborCol].isBomb()){
                        cell.setNeighborCount(cell.getNeighborCount()+1);
                    }
                }

            }
        }
    }

    private void buildGraph(int vertexCount,int edgeCount){
        for (int i=0;i<vertexCount;i++){
            for (int j=0;j<edgeCount;j++){
                //Build a options array, which will save in it, all cell pos combinations
                // with the purpose of using it random bomb distribution
                options.add(new int[]{i,j});
            }
        }

        for (int i=0; i<totalNumOfBombs;i++){
            Random rand = new Random();
            int index = rand.nextInt(options.size());
            int [] choice = new int[2];
            choice[0]=options.get(index)[0];
            choice[1]=options.get(index)[1];

            cells[choice[0]][choice[1]].setBomb(true);

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
                this.countBombs(this.cells,i,j);
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
        frame.add(grid,BorderLayout.CENTER);
        frame.setVisible(true);
    }
    Grid(){}
    @Override
    public void actionPerformed(ActionEvent arg0){}

}
