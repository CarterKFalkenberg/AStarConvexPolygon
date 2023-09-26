import java.util.LinkedList;

public class Vector2D {
  public double x, y;
  public LinkedList<Vector2D> validNextMoves;
  public Double h;

  public Vector2D() {
    this(0d, 0d);
  }

  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
    this.h = null; 
  }

  /* 
  // determines if two vectors are the same with some leeway c
  public boolean equals(Vector2D other){
    double c = 0.001;
    if (this.x - c < other.x && this.x + c > other.x
      &&this.y - c < other.y && this.y + c > other.y){
        return true;
      }
    return false;
  }
  */

  // sets this.validNextMoves
  // gets all valid moves for the given vector in an environment, 
  // Needs to know which polygon the vector is on so that we do not think
    // there is a valid next move of cutting through the current polygon
  public void findValidNextMoves(Environment env, Polygon poly){
    this.validNextMoves = new LinkedList<Vector2D>();
    /*
     * Possible moves:
     * - start / end
     * - left / right neighbor, always valid
     * - other obstacles' vertices
     */
    // add left and right neighbors. They are alwas valid
    if (poly != null){
      this.validNextMoves.add(poly.getLeftNeighbor(this));
      this.validNextMoves.add(poly.getRightNeighbor(this));
    }
    // check start
    boolean startReachable = true;
    Line toStart = new Line(this, env.start);
      // for all edges that don't contain vertex:
    for (Polygon obstacle : env.obstacles){
      for (Line line : obstacle.edges){
        if (line.contains(this)){
          continue;
        }
        // check if vertex->start intersects with line
        // if intersection, set a bool to false  
        if (toStart.intersectsWith(line)){
          startReachable = false;
          break;
        }
      }
    }
    // if the bool is true, add it
    if (startReachable){
      this.validNextMoves.add(env.start);
    }
        

    // check goal
    boolean goalReachable = true;
    Line toGoal = new Line(this, env.goal);
    // for all edges that don't contain vertex:
    for (Polygon obstacle : env.obstacles){
      for (Line line : obstacle.edges){
        if (line.contains(this)){
          continue;
        }
        // check if vertex->goal intersects with line
        // if intersection, set a bool to false  
        if (toGoal.intersectsWith(line)){
          goalReachable = false;
          break;
        }
      }
    }
    // if the bool is true, add it
    if (goalReachable){
      this.validNextMoves.add(env.goal);
    }

    // check all other vertices not in poly 
    for (Polygon obstacle : env.obstacles){
      if (obstacle.equals(poly)){
        continue;
      }
      A: for (Vector2D other : obstacle.vertices){
        Line toOther = new Line(this, other);
        // for all edges that don't contain vertex or other:
        for (Polygon o : env.obstacles){
          for (Line edge : o.edges){
            if (edge.contains(this) || edge.contains(other)){
              continue;
            }
            // check if vertex->other intersects with line
            // if intersection, continue to next other vertex
            if (toOther.intersectsWith(edge)){
              continue A;
            }
            
          }
        }
        // if you make it out of inner for loop, then there were no intersections
        this.validNextMoves.add(other);
      } 
    }
  }

  // finds h for this vector
  public void findHeuristic(Vector2D goal){
    this.h = Math.sqrt(Math.pow(this.x - goal.x, 2) + Math.pow(this.y - goal.y, 2));
  }

  public static double distance(Vector2D v1, Vector2D v2){
    return Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2));
  }

}
