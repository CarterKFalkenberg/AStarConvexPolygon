import java.io.FileNotFoundException;
import java.util.LinkedList;

public class Main {
  public static void main(String[] args) throws FileNotFoundException {
    Environment env = new Environment();
    env.readFromFile("output/environment.txt");
    System.out.println("Loaded an environment with " + env.obstacles.size() + " obstacles.");

    Search search = new AStarSearch();
    System.out.println("Attempting " + search.getName());
    LinkedList<Vector2D> path = search.search(env);
    Environment.printPath(search.getName(), path);
    for (Vector2D v : path){
      System.out.println("(" + v.x + ", " + v.y + ")");
    }
  }
}
