import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class AStarSearch implements Search {

  public AStarSearch(){
    
  }

  public LinkedList<Vector2D> search(Environment environment) {
    
    // store path
    LinkedList<Vector2D> path = new LinkedList<Vector2D>();

    // store frontier as hash map. Vector2D vect -> double g
    HashMap<Vector2D, Double> frontier = new HashMap<Vector2D, Double>();

    // store explored as hash set of Vector2Ds
    HashSet<Vector2D> explored = new HashSet<Vector2D>();

    // stores where the explored node came from
    HashMap<Vector2D, Vector2D> cameFrom = new HashMap<Vector2D, Vector2D>();

    // add start to frontier
    frontier.put(environment.start, 0.0);
    cameFrom.put(environment.start, null);

    // while frontier not empty:
    while(!(frontier.isEmpty())){
      // get vector & vectorG with best f(vector)
        // get vector with best f(vector)
      Vector2D best = bestF(frontier);
        // get vectorG = frontier[vector]
      double gBest = frontier.get(best);
        // remove vector from frontier
      frontier.remove(best);
      // add to path
      // if goal, return path
      if (best.equals(environment.goal)){
        ArrayList<Vector2D> p = getPath(environment, cameFrom);
        for (Vector2D v : p){
          path.add(v);
        }
        return path;
      }
      // add vector to explored
      explored.add(best);
      // possibleNext = vector.validNextMove
      LinkedList<Vector2D> possibleNext = best.validNextMoves;
      // for nextVect in possibleNext:
      for (Vector2D nextVect : possibleNext) {
        // if nextVect in explored: continue
        if (explored.contains(nextVect)){
          continue;
        }
        double gNextVect = gBest + Vector2D.distance(best, nextVect);
        
        // if frontier[nextVect] > g: set frontier[nextVect] = g
        if (frontier.keySet().contains(nextVect)){
          if (frontier.get(nextVect) > gNextVect){
            frontier.replace(nextVect, gNextVect);
            cameFrom.replace(nextVect, best);
          }
        } else{
          // if not in frontier, add it
          frontier.put(nextVect, gNextVect);
          cameFrom.put(nextVect, best);
        }
        
      }
    }    
    return null; // return failure
  }

  public ArrayList<Vector2D> getPath(Environment env, HashMap<Vector2D, Vector2D> cameFrom){
    ArrayList<Vector2D> path = new ArrayList<Vector2D>();
    Vector2D current = env.goal;
    while(!(current.equals(env.start))){
      path.add(0, current);
      current = cameFrom.get(current);
    }
    path.add(0, current);
    return path;
  }

  public Vector2D bestF(HashMap<Vector2D, Double> map){
    Double bestF = Double.MAX_VALUE;
    Vector2D bestVector = null;

    for (Map.Entry<Vector2D,Double> entry : map.entrySet()) {
      Vector2D key = entry.getKey();
      Double g = entry.getValue();
      double f = key.h + g;
      if (f < bestF || bestVector == null){
        bestF = f;
        bestVector = key;
      }
    }
    return bestVector;

  }

  public String getName() { return "astar"; }
}
