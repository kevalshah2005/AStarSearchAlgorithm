import java.util.ArrayList;
import java.util.Random;

public class Node {
  public final int x;
  public final int y;
  public double f = 0;
  public double g = 0;
  public double h = 0;
  public ArrayList<Node> neighbors = new ArrayList<Node>();

  public Node previous = null;
  public boolean wall = false;
  public boolean closeToWall = false;

  public Node (int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void addNeighbors() {
    // adjacent neighbors
    if (x < Main.columns - 1) {
      this.neighbors.add(Main.grid[x + 1][y]);
    }
    if (x > 0) {
      this.neighbors.add(Main.grid[x - 1][y]);
    }
    if (y < Main.rows - 1) {
      this.neighbors.add(Main.grid[x][y + 1]);
    }
    if (y > 0) {
      this.neighbors.add(Main.grid[x][y - 1]);
    }
    
    // diagonal neighbors
    if (x > 0 && y > 0 && 
        Main.grid[x - 1][y].wall == false && 
        Main.grid[x][y - 1].wall == false) {
      this.neighbors.add(Main.grid[x - 1][y - 1]);
    }
    if (x < Main.columns - 1 && y < Main.rows - 1 &&
        Main.grid[x + 1][y].wall == false && 
        Main.grid[x][y + 1].wall == false) {
      this.neighbors.add(Main.grid[x + 1][y + 1]);
    }
    if (x > 0 && y < Main.rows - 1 &&
        Main.grid[x - 1][y].wall == false && 
        Main.grid[x][y + 1].wall == false) {
      this.neighbors.add(Main.grid[x - 1][y + 1]);
    }
    if (x < Main.columns - 1 && y > 0 &&
        Main.grid[x + 1][y].wall == false && 
        Main.grid[x][y - 1].wall == false) {
      this.neighbors.add(Main.grid[x + 1][y - 1]);
    }    
  }

  public void isWall() {
    Random random = new Random();
    if (random.nextFloat() < 0.1) {
      this.wall = true;
    }
  }
}