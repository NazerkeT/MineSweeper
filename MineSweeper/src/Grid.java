import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class  Grid implements ActionListener {
    private Container grid = new Container();
    private ArrayList<int []> options=new ArrayList<>();
    private static int totalNumOfBombs=20;
    private static int vertexCount=9;
    private static int edgeCount=9;
    private static Position[][] cells;
    private static int flagCount=0;
    private static int safeMoveCount=0;

    public static void setSafeMoveCount(int safeMoveCount){Grid.safeMoveCount = safeMoveCount;}
    public static void setFlagCount(int flagCount){Grid.flagCount = flagCount;}
    public static void setEdgeCount(int edgeCount){Grid.edgeCount = edgeCount;}
    public static void setVertexCount(int vertexCount){Grid.vertexCount = vertexCount;}
    public static void setTotalNumOfBombs(int totalNumOfBombs){Grid.totalNumOfBombs = totalNumOfBombs;}

    public static void IncremFlagCount(boolean status){
        if(status){
            flagCount++;
            totalNumOfBombs--;
        }
        else {
            flagCount--;
            totalNumOfBombs++;
        }
    }

    public static void openSafeCell(Position cell, boolean isSafe){
        cell.setOpened(true);
        if(isSafe){
            safeMoveCount++;
            if (cell.getNeighborCount()==0){
                //Reveal every nearby empty cells
                foodFill(cells, cell.getRow(), cell.getCol());
            }
            //Add color, font
            cell.setText(String.valueOf(cell.getNeighborCount()));
            System.out.println("safeMoveCount: "+safeMoveCount);
            System.out.println("totalNumOfBombs: "+totalNumOfBombs);
            System.out.println("flagCount: "+flagCount);
            System.out.println("Cells Left To Win: "+(vertexCount*edgeCount-totalNumOfBombs-flagCount));
            if(safeMoveCount==vertexCount*edgeCount-totalNumOfBombs-flagCount){
                System.out.println("You won!");
                Main.keepRecords(true);
                //Disable screen
                //Pop up window
            }
        }
        else{
            Main.keepRecords(false);
        }
    }

    public static void foodFill(Position[][] cells, int row, int col){
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
                    openSafeCell(cells[neigborRow][neigborCol],true);
                    //Add color, font
                }

            }
        }
    }

    private void countBombs(Position[][] cells, int row, int col){
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
        this.vertexCount=vertexCount;
        this.edgeCount=edgeCount;
        cells = new Position[vertexCount][edgeCount];
        grid.setLayout(new GridLayout(vertexCount,edgeCount));
        for (int i=0;i<cells.length;i++){
            for (int j=0;j<cells.length;j++){
                cells[i][j]=new Position();
                cells[i][j].setRow(i);
                cells[i][j].setCol(j);
                grid.add(cells[i][j]);
                cells[i][j].addActionListener(this);
                cells[i][j].addMouseListener(cells[i][j]);
            }
        }
        buildGraph(vertexCount,edgeCount);
        frame.add(grid,BorderLayout.CENTER);
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent arg0){}

}
