import java.util.LinkedList;

public interface Search {
  
  public LinkedList<Vector2D> search(Environment environment);

  public String getName();

}
