import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ants.Action;
import ants.Ant;
import ants.Direction;
import ants.Surroundings;
import ants.Tile;


/**
 * @author carmine carella
 *
 * Ant class implementation
 */
public class MyAnt implements Ant{
	
  private Board board;
  
  private Role role;
  
  private LinkedList<Square> path;
  
  public MyAnt(){
	  board = new Board();
      path = new LinkedList<Square>();
      role = Role.SCOUT;
  }
  
  // ------------------------ Implemented method of Ant interface ------------------------
  public Action getAction(Surroundings surroundings){      
    // the ant updates the current square (amount of food)
	updateCurrentSquare(surroundings.getCurrentTile().getAmountOfFood());
	// the ant explores the enviroment
    exploreSurroundings(surroundings.getTile(Direction.NORTH), surroundings.getTile(Direction.EAST), 
    					surroundings.getTile(Direction.SOUTH), surroundings.getTile(Direction.WEST));
    // the ant does somenthing
    return doAction();
  }

  public byte[] send(){
    try {
      return sendBoard();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void receive(byte[] data){
    try {
      receiveBoard(data);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  //------------------------ Private method for the class ------------------------
  /**********ACTIONS*************/
  private Action doAction() {  	  
	// choose a move base on the ants role
	switch(this.role){
	case FOOD_BEARER:
		return doDropOff();
	case SCOUT:
		return doScout();
	}
	return Action.move(Direction.NORTH);			  	  
  }
  
  // If an ant has collected food, it is a FOOD BEARER 
  // and it has to go to the anthill to drop off.
  // After dropping off, the ant can start to scouting again
  private Action doDropOff(){
	  // create the path to go to anthill
	  if (path.isEmpty() && !board.isAntHill())      	
		  path = AStar.generatePath(board.getCurrentSquare(), board.getAntHillSquare(), board);	        	
      // if ant has reached the antihill -> drop off
	  if (board.isAntHill()) {
		  role = Role.SCOUT;
		  return Action.DROP_OFF;
	  // otherwise continues to move on the next square in the path towards anthill  
      } else {
    	  Direction d = board.getDirection(path.removeLast());
    	  return Action.move(d);
      }
  }
  
  // If an ant hasn't collected food, it is a SCOUT 
  // and it walks on board following a path until it reaches a food square
  // and gathers food and changes role 
  private Action doScout(){
	  // the ant has reached a square with food so it changes role and gathers food
	  if (board.getCurrentSquare().getFood() > 0 && !board.isAntHill()) {
		path.clear();
		role = Role.FOOD_BEARER;
		return Action.GATHER;
	  } 
	  // the ant continues walking on the board, creating a path towards 
	  // the next food cell or plain one or just moving if it has already had a path to follow 
	  else {
		if (path.isEmpty()){
			Square goal = board.getNextSquare();
			path = AStar.generatePath(board.getCurrentSquare(), goal, board);
		}
		Direction d = board.getDirection(path.removeLast());
		return Action.move(d);
	  } 
  }
  
  /**********UPDATE CURRENT SQUARE AND SOURROUNDINGS*************/
  private void updateCurrentSquare(Integer food) {
      board.updateCurrentSquare(food);
  }
  private void exploreSurroundings(Tile N, Tile E, Tile S, Tile W) {
	  board.exploreSurroundings(N, E, S, W);
  }
  
  /**********COMMUNICATION BETWEEN ANTS*************/
  private void receiveBoard(byte[] input) throws IOException {
  	
  	List<Square> result = new ArrayList<Square>();
  	
  	//BoardSerializable br = (BoardSerializable)SerializationUtils.deserialize(input);
  	BoardSerializable br = null;
  	
  	ByteArrayInputStream in = new ByteArrayInputStream(input);
	    ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(in);		
			br = (BoardSerializable)is.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
  	
		for(Square s: br.getSquares()){
			result.add(s);
		}
		      
		board.updateBoard(result);
  }

  private byte[] sendBoard() throws IOException {
    
    List<Square> squares = new ArrayList<Square>();
    
    for (Square s : board.discoveredSquare.values()) {
    	squares.add(s);        
    }
    
    BoardSerializable bs = new BoardSerializable(squares);
    
	  ByteArrayOutputStream out = new ByteArrayOutputStream();
	  ObjectOutputStream os;
	  try {
		  os = new ObjectOutputStream(out);
		  os.writeObject(bs);			
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
	
    //byte[] serializedBoard = SerializationUtils.serialize(bs);
    byte[] serializedBoard = out.toByteArray();
    return serializedBoard;
  }

}