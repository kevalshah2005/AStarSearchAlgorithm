import java.lang.Math;
import java.util.ArrayList;

class Main {
  public static final int rows = 48;
  public static final int columns = 48;
  public static Node[][] grid;
  public static ArrayList<Node> openSet = new ArrayList<Node>();
  public static ArrayList<Node> closedSet = new ArrayList<Node>();
  public static ArrayList<Node> path = new ArrayList<Node>();
  static Node current;

  public static void main(String[] args) {
    // Making a 2D array (aka a grid)
    grid = new Node[rows][columns];

    // Each value in the grid is a Node
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        grid[i][j] = new Node(i, j);
        grid[i][j].isWall();
      }
    }

    // This for loop is in separate from the last one because neighbors need to be added after all of the nodes are in to detect if any are walls
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        grid[i][j].addNeighbors();
      }
    }

    Node startNode = grid[0][0];
    Node endNode = grid[rows - 1][columns - 1];

    startNode.wall = false;
    endNode.wall = false;

    openSet.add(startNode);

    while (true) {
      if (openSet.size() > 0) {
        int lowestIndex = 0;
        for (int i = 0; i < openSet.size(); i++) {
          if (openSet.get(i).f < openSet.get(lowestIndex).f) {
            lowestIndex = i;
          }
        }

        current = openSet.get(lowestIndex);
        if (current == endNode) {
          System.out.println("DONE!");
          break;
        }
        
        openSet.remove(lowestIndex);
        closedSet.add(current);
        ArrayList<Node> neighbors = current.neighbors;

        for (var i = 0; i < neighbors.size(); i++) {
          Node neighbor = neighbors.get(i);
          
          if (!closedSet.contains(neighbor) && !neighbor.wall && !neighbor.closeToWall) {
            // var tempG = current.g + 0.1;
            double tempG = current.g + 0.1;
            
            boolean newPath = false;
            if (openSet.contains(neighbor)) {
              if (tempG < neighbor.g) {
                neighbor.g = tempG;
                newPath = true;
              }
            } else {
              neighbor.g = tempG;
              openSet.add(neighbor);
              newPath = true;
            }
            
            neighbor.h = calculateHeuristic(neighbor, endNode);
            neighbor.f = neighbor.g + neighbor.h;
            
            if (newPath == true) {
              neighbor.previous = current;
            }
          }
        }
      } else {
        System.out.println("No solution!");
        break;
      }
    }

    Node temp = current;
    path.add(temp);
    while (temp.previous != null) {
      path.add(temp.previous);
      temp = temp.previous;
    }


    for (int i = path.size() - 1; i >= 0; i--) {
      System.out.println(path.get(i).x + " " + path.get(i).y);
    }
  }
  public static double calculateHeuristic(Node a, Node b) {
    int deltaX = Math.abs(a.x - b.x);
    int deltaY = Math.abs(a.y - b.y);
    double d = (deltaX + deltaY) + (1.41 - 2) * Math.min(deltaX, deltaY);
    return d;
  }
}