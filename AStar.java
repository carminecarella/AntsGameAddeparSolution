import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author carmine carella
 * 
 * Astar algorithm for pathfinding
 *
 */
public final class AStar {
	    
    //Path to move from a start square to final one
    public static LinkedList<Square> generatePath(Square start, Square goal, Board board) {
    	
      Set<Square> open = new HashSet<Square>();
      Set<Square> closed = new HashSet<Square>();
      
      start.setG(0);
      start.setH(Utils.approximateDistance(start, goal));
      start.setF(start.getH());
      
      open.add(start);

      while (true) {
        Square current = null;
        if (open.size() == 0) {
          throw new RuntimeException("no path");
        }

        for (Square s : open) {
          if (current == null || s.getF() < s.getF()) {
            current = s;
          }
        }

        if (current == goal) {
          break;
        }

        open.remove(current);
        closed.add(current);

        for (Square neighbor: getNeighbors(current, board.discoveredSquare)) {
          if (neighbor != null) {
            int nextG = current.getG() + 1;

            if (nextG < neighbor.getG()) {
              open.remove(neighbor);
              closed.remove(neighbor);
            }

            if (!open.contains(neighbor) && !closed.contains(neighbor)) {
              neighbor.setG(nextG);
              neighbor.setH(Utils.approximateDistance(neighbor, goal));
              neighbor.setF(neighbor.getG() + neighbor.getH());
              neighbor.setParent(current);
              open.add(neighbor);
            }
          }
        }
      }
      
      // Way back
      LinkedList<Square> route = new LinkedList<Square>();
      Square current = goal;
      while (current.getParent() != null) {
        
    	route.add(current);
        Square t = current.getParent();
        current.setParent(null);
        current = t;
      }
      return route;
    }
    
    // Returns an ArrayList of all known neighboring cells
 	private static List<Square> getNeighbors(Square current, HashMap<String, Square> board){
 		
 		List<Square> result = new ArrayList<Square>();
 		
 		//retrieve North square
 		if(board.get(Utils.getNorthSquare(current)) != null) {
 			result.add(board.get(Utils.getNorthSquare(current)));
 		}
 		
 		//retrieve East square
 		if(board.get(Utils.getEastSquare(current)) != null) {
 			result.add(board.get(Utils.getEastSquare(current)));
 		}
 		
 		//retrieve South square
 		if(board.get(Utils.getSouthSquare(current)) != null) {
 			result.add(board.get(Utils.getSouthSquare(current)));
 		}
 		
 		//retrieve West square
 		if(board.get(Utils.getWestSquare(current)) != null) {
 			result.add(board.get(Utils.getWestSquare(current)));
 		}
 	
 		return result;
 	}

}