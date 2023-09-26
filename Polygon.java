import java.util.ArrayList;

public class Polygon {
  public int sides;
  public ArrayList<Vector2D> vertices;
  public ArrayList<Line> edges;

  // on initialization, vertices will be empty temporarily
  public Polygon(int sides) {
    this.sides = sides;
    this.vertices = new ArrayList<Vector2D>(sides);
    this.edges = new ArrayList<Line>();
  }

  /*
   * Removes duplicate vertices from the polygon
   * TODO: Implement method
   */
  public void removeDuplicates() {
    boolean[] toRemove = new boolean[sides];
    A: for (int i = 0; i < sides-1; i++){
      for (int j = i + 1; j < sides; j++){
        if (vertices.get(i).equals(vertices.get(j))){
          toRemove[i] = true;
          continue A;
        }
      }
    }
    for (int i = 0; i < toRemove.length; i++){
      if (toRemove[i]){
        this.vertices.remove(i);
        this.sides--;
      }
    }
  }

  public void storeEdges(){
    // get all sides of the polygon (0 -> 1, 1 -> 2, ..., 2nd to last -> last)
    // this exludes 0 -> last, which will be included after
    for (int i = 0; i < this.sides-1; i++){
      this.edges.add(new Line(this.vertices.get(i), 
                          this.vertices.get(i+1)));
    }
    // include 0 -> last
    this.edges.add(new Line(this.vertices.get(0), 
                          this.vertices.get(this.sides-1)));
  }

  public Vector2D getLeftNeighbor(Vector2D vertex){
    for (int i = 0; i < sides; i++){
      if (vertices.get(i).equals(vertex)){
        if (i == 0){
          return vertices.get(sides - 1);
        }
        return vertices.get(i-1);
      }
    }
    return null;
  }
  public Vector2D getRightNeighbor(Vector2D vertex){
    for (int i = 0; i < sides; i++){
      if (vertices.get(i).equals(vertex)){
        if (i == sides - 1){
          return vertices.get(0);
        }
        return vertices.get(i+1);
      }
    }
    return null;
  }

}
