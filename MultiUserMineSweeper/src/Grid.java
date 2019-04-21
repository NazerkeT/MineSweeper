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
    private static int safeCellsLeft=61;

    public static void setSafeMoveCount(int safeMoveCount){Grid.safeMoveCount = safeMoveCount;}
    public static void setFlagCount(int flagCount){Grid.flagCount = flagCount;}
    public static void setEdgeCount(int edgeCount){Grid.edgeCount = edgeCount;}
    public static void setVertexCount(int vertexCount){Grid.vertexCount = vertexCount;}
    public static void setTotalNumOfBombs(int totalNumOfBombs){Grid.totalNumOfBombs = totalNumOfBombs;}
    public static void setSafeCellsLeft(int safeCellsLeft) {Grid.safeCellsLeft = safeCellsLeft;}
    public static int getSafeCellsLeft() {return safeCellsLeft;}

    public static int getFlagCount() {return flagCount;}
    public static int getSafeMoveCount() {return safeMoveCount;}
    public static int getTotalNumOfBombs() {return totalNumOfBombs;}
    public static int getEdgeCount() {return edgeCount;}
    public static int getVertexCount() {return vertexCount;}

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

    public static void openSafeCell(Position cell, boolean isSafe,Player player){
        cell.setOpened(true);
        if(isSafe){
            safeMoveCount++;
            safeCellsLeft--;
            if(player!=null){
                player.setScore(player.getScore()+1);
                System.out.println(player.getName()+": "+player.getScore());
            }
            if (cell.getNeighborCount()==0){
                //Reveal every nearby empty cells
                floodFill(cells, cell, player);
            }
            //Add color, font
            cell.setText(String.valueOf(cell.getNeighborCount()));
            if(player!=null){
                Main.setPlayer1Info(Main.getPlayer1().getName()+": "+Main.getPlayer1().getScore());
                Main.setPlayer2Info(Main.getPlayer2().getName()+": "+Main.getPlayer2().getScore());
                if(safeCellsLeft==0 && Main.getPlayer1().getScore()>Main.getPlayer2().getScore()){
                    System.out.println(Main.getPlayer1().getName()+", You won! Score: "+Main.getPlayer1().getScore());
                    Main.keepRecords(true, Main.getPlayer1());
                }
                else if(safeCellsLeft==0 && Main.getPlayer1().getScore()<Main.getPlayer2().getScore()){
                    System.out.println(Main.getPlayer2().getName()+", You won! Score: "+Main.getPlayer2().getScore());
                    Main.keepRecords(true, Main.getPlayer2());
                }
                //InEfficient use of memory and performance!



            }
            else{
                if(safeMoveCount==vertexCount*edgeCount-totalNumOfBombs-flagCount){
                    System.out.println(player.getName()+", You won!");
                    Main.keepRecords(true, player);
                }
            }
        }
        else{
            if(player!=null){
                if(Main.getStatusOfFirst()){
                    player=Main.getPlayer1();
                }
                else{
                    player=Main.getPlayer2();
                }
            }
            else{System.out.println("Game over!");}
            Main.keepRecords(false,player);
        }
    }

    public static void floodFill(Position[][] cells, Position cell, Player player){
        int vertexCount = cells.length;
        int edgeCount = cells[0].length;
        int row = cell.getRow();
        int col = cell.getCol();
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
                    openSafeCell(cells[neigborRow][neigborCol],true, player);
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

    public void buildGraph(int vertexCount,int edgeCount){
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

//            ImageIcon icon = new ImageIcon("resources/bomb.png");
//            Image img = icon.getImage() ;
//            Image newimg = img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
//            icon = new ImageIcon( newimg );
//            cells[choice[0]][choice[1]].setIcon(icon);

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