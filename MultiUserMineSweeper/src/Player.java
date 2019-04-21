public class Player {
    private String name;
    private int score=0;
    private double time;
    private int flagCount=0;

    public void setName(String name) {this.name = name;}
    public String getName() {return name;}
    public void setScore(int score) {this.score = score;}
    public int getScore() {return score;}
    public double getTime() {return time;}
    public void setTime(double time) {this.time = time;}
    public void setFlagCount(int flagCount) {this.flagCount = flagCount;}
    public int getFlagCount() {return flagCount;}

    public Player(String name){
        this.name=name;
    }
}
